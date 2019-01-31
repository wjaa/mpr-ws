package br.com.mpr.ws.service;

/**
 * Created by wagner on 09/01/19.
 */
public interface MprParameterService {



    enum MprParameter{
        CEP_ORIGEM,
        MARGEM_PROTECAO,
        PS_API_TOKEN,
        PS_API_EMAIL,
        PS_API_SESSION_URL,
        ;
    }


    String getParameter(MprParameter parameter, String defaultValue);
    Integer getParameterInteger(MprParameter margemProtecao, int i);
}
