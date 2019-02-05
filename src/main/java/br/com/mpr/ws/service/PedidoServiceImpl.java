package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.vo.CheckoutForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wagner on 12/21/18.
 */
@Service
public class PedidoServiceImpl implements PedidoService{

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private CupomService cupomService;

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ProdutoService produtoService;

    @Override
    public PedidoEntity createPedido(String code, CheckoutForm checkoutForm) {
        PedidoEntity pedidoEntity = new PedidoEntity();


        return pedidoEntity;
    }


}
