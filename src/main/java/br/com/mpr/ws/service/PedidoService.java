package br.com.mpr.ws.service;


import br.com.mpr.ws.entity.HistoricoPedidoEntity;
import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.entity.SysCodeType;
import br.com.mpr.ws.exception.PedidoServiceException;
import br.com.mpr.ws.vo.CheckoutForm;

import java.util.List;

/**
 *
 */
public interface PedidoService {


    PedidoEntity createPedido(CheckoutForm checkoutForm) throws PedidoServiceException;

    HistoricoPedidoEntity createNovoHistorico(Long idPedido, SysCodeType sysCode) throws PedidoServiceException;

    PedidoEntity cancelarPedido(Long idPedido) throws PedidoServiceException;

    PedidoEntity confirmarPedido(String code, Long idPedido) throws PedidoServiceException;

    PedidoEntity getPedido(Long idPedido);

    PedidoEntity findPedidoByTransactionCode(String transactionCode);

    PedidoEntity findPedidoByCodigo(String codigo);

    List<PedidoEntity> findPedidoByStatus(SysCodeType sysCode);

    List<PedidoEntity> findPedidoByIdCliente(Long idCliente);

}
