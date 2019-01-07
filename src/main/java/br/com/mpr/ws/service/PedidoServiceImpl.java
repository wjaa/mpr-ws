package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.CarrinhoEntity;
import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.vo.FormaPagamentoVo;
import org.springframework.stereotype.Service;

/**
 * Created by wagner on 12/21/18.
 */
@Service
public class PedidoServiceImpl implements PedidoService{
    @Override
    public PedidoEntity createPedido(CarrinhoEntity carrinho, FormaPagamentoVo formaPagamento) {
        return new PedidoEntity();
    }
}
