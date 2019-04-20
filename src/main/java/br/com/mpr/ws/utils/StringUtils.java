package br.com.mpr.ws.utils;

import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    : rand == 2 ? String.valueOf((char)ThreadLocalRandom.current().nextInt('A', 'Z'+1))
                    : String.valueOf((char)ThreadLocalRandom.current().nextInt('0', '9'+1));

            hash += c;
        }
        return hash;
    }

    public static String getNumber(String numberStr) {
        Pattern p = Pattern.compile("([0-9]+)");
        Matcher m = p.matcher(numberStr);
        String result = "";
        while (m.find()) {
            result += m.group();
        }

        try {
            return result;
        }catch(Exception ex) {
            return null;
        }
    }


}