package br.com.mpr.ws.utils;

import org.springframework.util.StringUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    private static String DEFAULT_DATE_PATTERN = "dd/MM/yyyy";
    private static String DDMM = "dd/MM";
    private static String DATE_MES_DESC_PATTERN = "MMMMM/yyyy";
    private static String MES_DESC_PATTERN = "MMMMM";
    private static String DEFAULT_TIME_PATTERN = "HH:mm";
    private static String DEFAULT_DATETIME_PATTERN = DEFAULT_DATE_PATTERN + " " + DEFAULT_TIME_PATTERN;
    private static String DEFAULT_FULLDATETIME_PATTERN = DEFAULT_DATE_PATTERN + " " + DEFAULT_TIME_PATTERN + ":ss";
    private static String DEFAULT_TIME_MASK = "([0-1][0-9]|[2][0-3]):([0-5][0-9])";
    private static final long DIFF_IN_DAYS_FACTOR = 24 * 60 * 60 * 1000;

    // -------------------------------------------------------------------------------------
    // MÉTODOS PARA MONTAR UM DATE BASEADO EM UMA STRING
    // -------------------------------------------------------------------------------------
    public static final Date getDate(String value) {
        return getDate(value, DEFAULT_DATE_PATTERN);
    }

    public static final Date getDate(String value, String pattern) {
        try {
            return DateTimeParser.parse(value, pattern);
        } catch (ParseException e) {
            return null;
        }
    }

    public static final Date getTime(String value) {
        return getTime(value, DEFAULT_TIME_PATTERN);
    }

    public static final Date getTime(String value, String pattern) {
        try {
            return DateTimeParser.parse(value, pattern);
        } catch (ParseException e) {
            return null;
        }
    }

    public static final Date getDateTime(String value) {
        return getTime(value, DEFAULT_DATETIME_PATTERN);
    }

    public static final Date getDateTime(String value, String pattern) {
        try {
            return DateTimeParser.parse(value, pattern);
        } catch (ParseException e) {
            return null;
        }
    }

    public static final Date getFullDateTime(String value) {
        return getTime(value, DEFAULT_FULLDATETIME_PATTERN);
    }

    public static final Date getFullDateTime(String value, String pattern) {
        try {
            return DateTimeParser.parse(value, pattern);
        } catch (ParseException e) {
            return null;
        }
    }

    // -------------------------------------------------------------------------------------
    // MÉTODOS PARA PEGAR UMA STRING FORMATADA BASEADA EM UM DATE
    // -------------------------------------------------------------------------------------
    public static final String formatDate(Date value) {
        return formatDate(value, DEFAULT_DATE_PATTERN);
    }
    public static final String formatDateDDMM(Date value){
        return formatDate(value, DDMM);
    }

    public static final String formatDate(Date value, String pattern) {
        return DateTimeParser.format(value, pattern);
    }

    public static final String formatDateMesAno(Date date) {
        return formatDateTime(date, DATE_MES_DESC_PATTERN);
    }

    public static final String formatDateTime(Date value) {
        return formatDateTime(value, DEFAULT_DATETIME_PATTERN);
    }

    public static final String formatDateTime(Date value, String pattern) {
        return DateTimeParser.format(value, pattern);
    }

    public static final String formatFullDateTime(Date value) {
        return formatFullDateTime(value, DEFAULT_FULLDATETIME_PATTERN);
    }

    public static final String formatFullDateTime(Date value, String pattern) {
        return DateTimeParser.format(value, pattern);
    }

    public static final String formatTime(Date value) {
        return formatTime(value, DEFAULT_TIME_PATTERN);
    }

    public static final String formatTime(Date value, String pattern) {
        return DateTimeParser.format(value, pattern);
    }

    // -------------------------------------------------------------------------------------
    // MÉTODOS UTILITÁRIOS PARA MANIPULAÇÃO DE DATAS
    // -------------------------------------------------------------------------------------

    /**
     * volta um date, com a parte do time zerado, muito util para buscas com date
     */
    public static final Date getPureDate(Date date) {
        return getDate(formatDate(date));
    }

    public static final Date addDays(Date date, int days) {
        if (date == null)
            return null;
        return new Date(date.getTime() + DIFF_IN_DAYS_FACTOR * days);
    }

    public static final Date addWorkingDays(Date date, int days, Set<Date> holidays) {
        if (date == null)
            return null;
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        for (int i = 1; i <= days; i++) {
            Date workingDate = addDays(date, i);
            if (!isWorkingDate(workingDate, holidays)) {
                days++;
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return cal.getTime();
    }

    public static final Date addMonths(Date date, int months) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    public static final Date addSeconds(Date date, int seconds) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.SECOND, seconds);
        return cal.getTime();
    }

    public static int getYear(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static int getDays(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getWeek(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static final Date getTomorrow() {
        return addDays(getPureDate(new Date()), +1);
    }

    public static final Date getYesterday() {
        return addDays(getPureDate(new Date()), -1);
    }

    public static final int ageInYears(Date birthDate) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(birthDate);
        Calendar now = new GregorianCalendar();
        int res = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
        if ((cal.get(Calendar.MONTH) > now.get(Calendar.MONTH)) || (cal.get(Calendar.MONTH) == now.get(Calendar.MONTH) && cal.get(Calendar.DAY_OF_MONTH) > now.get(Calendar.DAY_OF_MONTH))) {
            res--;
        }
        return res;
    }

    public static final int ageInMonths(Date birthDate) {
        Calendar birthDateCalendar = new GregorianCalendar();
        birthDateCalendar.setTime(birthDate);

        Calendar currentDate = Calendar.getInstance();
        int month = birthDateCalendar.get(Calendar.MONTH);
        int day = birthDateCalendar.get(Calendar.DAY_OF_MONTH);

        int diffInYears = currentDate.get(Calendar.YEAR) - birthDateCalendar.get(Calendar.YEAR);
        int diffInMonths = 0;
        int diffInDays = 0;
        if (currentDate.before(new GregorianCalendar(currentDate.get(Calendar.YEAR), month, day))) {
            // dataNascimento com o mês/dia pós data atual
            diffInYears--;
            diffInMonths = (12 - (birthDateCalendar.get(Calendar.MONTH) + 1)) + (birthDateCalendar.get(Calendar.MONTH));
            if (day > currentDate.get(Calendar.DAY_OF_MONTH)) {
                diffInDays = day - currentDate.get(Calendar.DAY_OF_MONTH);
            } else if (day < currentDate.get(Calendar.DAY_OF_MONTH)) {
                diffInDays = currentDate.get(Calendar.DAY_OF_MONTH) - day;
            }
        } else if (currentDate.after(new GregorianCalendar(currentDate.get(Calendar.YEAR), month, day))) {
            diffInMonths = (currentDate.get(Calendar.MONTH) - (birthDateCalendar.get(Calendar.MONTH)));
            if (day > currentDate.get(Calendar.DAY_OF_MONTH))
                diffInDays = day - currentDate.get(Calendar.DAY_OF_MONTH);
            else if (day < currentDate.get(Calendar.DAY_OF_MONTH)) {
                diffInDays = currentDate.get(Calendar.DAY_OF_MONTH) - day;
            }
        }
        // corrige o mês, caso não tenha completado (pelos dias)
        if (diffInDays > 0 && diffInMonths > 0) {
            diffInMonths--;
        }
        return diffInYears * 12 + diffInMonths;
    }

    public static Date[] getInitEndYearDates(Date referenceDate) {
        if (referenceDate == null)
            return null;
        String year = "" + getYear(referenceDate);
        Date firstDay = getDate("01/01/" + year);
        Date lastDay = getDate("31/12/" + year);
        return new Date[] { firstDay, lastDay };
    }

    public static Date[] getInitEndOfMonthDates(Date referenceDate) {
        if (referenceDate == null)
            return null;
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(referenceDate);
        int firstDayOfMonth = cal.getActualMinimum(GregorianCalendar.DAY_OF_MONTH);
        int lastDayOfMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        Date firstDay = getDate("" + firstDayOfMonth + "/" + month + "/" + year);
        Date lastDay = getDate("" + lastDayOfMonth + "/" + month + "/" + year);
        return new Date[] { firstDay, lastDay };
    }

    public static Date getXmlDate(XMLGregorianCalendar xmlDate) {
        Date data = null;
        if (xmlDate != null) {
            Calendar cal = new GregorianCalendar();
            cal.set(Calendar.DAY_OF_MONTH, xmlDate.getDay());
            cal.set(Calendar.MONTH, xmlDate.getMonth() - 1);
            cal.set(Calendar.YEAR, xmlDate.getYear());
            cal.set(Calendar.HOUR_OF_DAY, xmlDate.getHour());
            cal.set(Calendar.MINUTE, xmlDate.getMinute());
            cal.set(Calendar.SECOND, xmlDate.getSecond());
            cal.set(Calendar.MILLISECOND, xmlDate.getMillisecond());
            data = cal.getTime();
        }
        return data;
    }

    // -------------------------------------------------------------------------------------
    // MÉTODOS PARA VALIDAÇÃO/COMPARAÇÃO
    // -------------------------------------------------------------------------------------
    public static final boolean isValid24HourTime(String time) {
        if (StringUtils.isEmpty(time))
            return false;
        return time.matches(DEFAULT_TIME_MASK);
    }

    public static final boolean isValidDate(String date) {
        return getDate(date) != null;
    }

    public static final boolean isValidDateTime(String date) {
        return getDateTime(date) != null;
    }

    // Retorna
    // 0 : DATE1 = DATE2
    // 1 : DATE1 > DATE2
    // -1 : DATE1 < DATE2
    public static final int compareDates(Date date1, Date date2) {
        if (date1 == null) {
            if (date2 == null) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if (date2 == null) {
                return 1;
            } else {
                return getPureDate(date1).compareTo(getPureDate(date2));
            }
        }
    }

    // Retorna
    // 0 : TIME1 = TIME2
    // 1 : TIME1 > TIME2
    // -1 : TIME1 < TIME2
    public static int compareTimes(Date time1, Date time2) {
        if (time1 == null) {
            if (time2 == null) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if (time2 == null) {
                return 1;
            } else {
                return time1.compareTo(time2);
            }
        }
    }

    public static boolean isEqual(Date date1, Date date2) {
        return compareDates(date1, date2) == 0;
    }

    public static boolean isGreater(Date date1, Date date2) {
        return compareDates(date1, date2) > 0;
    }

    public static boolean isXmlGreater(XMLGregorianCalendar xmlDate1, XMLGregorianCalendar xmlDate2) {
        Date date1 = getXmlDate(xmlDate1);
        Date date2 = getXmlDate(xmlDate2);
        return compareDates(date1, date2) > 0;
    }

    public static boolean isGreaterEqual(Date date1, Date date2) {
        return compareDates(date1, date2) >= 0;
    }

    public static boolean isLesser(Date date1, Date date2) {
        return compareDates(date1, date2) < 0;
    }

    public static boolean isLesserEqual(Date date1, Date date2) {
        return compareDates(date1, date2) <= 0;
    }

    public static boolean isEqualTime(Date date1, Date date2) {
        return compareTimes(date1, date2) == 0;
    }

    public static boolean isGreaterTime(Date date1, Date date2) {
        return compareTimes(date1, date2) > 0;
    }

    public static boolean isGreaterEqualTime(Date date1, Date date2) {
        return compareTimes(date1, date2) >= 0;
    }

    public static boolean isLesserTime(Date date1, Date date2) {
        return compareTimes(date1, date2) < 0;
    }

    public static boolean isLesserEqualTime(Date date1, Date date2) {
        return compareTimes(date1, date2) <= 0;
    }

    public static boolean isToday(Date date1) {
        return isEqual(new Date(), date1);
    }

    public static boolean isWorkingDate(Date date, Set<Date> holidays) {
        if (date == null)
            return false;
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || (holidays != null && holidays.contains(getPureDate(date)))) {
            return false;
        }
        return true;
    }

    public static long getDiffInDays(Date dateIni, Date dateFinal) {
        long diff = dateFinal.getTime() - dateIni.getTime();
        long diffInDias = (diff / 1000) / 60 / 60 / 24;
        return diffInDias;
    }

    public static double getDiffInHours(Date dateIni, Date dateFinal) {
        long diff = dateFinal.getTime() - dateIni.getTime();
        double diffInHours = (diff / 1000) / 60 / 60;
        return diffInHours;
    }

    public static String formatIntervalInTime(final long timeElapsed) {
        final long hr = TimeUnit.MILLISECONDS.toHours(timeElapsed);
        final long min = TimeUnit.MILLISECONDS.toMinutes(timeElapsed - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(timeElapsed - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
        final long ms = TimeUnit.MILLISECONDS.toMillis(timeElapsed - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(sec));
        return String.format("%d:%02d:%02d.%03d", hr, min, sec, ms);
    }

    public static String formatMonthDate(Date date) {
        return formatDateTime(date, MES_DESC_PATTERN);
    }

    public static Calendar getYestarday(Date data) {
        Date ontem = DateUtils.addDays(data, -1);
        Calendar dataIni = Calendar.getInstance();
        dataIni.setTime(ontem);
        dataIni.set(Calendar.HOUR_OF_DAY,23);
        dataIni.set(Calendar.MINUTE,59);
        dataIni.set(Calendar.SECOND,59);
        return dataIni;
    }

    public static Calendar getTomorrow(Date data) {
        Date amanha = DateUtils.addDays(data, +1);
        Calendar dataFim = Calendar.getInstance();
        dataFim.setTime(amanha);
        dataFim.set(Calendar.HOUR_OF_DAY,0);
        dataFim.set(Calendar.MINUTE,0);
        dataFim.set(Calendar.SECOND,0);
        return dataFim;
    }

    public static Calendar getFirstDayOfActualMonth(){
        Date now = new Date();
        Calendar firstDay = Calendar.getInstance();
        firstDay.set(Calendar.DATE, 1);
        firstDay.set(Calendar.MONTH,DateUtils.getMonth(now));
        firstDay.set(Calendar.YEAR,DateUtils.getYear(now));
        firstDay.set(Calendar.HOUR_OF_DAY,0);
        firstDay.set(Calendar.MINUTE,0);
        firstDay.set(Calendar.SECOND,0);
        return firstDay;
    }

    public static Calendar getFirstDayOfNextMonth(){
        Date now = new Date();
        Calendar firstDayNextMonth = Calendar.getInstance();
        firstDayNextMonth.set(Calendar.DATE, 1);
        firstDayNextMonth.set(Calendar.MONTH,DateUtils.getMonth(now)+1);
        firstDayNextMonth.set(Calendar.YEAR,DateUtils.getYear(now));
        firstDayNextMonth.set(Calendar.HOUR_OF_DAY,23);
        firstDayNextMonth.set(Calendar.MINUTE,59);
        firstDayNextMonth.set(Calendar.SECOND,59);
        return firstDayNextMonth;
    }

}
