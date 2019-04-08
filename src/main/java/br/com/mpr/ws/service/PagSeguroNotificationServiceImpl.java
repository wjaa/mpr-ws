package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.exception.PagSeguroNotificationException;
import br.com.mpr.ws.exception.PedidoServiceException;
import br.com.uol.pagseguro.api.application.authorization.search.AuthorizationDetail;
import br.com.uol.pagseguro.api.notification.PagSeguroNotificationHandler;
import br.com.uol.pagseguro.api.preapproval.search.PreApprovalDetail;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class PagSeguroNotificationServiceImpl implements PagSeguroNotificationHandler {


    private static final Log LOG = LogFactory.getLog(PagSeguroNotificationServiceImpl.class);

    @Autowired
    private PedidoService pedidoService;


    @Override
    public void handleTransactionNotification(TransactionDetail transactionDetail) {
        LOG.info("m=handleTransactionNotification");
        PedidoEntity pedido = pedidoService.findPedidoByCodigo(transactionDetail.getReference());

        if (pedido == null){
            pedido = pedidoService.findPedidoByTransactionCode(transactionDetail.getCode());
        }

        if (pedido == null){
            LOG.info("m=handleTransactionNotification, nenhum pedido encontrado. \n " +
                    String.format("Pedido com código %s e transactionCode %s , não foi encontrado!!!"
                            ,transactionDetail.getReference(),
                            transactionDetail.getCode()));

            throw new PagSeguroNotificationException(
                    String.format("Pedido com código %s e transactionCode %s , não foi encontrado!!!"
                            ,transactionDetail.getReference(),
                            transactionDetail.getCode()));
        }

        try{

            LOG.info("m=handleTransactionNotification, status = " + transactionDetail.getStatus().getStatus());
            switch (transactionDetail.getStatus().getStatus()){

                //estao confirmando o pagamento
                case WAITING_PAYMENT:
                case IN_REVIEW: {
                    LOG.info("m=handleTransactionNotification, pedido = " + pedido.getCodigoPedido() +
                            " status redundante, nao precisa fazer nada.");
                    break;
                }

                //já foi aprovado o pagamento
                case AVAILABLE:
                case APPROVED: {
                    pedidoService.confirmarPagamento(transactionDetail.getCode(), pedido.getId());
                    break;
                }

                //já foi cancelada direto.
                case CANCELLED: {
                    pedidoService.cancelarPedido(pedido.getId());
                    break;
                }

                //default caso venha outro status.
                default: {
                    LOG.info("m=handleTransactionNotification, pedido = " + pedido.getCodigoPedido() +
                            " status redundante, nao precisa fazer nada.");
                    break;
                }
            }
        }catch (PedidoServiceException ex){
            LOG.error("m=handleTransactionNotification, error ", ex );
            throw new PagSeguroNotificationException("Erro no handler do pagaseguro: " + ex.getMessage(), ex);
        }


    }

    @Override
    public void handleAuthorizationNotification(AuthorizationDetail authorizationDetail) {
        throw new PagSeguroNotificationException("Metodo nao implementado");
    }

    @Override
    public void handlePreApprovalNotification(PreApprovalDetail preApprovalDetail) {
        throw new PagSeguroNotificationException("Metodo nao implementado");
    }
}
