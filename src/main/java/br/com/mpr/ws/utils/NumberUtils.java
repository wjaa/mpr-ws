package br.com.mpr.ws.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by wagner on 26/08/15.
 */
public class NumberUtils {

    private static Locale ptBR = new Locale("pt", "BR");
    private static final NumberFormat numberFormat = new DecimalFormat("#,##0.00");


    public static String formatPTbr(Double valorConsulta) {
        return numberFormat.format(valorConsulta);
    }

    public static NumberFormat getFormat(){
        return numberFormat;
    }
}
