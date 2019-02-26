package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.MprParameterEntity;
import br.com.mpr.ws.entity.MprParameterType;
import br.com.mpr.ws.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wagner on 09/01/19.
 */
@Service
public class MprParameterServiceImpl implements MprParameterService {

    @Autowired
    private CommonDao commonDao;



    @Override
    public String getParameter(MprParameterType parameter, String defaultValue) {
        MprParameterEntity entity = this.getMprParameter(parameter);

        if (entity != null){
            return entity.getValor();
        }
        return defaultValue;
    }


    @Override
    public Boolean getParameterBoolean(MprParameterType parameter, Boolean defaultValue) {

        MprParameterEntity entity = this.getMprParameter(parameter);
        if (entity != null){
            if (entity.getValor().length() == 1){
                return "1".equals(entity.getValor()) ? true : false;
            }
            return new Boolean(entity.getValor());
        }
        return defaultValue;
    }

    @Override
    public Date getParameterDate(MprParameterType parameter, Date defaultValue) {
        MprParameterEntity entity = this.getMprParameter(parameter);
        if (entity != null){
            return DateUtils.getDateddMMyyyy(entity.getValor());
        }
        return defaultValue;
    }

    @Override
    public Double getParameterDouble(MprParameterType parameter, Double defaultValue) {
        MprParameterEntity entity = this.getMprParameter(parameter);
        if (entity != null){
            return Double.valueOf(entity.getValor());
        }
        return defaultValue;
    }

    @Override
    public Long getParameterLong(MprParameterType parameter, Long defaultValue) {
        MprParameterEntity entity = this.getMprParameter(parameter);
        if (entity != null){
            return Long.valueOf(entity.getValor());
        }
        return defaultValue;
    }

    @Override
    public Integer getParameterInteger(MprParameterType parameter, int defaultValue) {
        MprParameterEntity entity = this.getMprParameter(parameter);
        if (entity != null){
            return Integer.valueOf(entity.getValor());
        }
        return defaultValue;
    }

    private MprParameterEntity getMprParameter(MprParameterType parameter) {
        return commonDao
                .findByPropertiesSingleResult(MprParameterEntity.class, new String[]{"chave"},
                        new Object[]{parameter});
    }


}
