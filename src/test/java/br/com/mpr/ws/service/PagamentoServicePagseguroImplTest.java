package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.vo.CartaoCreditoVo;
import br.com.mpr.ws.vo.CheckoutForm;
import br.com.mpr.ws.vo.FormaPagamentoVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.junit.Assert.*;

/**
 * Created by wagner on 21/01/19.
 */
public class PagamentoServicePagseguroImplTest extends BaseDBTest {

    @Autowired
    @Qualifier("PagamentoServicePagseguroImpl")
    private PagamentoService pagamentoService;

    @Test
    public void pagamento() throws Exception {
        CheckoutForm form = new CheckoutForm();
        //form.setIdCarrinho(2l);
        FormaPagamentoVo formaPagamentoVo = new FormaPagamentoVo();
        formaPagamentoVo.setTipoPagamento(FormaPagamentoVo.TipoPagamento.CARTAO_CREDITO);
        CartaoCreditoVo cartaoCreditoVo = new CartaoCreditoVo();
        cartaoCreditoVo.setToken("teste");
        formaPagamentoVo.setCartaoCredito(cartaoCreditoVo);
        form.setFormaPagamento(formaPagamentoVo);
        PedidoEntity pedidoEntity = pagamentoService.pagamento(form);
        Assert.assertNotNull(pedidoEntity);
        Assert.assertNotNull(pedidoEntity.getIdPagamento());
    }

}