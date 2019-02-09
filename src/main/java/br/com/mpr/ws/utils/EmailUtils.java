package br.com.mpr.ws.utils;


import br.com.mpr.ws.vo.EmailParamVo;
import br.com.mpr.ws.vo.EmailServerConfigVo;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

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

}
