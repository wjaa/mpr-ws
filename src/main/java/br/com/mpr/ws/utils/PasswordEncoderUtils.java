package br.com.mpr.ws.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtils {

    public static String encoder(String pass){
        return new BCryptPasswordEncoder().encode(pass);
    }


    public static void main(String [] args){
        System.out.println(encoder("password"));
    }

}
