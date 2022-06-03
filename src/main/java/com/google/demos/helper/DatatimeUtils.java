package com.google.demos.helper;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

public class DatatimeUtils {

    public static Date buildDate(int year, int month, int day, int hh, int mm, int ss){

        LocalDateTime localDateTime = LocalDateTime.of(
                year,
                month,
                day,
                hh, mm, ss);

        Instant instant = localDateTime.atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        Date output = Date.from(instant);
        return output;

    }

    public static String buildDateAsString(int year, int month, int day, int hh, int mm, int ss){

        //1970-01-01T01:06:41Z
        String pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat cfrDateFormat = new SimpleDateFormat(pattern);
        return cfrDateFormat.format(buildDate(year, month, day, hh, mm, ss));
    }
}
