package br.com.mpr.ws.service;

import br.com.mpr.ws.vo.ResultFreteVo;
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
        ResultFreteVo frete = freteService.calcFrete("07093090","02323000");
        System.out.println(frete);
    }

}