package br.com.mpr.ws.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class DateUtilsTest {

    @Test
    public void addUtilDaysTest1() {
        Calendar c = Calendar.getInstance();
        c.set(2019,Calendar.JANUARY,01);
        Date date = DateUtils.addUtilDays(c.getTime(),15);
        Assert.assertEquals("22/01/2019",DateUtils.formatddMMyyyy(date));
    }

    @Test
    public void addUtilDaysTest2() {
        Calendar c = Calendar.getInstance();
        c.set(2019,Calendar.JANUARY,01);
        Date date = DateUtils.addUtilDays(c.getTime(),2);
        Assert.assertEquals("03/01/2019",DateUtils.formatddMMyyyy(date));
    }

    @Test
    public void addUtilDaysTest3() {
        Calendar c = Calendar.getInstance();
        c.set(2019,Calendar.JANUARY,01);
        Date date = DateUtils.addUtilDays(c.getTime(),5);
        Assert.assertEquals("08/01/2019",DateUtils.formatddMMyyyy(date));
    }
}