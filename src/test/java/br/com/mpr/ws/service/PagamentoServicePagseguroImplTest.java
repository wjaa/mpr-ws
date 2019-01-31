package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.entity.PedidoEntity;
import br.com.mpr.ws.exception.RestException;
import br.com.mpr.ws.utils.RestUtils;
import br.com.mpr.ws.vo.*;
import cieloecommerce.sdk.ecommerce.Customer;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by wagner on 21/01/19.
 */
public class PagamentoServicePagseguroImplTest extends BaseDBTest {

    @Autowired
    @Qualifier("PagamentoServicePagseguroImpl")
    private PagamentoService pagamentoService;

    @Autowired
    private CheckoutService checkoutService;


    @Test
    public void pagamento() throws Exception {

        CheckoutVo checkoutVo = checkoutService.checkout(1l);

        CheckoutForm form = new CheckoutForm();
        form.setIdCheckout(checkoutVo.getId());
        FormaPagamentoVo formaPagamentoVo = new FormaPagamentoVo();
        formaPagamentoVo.setTipoPagamento(FormaPagamentoVo.TipoPagamento.CARTAO_CREDITO);
        CartaoCreditoVo cartaoCreditoVo = new CartaoCreditoVo();
        cartaoCreditoVo.setToken(this.getToken());
        formaPagamentoVo.setCartaoCredito(cartaoCreditoVo);
        form.setFormaPagamento(formaPagamentoVo);
        PedidoEntity pedidoEntity = pagamentoService.pagamento(form);
        Assert.assertNotNull(pedidoEntity);
        Assert.assertNotNull(pedidoEntity.getIdPagamento());
    }

    private String getToken(){
        String tokenPS = "66B5E9A69A3145F9B60DCF964E84733E";
        String emailPS = "wag182@gmail.com";
        String urlPS = "https://ws.sandbox.pagseguro.uol.com.br/v2/sessions/";

        Map<String,String> param = new HashMap();
        param.put("email",emailPS);
        param.put("token",tokenPS);

        try {
            String xml = RestUtils.post(urlPS,param);
            JAXBContext jaxbContext = JAXBContext.newInstance(SessionPagseguro.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            SessionPagseguro sessionPagseguro = (SessionPagseguro) jaxbUnmarshaller.unmarshal(new StringReader(xml));

            System.out.println("session pagseguro = " + sessionPagseguro.getId());
            PagSeguro

        } catch (RestException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return "fdsa";
    }

}