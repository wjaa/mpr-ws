package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.CupomEntity;

/**
 * Created by wagner on 11/01/19.
 */
public interface CupomService {

    CupomEntity findCupomByCode(String cupomCode);

    CupomEntity getCupomById(Long idCupom);
}
