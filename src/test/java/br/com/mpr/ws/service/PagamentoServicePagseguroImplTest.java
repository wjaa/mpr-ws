package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.PagamentoType;
import br.com.mpr.ws.exception.RestException;
import br.com.mpr.ws.utils.RestUtils;
import br.com.mpr.ws.vo.*;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wagner on 21/01/19.
 */
public class PagamentoServicePagseguroImplTest{

    @Autowired
    @Qualifier("PagamentoServicePagseguroImpl")
    private PagamentoService pagamentoService;

    @Autowired
    private CheckoutService checkoutService;


    public void pagamentoBoleto() throws Exception {

        CheckoutVo checkoutVo = checkoutService.checkout(1l);

        CheckoutForm form = new CheckoutForm();
        form.setIdCheckout(checkoutVo.getId());
        FormaPagamentoVo formaPagamentoVo = new FormaPagamentoVo();
        formaPagamentoVo.setPagamentoType(PagamentoType.BOLETO);
        form.setFormaPagamento(formaPagamentoVo);
        ResultadoPagamentoVo result = pagamentoService.pagamento(form);
        Assert.assertNotNull(result);
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
            JAXBContext jaxbContext = JAXBContext.newInstance(SessionPagseguroVo.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            SessionPagseguroVo sessionPagseguro = (SessionPagseguroVo) jaxbUnmarshaller.unmarshal(new StringReader(xml));

            System.out.println("session pagseguro = " + sessionPagseguro.getId());
           // PagSeguro

        } catch (RestException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return "fdsa";
    }

}