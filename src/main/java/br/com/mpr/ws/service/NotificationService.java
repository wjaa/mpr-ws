package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.ClienteEntity;
import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.vo.ClienteVo;

/**
 * Created by wagner on 08/02/19.
 */
public interface NotificationService {

    void sendTransactionCriadaBoleto(ClienteEntity cliente, PedidoEntity pedido, String urlBoleto);

    void sendTransactionCriadaCartao(ClienteEntity cliente, PedidoEntity pedido);

    void sendPedidoCancelado(ClienteEntity cliente, PedidoEntity pedido);

    void sendEsqueceuSenha(ClienteEntity cliente, String hashTrocaSenha);

    void sendUsuarioCriado(ClienteEntity cliente);

    void sendPagamentoConfirmado(ClienteEntity cliente, PedidoEntity pedido);
}
