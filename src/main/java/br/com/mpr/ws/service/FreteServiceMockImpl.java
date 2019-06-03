package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.FreteType;
import br.com.mpr.ws.utils.DateUtils;
import br.com.mpr.ws.vo.ResultFreteVo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wagner on 26/02/19.
 */
@Profile("test")
//@Service
public class FreteServiceMockImpl implements FreteService{
    @Override
    public ResultFreteVo calcFrete(FreteParam param) {
        ResultFreteVo resultFreteVo = new ResultFreteVo();

        if (FreteType.ECONOMICO.equals(param.getFreteType())){
            resultFreteVo.setValor(18.50);
            resultFreteVo.setPrevisaoEntrega(DateUtils.addDays(new Date(),8));
            resultFreteVo.setDiasUteis(8);
        }else{
            resultFreteVo.setValor(25.50);
            resultFreteVo.setPrevisaoEntrega(DateUtils.addDays(new Date(),2));
            resultFreteVo.setDiasUteis(2);
        }
        resultFreteVo.setFreteType(param.getFreteType());
        return resultFreteVo;
    }
}
