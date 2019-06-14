package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.SessionEntity;
import br.com.mpr.ws.exception.SessionServiceException;
import br.com.mpr.ws.utils.DateUtils;
import br.com.mpr.ws.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 */
@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private CommonDao commonDao;

    @Override
    public SessionEntity createSession() {
        SessionEntity session = new SessionEntity();
        session.setSessionToken(StringUtils.createMD5(StringUtils.createRandomHash()) +
                StringUtils.createMD5(String.valueOf(new Date().getTime())));
        session.setExpirationDate(resetExpirationDate());
        return commonDao.save(session);
    }

    @Override
    public void renewSession(String sessionToken) throws SessionServiceException {
        SessionEntity sessionEntity =  commonDao.findByPropertiesSingleResult(SessionEntity.class,
                new String[]{"sessionToken"},
                new Object[]{sessionToken});

        if (sessionEntity == null){
            throw new SessionServiceException("Session token não encontrado!");
        }

        sessionEntity.setExpirationDate(resetExpirationDate());

        commonDao.update(sessionEntity);
    }

    @Override
    public SessionEntity getSessionByToken(String sessionToken) {
        SessionEntity sessionEntity =  commonDao.findByPropertiesSingleResult(SessionEntity.class,
                new String[]{"sessionToken"},
                new Object[]{sessionToken});

        return sessionEntity;

    }


    private Date resetExpirationDate() {
        return DateUtils.addHour(new Date(), 1);
    }
}
