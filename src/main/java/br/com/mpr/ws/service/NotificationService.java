package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.vo.ClienteVo;

/**
 * Created by wagner on 08/02/19.
 */
public interface NotificationService {

    void sendPedidoCriado(ClienteVo cliente, PedidoEntity pedido);

    void sendTransactionCriadaBoleto(ClienteVo cliente, PedidoEntity pedido, Byte[] boleto);

    void sendTransactionCriadaCartao(ClienteVo cliente, PedidoEntity pedido);

}
