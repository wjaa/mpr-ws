package br.com.mpr.ws.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * Created by wagner on 02/07/15.
 */
public class ObjectUtils {
    private static final Log LOG = LogFactory.getLog(ObjectUtils.class);
    private static final ObjectMapper om = new ObjectMapper();


    public static String toJson(Object o){
        try {
            return om.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            LOG.error("Erro ao criar um json do objeto: " + o,e);
        }
        return null;
    }


    public static <T>T fromJSON(String json, Class<T> clazz){
        try {
            return om.readValue(json,clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
