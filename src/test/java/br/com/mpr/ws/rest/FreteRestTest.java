package br.com.mpr.ws.rest;

import br.com.mpr.ws.BaseMvcTest;
import br.com.mpr.ws.entity.FreteType;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.vo.ResultFreteVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

public class FreteRestTest extends BaseMvcTest {


    @Test
    public void calcFreteEconomico(){
        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/frete/economico/5/02323000");
            ResultFreteVo result = ObjectUtils.fromJSON(ra
                    .andReturn()
                    .getResponse().getContentAsString(), ResultFreteVo.class);

            Assert.assertNotNull(result);
            Assert.assertTrue(result.getValor() > 0);
            Assert.assertEquals(FreteType.ECONOMICO, result.getFreteType());
            Assert.assertNotNull(result.getDiasUteis());
            Assert.assertNotNull(result.getPrevisaoEntrega());

        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

    @Test
    public void calcFreteRapido(){
        try{
            ResultActions ra = getMvcGetResultActions("/api/v1/core/frete/rapido/5/02323000");
            ResultFreteVo result = ObjectUtils.fromJSON(ra
                    .andReturn()
                    .getResponse().getContentAsString(), ResultFreteVo.class);

            Assert.assertNotNull(result);
            Assert.assertTrue(result.getValor() > 0);
            Assert.assertEquals(FreteType.RAPIDO, result.getFreteType());
            Assert.assertNotNull(result.getDiasUteis());
            Assert.assertNotNull(result.getPrevisaoEntrega());

        }catch (Exception ex){
            Assert.assertTrue(ex.getMessage(), false);
        }
    }

}