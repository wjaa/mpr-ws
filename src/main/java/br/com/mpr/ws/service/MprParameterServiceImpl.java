package br.com.mpr.ws.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wagner on 09/01/19.
 */
@Service
public class MprParameterServiceImpl implements MprParameterService {


    @Override
    public String getParameter(MprParameter parameter, String defaultValue) {
        return defaultValue;
    }

    @Override
    public Integer getParameterInteger(MprParameter margemProtecao, int defaultValue) {
        return defaultValue;
    }
}
