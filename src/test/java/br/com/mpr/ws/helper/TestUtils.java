package br.com.mpr.ws.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 */
public class TestUtils {

    /**
     *
     * @param o
     * @return
     */
    public static String toJson(Object o){
        Gson g = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        return g.toJson(o);
    }

}
