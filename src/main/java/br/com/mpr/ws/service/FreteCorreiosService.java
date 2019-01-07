package br.com.mpr.ws.service;

import br.com.mpr.ws.frete.correios.CalcPrecoPrazoWS;
import br.com.mpr.ws.vo.ResultFreteVo;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 */
@Service
public class FreteCorreiosService implements FreteService {


    @Override
    public ResultFreteVo calcFrete(String cepOrigem, String cepDestino) {
        CalcPrecoPrazoWS ws = new CalcPrecoPrazoWS();
        ws.getCalcPrecoPrazoWSSoap();
        ResultFreteVo resultFreteVo = new ResultFreteVo();
        resultFreteVo.setValor(100.0);
        resultFreteVo.setPrevisaoEntrega(new Date());
        return resultFreteVo;
    }
}
