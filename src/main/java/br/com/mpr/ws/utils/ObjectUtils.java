package br.com.mpr.ws.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by wagner on 02/07/15.
 */
public class ObjectUtils {
    private static final Log LOG = LogFactory.getLog(ObjectUtils.class);
    private static final ObjectMapper om = new ObjectMapper();
    private static final Gson g = new GsonBuilder().create();


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

    public static <T>T fromJSON(String json, Type type){
        return g.fromJson(json,type);

    }
}
