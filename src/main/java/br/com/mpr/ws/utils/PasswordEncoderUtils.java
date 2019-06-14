package br.com.mpr.ws.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtils {

    public static String encoder(String pass){
        return new BCryptPasswordEncoder().encode(pass);
    }


    public static void main(String [] args){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);

        String psss1 = encoder.encode("1234567");
        System.out.println(psss1);
        String psss2 = encoder.encode("1234567");
        System.out.println(psss2);

        System.out.println(new BCryptPasswordEncoder().matches("userwebsite@*753951*",psss1));
        System.out.println(new BCryptPasswordEncoder().matches("userwebsite@*753951*",psss2));

    }

}
