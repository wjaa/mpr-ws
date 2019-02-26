package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.MprParameterType;

import java.util.Date;

/**
 * Created by wagner on 09/01/19.
 */
public interface MprParameterService {

    String getParameter(MprParameterType parameter, String defaultValue);
    Boolean getParameterBoolean(MprParameterType parameter, Boolean defaultValue);
    Date getParameterDate(MprParameterType parameter, Date defaultValue);
    Double getParameterDouble(MprParameterType parameter, Double defaultValue);
    Long getParameterLong(MprParameterType parameter, Long defaultValue);
    Integer getParameterInteger(MprParameterType parameter, int i);
}
