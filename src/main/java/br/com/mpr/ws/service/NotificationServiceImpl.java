package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.utils.NotificationUtils;
import br.com.mpr.ws.vo.ClienteVo;
import br.com.mpr.ws.vo.EmailParamVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by wagner on 08/02/19.
 */
@Service
public class NotificationServiceImpl implements NotificationService {
    private static final Log LOG = LogFactory.getLog(NotificationServiceImpl.class);

    @Value("${spring.profiles.active}")
    private String activeProfile;


    @Override
    public void sendPedidoCriado(ClienteVo cliente, PedidoEntity pedido) {

        //nao faz isso no ambiente de
        if ("test".equalsIgnoreCase(activeProfile)){
            return;
        }

        String emailTo = cliente.getEmail();
        StringBuilder body = new StringBuilder();
        body.append("Olá " + cliente.getNome() + "<br/>");
        body.append("  Seu pedido de numero = " + pedido.getCodigoPedido());
        body.append(" foi criado com sucesso! <br/>");
        body.append("  Estamos aguardando a confirmação de pagamento. <br/>");
        body.append("  Em breve enviaremos um novo status.<br/><br/>");
        body.append("  Att., <br/>");
        body.append("  Equipe meuportaretrato.com :) <br/>");

        try {
            NotificationUtils.sendEmail(new EmailParamVo()
                            .setBody(body.toString())
                            .setEmail(emailTo)
                            .setTitle("Recebemos seu pedido!"),
                    NotificationUtils.scNoreply
            );
        } catch (Exception e) {
            //TODO VERIFICAR O ERRO E SE FOR UM ERRO DE CONEXAO, JOGAR O EMAIL NA FILA DE ENVIO NOVAMENTE.
            LOG.error("Erro ao enviar a notificacao de pedido criado", e);
        }


    }

    @Override
    public void sendTransactionCriadaBoleto(ClienteVo cliente, PedidoEntity pedido, Byte[] boleto) {
        //nao faz isso no ambiente de
        if ("test".equalsIgnoreCase(activeProfile)){
            return;
        }

        String emailTo = cliente.getEmail();
        StringBuilder body = new StringBuilder();
        body.append("Olá " + cliente.getNome() + "<br/>");
        body.append("  Seu pedido de numero = " + pedido.getCodigoPedido());
        body.append(" foi criado com sucesso! <br/>");
        body.append("  Estamos aguardando a confirmação de pagamento. <br/>");
        body.append("  Em breve enviaremos um novo status.<br/><br/>");
        body.append("  Att., <br/>");
        body.append("  Equipe meuportaretrato.com :) <br/>");

        try {
            NotificationUtils.sendEmail(new EmailParamVo()
                            .setBody(body.toString())
                            .setEmail(emailTo)
                            .setTitle("Recebemos seu pedido!"),
                    NotificationUtils.scNoreply
            );
        } catch (Exception e) {
            //TODO VERIFICAR O ERRO E SE FOR UM ERRO DE CONEXAO, JOGAR O EMAIL NA FILA DE ENVIO NOVAMENTE.
            LOG.error("Erro ao enviar a notificacao de pedido criado", e);
        }
    }

    @Override
    public void sendTransactionCriadaCartao(ClienteVo cliente, PedidoEntity pedido) {

        //nao faz isso no ambiente de
        if ("test".equalsIgnoreCase(activeProfile)){
            return;
        }
        String emailTo = cliente.getEmail();
        StringBuilder body = new StringBuilder();
        body.append("Olá " + cliente.getNome() + "<br/>");
        body.append("  Seu pedido de numero = " + pedido.getCodigoPedido());
        body.append(" foi criado com sucesso! <br/>");
        body.append("  Estamos aguardando a confirmação de pagamento. <br/>");
        body.append("  Em breve enviaremos um novo status.<br/><br/>");
        body.append("  Att., <br/>");
        body.append("  Equipe meuportaretrato.com :) <br/>");

        try {
            NotificationUtils.sendEmail(new EmailParamVo()
                            .setBody(body.toString())
                            .setEmail(emailTo)
                            .setTitle("Recebemos seu pedido!"),
                    NotificationUtils.scNoreply
            );
        } catch (Exception e) {
            //TODO VERIFICAR O ERRO E SE FOR UM ERRO DE CONEXAO, JOGAR O EMAIL NA FILA DE ENVIO NOVAMENTE.
            LOG.error("Erro ao enviar a notificacao de pedido criado", e);
        }
    }
}
