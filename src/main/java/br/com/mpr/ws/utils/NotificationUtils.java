package br.com.mpr.ws.utils;


import br.com.mpr.ws.exception.RestException;
import br.com.mpr.ws.properties.MprWsProperties;
import br.com.mpr.ws.vo.EmailParamVo;
import br.com.mpr.ws.vo.SendResultVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.io.*;
import java.net.URL;

/**
 * Created by wagner on 08/02/19.
 */
public class NotificationUtils {

    private static final Log LOG = LogFactory.getLog(NotificationUtils.class);


    public static void sendEmail(EmailParamVo param, MprWsProperties properties){

        try {
            SendResultVo sendResult = RestUtils.postJson(SendResultVo.class,
                    properties.getNotificationUrl(),
                    properties.getNotificationEmail(),
                    ObjectUtils.toJson(param)
                    );

            if (sendResult.getError()){
                LOG.error("m=sendEmail, erro ao enviar email,  param =" + param);
                LOG.error("m=sendEmail, msgErro = " +sendResult.getMsg());
            }
        } catch (RestException e) {
            LOG.error("m=sendEmail, erro ao enviar email,  param =" + param, e);
        }

    }

}
