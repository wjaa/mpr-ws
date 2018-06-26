package br.com.mpr.ws.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

public class DateTimeParser {
    private static final Log log = LogFactory.getLog(DateTimeParser.class);

    private static final ThreadLocal<Map<String, DateFormat>> PARSERS = new ThreadLocal<Map<String, DateFormat>>() {
        protected Map<String, DateFormat> initialValue() {
            return new Hashtable<String, DateFormat>();
        }
    };

    private static final DateFormat getParser(final String pattern) {
        Map<String, DateFormat> parserMap = PARSERS.get();
        DateFormat df = parserMap.get(pattern);
        if (null == df) {
            if (log.isDebugEnabled()) {
                log.debug("Date Format Pattern " + pattern + " not found in current thread:" + Thread.currentThread().getId());
            }
            // if parser for the same pattern does not exist yet, create one and
            // save it into map
            df = new SimpleDateFormat(pattern);
            parserMap.put(pattern, df);
        }
        return df;
    }

    public static final Date parse(final String date, final String pattern) throws ParseException {
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(pattern))
            return null;
        try {
            return getParser(pattern).parse(date);
        } catch (Exception e) {
            throw new ParseException("Data invalida, formato incorreto!", 0);
        }
    }

    public static final long parseLongDate(final String date, final String pattern) throws ParseException {
        return parse(date, pattern).getTime();
    }

    public static final String format(final Date date, final String pattern) {
        if (date == null)
            return "";
        return getParser(pattern).format(date);
    }

    public static final String format(final long date, final String pattern) {
        if (date == 0)
            return "";
        return getParser(pattern).format(new Date(date));
    }

}
