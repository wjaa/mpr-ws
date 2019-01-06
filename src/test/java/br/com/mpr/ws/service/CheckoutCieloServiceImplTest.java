package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.exception.CheckoutCieloServiceException;
import br.com.mpr.ws.vo.CartaoCreditoVo;
import br.com.mpr.ws.vo.CheckoutForm;
import br.com.mpr.ws.vo.FormaPagamentoVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 */
public class CheckoutCieloServiceImplTest extends BaseDBTest {


    @Autowired
    private CheckoutService checkoutService;



    @Test
    public void testPagamentoBoleto(){

        try{
            CheckoutForm form = new CheckoutForm();
            form.setIdCarrinho(1l);
            FormaPagamentoVo formaPagamentoVo = new FormaPagamentoVo();
            formaPagamentoVo.setTipoPagamento(FormaPagamentoVo.TipoPagamento.BOLETO);
            form.setFormaPagamento(formaPagamentoVo);
            checkoutService.checkout(form);
        }catch (CheckoutCieloServiceException ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

    @Test
    public void testPagamentoCartaoCredito(){

        try{
            CheckoutForm form = new CheckoutForm();
            form.setIdCarrinho(2l);
            FormaPagamentoVo formaPagamentoVo = new FormaPagamentoVo();
            formaPagamentoVo.setTipoPagamento(FormaPagamentoVo.TipoPagamento.CARTAO_CREDITO);
            CartaoCreditoVo cartaoCreditoVo = new CartaoCreditoVo();
            cartaoCreditoVo.setCardToken(getTokenCielo());
            formaPagamentoVo.setCartaoCredito(cartaoCreditoVo);
            form.setFormaPagamento(formaPagamentoVo);
            PedidoEntity pedidoEntity = checkoutService.checkout(form);
            Assert.assertNotNull(pedidoEntity);
            Assert.assertNotNull(pedidoEntity.getIdPagamento());
        }catch (CheckoutCieloServiceException ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }


    @Test
    public void testErrorPagamentoCartao(){


    }

    @Test
    public void testErrorPagamentoBoleto(){


    }

    private String getTokenCielo() throws CheckoutCieloServiceException {
        CartaoCreditoVo cartaoCreditoVo = new CartaoCreditoVo();
        cartaoCreditoVo.setBrand("Visa")
                .setSecutiryCode("123")
                .setCardNumber("4532117080573700")
                .setHolder("Comprador T Cielo")
                .setExpirationDate("12/2018");

        return checkoutService.getCardToken(cartaoCreditoVo);

    }

}
