package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.exception.CheckoutCieloServiceException;
import br.com.mpr.ws.vo.CartaoCreditoVo;
import br.com.mpr.ws.vo.CheckoutForm;
import br.com.mpr.ws.vo.CheckoutVo;
import br.com.mpr.ws.vo.FormaPagamentoVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

/**
 *
 */
public class CheckoutCieloServiceImplTest extends BaseDBTest {


    @Autowired
    private CheckoutService checkoutService;


    @Test
    public void getCheckout(){

        try {
            CheckoutVo checkout = this.checkoutService.detailCheckout(1l);
            Assert.assertEquals(new Double(28.50), checkout.getValorProdutos());
            Assert.assertEquals(new Double(128.50), checkout.getTotal());
            Assert.assertNotNull(checkout.getProdutos());
            Assert.assertEquals(1, checkout.getProdutos().size());
            Assert.assertNotNull(checkout.getEnderecoVo());
            Assert.assertNotNull(checkout.getEnderecoVo().getEndereco());
            Assert.assertNotNull(checkout.getEnderecoVo().getDescricao());
            Assert.assertNotNull(checkout.getPrevisaoEntrega());


        } catch (CheckoutCieloServiceException e) {
            Assert.assertTrue(e.getMessage(), false);
        }


    }



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
            cartaoCreditoVo.setCardToken(this.getTokenCielo());
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
        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        CartaoCreditoVo cartaoCreditoVo = new CartaoCreditoVo();
        cartaoCreditoVo.setBrand("Visa")
                .setSecutiryCode("123")
                .setCardNumber("4532117080573700")
                .setHolder("Comprador T Cielo")
                .setExpirationDate("12/" + year );

        return checkoutService.getCardToken(cartaoCreditoVo);

    }

}
