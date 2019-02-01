package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.CupomEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by wagner on 14/01/19.
 */
@Service
public class CupomServiceImpl implements CupomService {

    @Resource(name = "CupomService.findCupomByName")
    private String QUERY_FIND_CUPOM_BY_NAME;

    @Autowired
    private CommonDao commonDao;

    @Override
    public CupomEntity findCupomByCode(String cupomCode) {
        return commonDao.findByNativeQuerySingleResult(QUERY_FIND_CUPOM_BY_NAME,
                CupomEntity.class,
                new String[]{"hash"},
                new Object[]{cupomCode});
    }

    @Override
    public CupomEntity getCupomById(Long idCupom) {
        return commonDao.get(CupomEntity.class, idCupom);
    }
}
