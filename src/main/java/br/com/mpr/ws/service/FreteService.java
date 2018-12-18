package br.com.mpr.ws.service;

import br.com.mpr.ws.vo.ResultFreteVo;

/**
 *
 */
public interface FreteService {

     ResultFreteVo calcFrete(String cepOrigem, String cepDestino);

}
