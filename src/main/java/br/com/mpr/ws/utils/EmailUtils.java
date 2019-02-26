package br.com.mpr.ws.utils;


import br.com.mpr.ws.vo.EmailParamVo;
import br.com.mpr.ws.vo.EmailServerConfigVo;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by wagner on 08/02/19.
 */
public class EmailUtils {


    public static EmailServerConfigVo scNoreply = new EmailServerConfigVo();
    static{
        scNoreply = ObjectUtils.fromJSON("{\"smtp\":\"smtp.zoho.com\"," +
                        "\"port\":465,\"user\":\"noreply@meuportaretrato.com\"," +
                        "\"pass\":\"*F071212f*\",\"ssl\":true, \"from\":\"noreply@meuportaretrato.com\"," +
                        "\"name\":\"MeuPortaRetrato.com\"}"
                , EmailServerConfigVo.class);
    }

    public static void send(EmailParamVo p, EmailServerConfigVo sc) throws EmailException {
        HtmlEmail mail = new HtmlEmail();
        mail.addTo(p.getEmail());
        mail.setHostName(sc.getSmtp());
        mail.setSmtpPort(sc.getPort());
        mail.setAuthentication(sc.getUser(), sc.getPass());
        mail.setFrom(sc.getFrom(), sc.getName(), "UTF-8");
        mail.setBoolHasAttachments(true);
        mail.setHtmlMsg(p.getBody());
        mail.setSubject(p.getTitle());
        mail.setSSLOnConnect(new Boolean(sc.getSsl()));
        mail.setCharset("UTF-8");

        mail.send();
    }

    public static void sendAttachment(EmailParamVo p, EmailServerConfigVo sc, File anexo) throws EmailException {

        HtmlEmail mail = new HtmlEmail();
        mail.addTo(p.getEmail());
        mail.setHostName(sc.getSmtp());
        mail.setSmtpPort(sc.getPort());
        mail.setAuthentication(sc.getUser(), sc.getPass());
        mail.setFrom(sc.getFrom(), sc.getName(), "UTF-8");
        mail.setBoolHasAttachments(true);
        mail.setMsg(p.getBody());
        mail.setSubject(p.getTitle());
        mail.setSSLOnConnect(new Boolean(sc.getSsl()));
        mail.setCharset("UTF-8");
        mail.attach(anexo);

        mail.send();
    }

    public static void main(String args[]) throws IOException {
        File file = File.createTempFile("boleto-mpr-",".pdf");
        try {
            BufferedInputStream in = new BufferedInputStream(new URL("https://sandbox.pagseguro.uol.com.br/checkout/imprimeBoleto.jhtml?code=BAD19119DE0B41EC812968E5E87C8E48").openStream());

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            // handle exception
        }



        try {
            EmailUtils.sendAttachment(new EmailParamVo()
                            .setBody("Segue boleto em anexo")
                            .setEmail("wag182@gmail.com")
                            .setTitle("Transação confirmada"),
                            EmailUtils.scNoreply,
                            file
            );
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
