package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.FreteType;
import br.com.mpr.ws.vo.ResultFreteVo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by wagner on 12/21/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FreteServiceCorreioImplTest{

    @Autowired
    @Qualifier("FreteServiceCorreioImpl")
    private FreteService freteService;

    @Test
    public void calcFrete() throws Exception {
        ResultFreteVo frete = freteService.calcFrete(
                new FreteService.FreteParam(FreteType.ECONOMICO,
                        "04634-900",
                        0.3,
                        30.0,
                        25.0,
                        4.0));
        Assert.assertNotNull(frete.getValor());
        Assert.assertNotNull(frete.getDiasUteis());
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