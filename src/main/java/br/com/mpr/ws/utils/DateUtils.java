package br.com.mpr.ws.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wagner on 04/11/15.
 */
public class DateUtils {

    private static final Log LOG = LogFactory.getLog(DateUtils.class);
    private static final Locale locale = new Locale("pt", "BR");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdfddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat sdfddMMyyyyHHmmss = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static SimpleDateFormat sdfyyyyMMddTHHmmss = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static SimpleDateFormat sdfyyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static SimpleDateFormat sdfyyyymmdd = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdfhhmmssSSS = new SimpleDateFormat("HH:mm:ss.SSS");
    private static Date lastDayActualWeek;

    public static Date now(){
        Calendar c = Calendar.getInstance(locale);
        return c.getTime();
    }


    public static Calendar nowCalendar() {
        Calendar c = Calendar.getInstance(locale);
        return c;
    }

    public static String formatyyyyMMdd(Date date){
        return sdf.format(date);
    }

    public static String formatyyyyMMddTHHmmss(Date date){
        return sdfyyyyMMddTHHmmss.format(date);
    }

    public static String formatddMMyyyyHHmmss(Date date){
        return sdfddMMyyyyHHmmss.format(date);
    }

    public static Date getFirstDayActualWeek() {
        Calendar c = nowCalendar();
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();
    }

    public static Date getLastDayActualWeek() {
        Calendar c = nowCalendar();
        c.set(Calendar.DAY_OF_WEEK,c. getActualMaximum(Calendar.DAY_OF_WEEK));
        return c.getTime();
    }

    public static String formatddMMyyyy(Date data) {
        return sdfddMMyyyy.format(data);
    }

    public static String formatDateISO8601(Date date) {
        return sdfyyyymmdd.format(date);
    }

    public static String formatHourISO8601(Date date) {
        return sdfhhmmssSSS.format(date);
    }

    public static Date getDateyyyymmddhhmm(String s) {
        try {
            return sdfyyyyMMddHHmm.parse(s);
        } catch (ParseException e) {
            LOG.error("Erro no parse da data", e);
        }
        return null;
    }

    public static boolean isLesser(Date d1, Date d2) {
        return d1.compareTo(d2) < 0;
    }

    public static boolean isLesserEqual(Date d1, Date d2) {
        return d1.compareTo(d2) <= 0;
    }

    public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static Date addUtilDays(Date date, int days) {
        int adjustDays = days;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        for (int i = 1; i <= days; i++){
            c.add(Calendar.DATE, 1);
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                c.add(Calendar.DATE, 1);
                i--;
            }

        }
        return c.getTime();
    }

    public static long getDiffInDays(Date d1, Date d2) {
       long time = d2.getTime() - d1.getTime();
       return time / 1000 / 60 / 60 / 24;
    }

    public static Date getDateddMMyyyy(String s) {
        try {
            return sdfddMMyyyy.parse(s);
        } catch (ParseException e) {
            LOG.error("Erro no parse da data", e);
        }
        return null;
    }
}