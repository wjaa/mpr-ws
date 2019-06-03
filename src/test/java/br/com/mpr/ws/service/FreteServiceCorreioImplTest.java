package br.com.mpr.ws.service;

import br.com.mpr.ws.BaseDBTest;
import br.com.mpr.ws.entity.FreteType;
import br.com.mpr.ws.entity.MprParameterType;
import br.com.mpr.ws.vo.ResultFreteVo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by wagner on 12/21/18.
 */
public class FreteServiceCorreioImplTest extends BaseDBTest {

    @Autowired
    @Qualifier("FreteServiceCorreioImpl")
    private FreteService freteService;

    @Autowired
    private MprParameterService parameterService;

    @Test
    public void calcFrete() throws Exception {

        ResultFreteVo frete = freteService.calcFrete(
                new FreteService.FreteParam(FreteType.ECONOMICO,
                        "02323000",
                        0.3,
                        150.1,
                        150.1,
                        4.5));
        Assert.assertNotNull(frete);
        Assert.assertEquals(new Double(19.80), frete.getValor());
        Assert.assertEquals(FreteType.ECONOMICO, frete.getFreteType());

        Integer prazo = 2 + parameterService.getParameterInteger(MprParameterType.PRAZO_MONTAGEM,3);
        Assert.assertEquals(prazo, frete.getDiasUteis());
        Assert.assertNotNull(frete.getPrevisaoEntrega());
    }

    @Test
    public void calcFreteCepInvalido() throws Exception {
        ResultFreteVo frete = freteService.calcFrete(
                new FreteService.FreteParam(FreteType.ECONOMICO,
                        "0000-000",
                        0.3,
                        30.0,
                        25.0,
                        4.0));
        Assert.assertTrue(frete.hasError());
        Assert.assertTrue(frete.getMessageError().contains("CEP de destino invalido"));
    }

}