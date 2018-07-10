package br.com.mpr.ws.utils;

import org.springframework.util.DigestUtils;

/**
 * Created by wagner on 18/08/15.
 */
public class StringUtils {

    public static String createMD5(String str){
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }
}