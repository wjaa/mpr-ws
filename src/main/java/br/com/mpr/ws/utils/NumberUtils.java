package br.com.mpr.ws.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wagner on 26/08/15.
 */
public class NumberUtils {

    private static Locale ptBR = new Locale("pt", "BR");
    private static final NumberFormat numberFormat = new DecimalFormat("#,##0.00" , DecimalFormatSymbols.getInstance(ptBR));


    public static String formatPTbr(Double valor) {
        return numberFormat.format(valor);
    }

    public static NumberFormat getFormat(){
        return numberFormat;
    }

    public static Long getNumber(String numberStr) {
        Pattern p = Pattern.compile("([0-9]+)");
        Matcher m = p.matcher(numberStr);
        String result = "";
        while (m.find()) {
            result += m.group();
        }

        try {
            return new Long(result);
        }catch(Exception ex) {
            return null;
        }
    }

    public static Double convertNumberPtBr(String numberStr) {
        try {
            System.out.println("FORMAT = " + numberStr);
            return getFormat().parse(numberStr).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
