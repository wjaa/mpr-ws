package br.com.mpr.ws.service;

import br.com.mpr.ws.frete.correios.CalcPrecoPrazoWS;
import br.com.mpr.ws.vo.ResultFreteVo;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class CorreiosFreteService implements FreteService {


    @Override
    public ResultFreteVo calcFrete(String cepOrigem, String cepDestino) {
        CalcPrecoPrazoWS ws = new CalcPrecoPrazoWS();
        ws.getCalcPrecoPrazoWSSoap();
        return null;
    }
}
