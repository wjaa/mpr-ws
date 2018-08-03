package br.com.mpr.ws.utils;

import org.springframework.util.DigestUtils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by wagner on 18/08/15.
 */
public class StringUtils {

    public static String createMD5(String str){
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    public static String createRandomHash() {
        String hash = "";
        for (int i = 0; i < 8; i++){
            String c;
            int rand = ThreadLocalRandom.current().nextInt(0,3);
            c = rand == 0 ? String.valueOf((char)ThreadLocalRandom.current().nextInt('A', 'Z'+1))
                    : rand == 1 ? String.valueOf((char)ThreadLocalRandom.current().nextInt('0', '9'+1))
                    : rand == 2 ? String.valueOf((char)ThreadLocalRandom.current().nextInt('a', 'z'+1))
                    : String.valueOf((char)ThreadLocalRandom.current().nextInt('A', 'Z'+1));

            hash += c;
        }
        return hash;
    }
    public static void main (String args[]){
        System.out.println(StringUtils.createRandomHash());
        System.out.println(StringUtils.createRandomHash());
        System.out.println(StringUtils.createRandomHash());
        System.out.println(StringUtils.createRandomHash());
        System.out.println(StringUtils.createRandomHash());
    }
}