package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.PagamentoType;
import br.com.mpr.ws.exception.PagamentoServiceException;
import br.com.mpr.ws.vo.CartaoCreditoVo;
import br.com.mpr.ws.vo.CheckoutForm;
import br.com.mpr.ws.vo.FormaPagamentoVo;
import br.com.mpr.ws.vo.ResultadoPagamentoVo;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

/**
 *
 */
public class PagamentoServiceCieloImplTest{


    @Autowired
    private PagamentoService pagamentoService;



    public void testPagamentoBoleto(){

        try{
            CheckoutForm form = new CheckoutForm();
            //form.setIdCarrinho(1l);
            FormaPagamentoVo formaPagamentoVo = new FormaPagamentoVo();
            formaPagamentoVo.setPagamentoType(PagamentoType.BOLETO);
            form.setFormaPagamento(formaPagamentoVo);
            pagamentoService.pagamento(form);
        }catch (PagamentoServiceException ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

    public void testPagamentoCartaoCredito(){

        try{
            CheckoutForm form = new CheckoutForm();
            //form.setIdCarrinho(2l);
            FormaPagamentoVo formaPagamentoVo = new FormaPagamentoVo();
            formaPagamentoVo.setPagamentoType(PagamentoType.CARTAO_CREDITO);
            CartaoCreditoVo cartaoCreditoVo = new CartaoCreditoVo();
            cartaoCreditoVo.setToken(this.getTokenCielo());
            formaPagamentoVo.setCartaoCredito(cartaoCreditoVo);
            form.setFormaPagamento(formaPagamentoVo);
            ResultadoPagamentoVo result = pagamentoService.pagamento(form);
            Assert.assertNotNull(result);
        }catch (PagamentoServiceException ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }


    public void testErrorPagamentoCartao(){


    }

    public void testErrorPagamentoBoleto(){


    }

    private String getTokenCielo() throws PagamentoServiceException {
        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        CartaoCreditoVo cartaoCreditoVo = new CartaoCreditoVo();
        cartaoCreditoVo.setBrand("Visa")
                .setSecutiryCode("123")
                .setCardNumber("4532117080573700")
                .setHolder("Comprador T Cielo")
                .setExpirationDate("12/" + year );

        return pagamentoService.getCardToken(cartaoCreditoVo);

    }

}
