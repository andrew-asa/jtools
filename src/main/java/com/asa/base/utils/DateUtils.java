package com.asa.base.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author andrew_asa
 * @date 2022/6/13.
 */
public class DateUtils {

    public static final String YY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";
    public static final String YYMMDDHHMMSS = "yyyyMMddhhmmss";

    public static String getYY_MM_DD_HH_MM_SS() {

        return formatDate(YY_MM_DD_HH_MM_SS);
    }

    public static String formatDate(String pattern) {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat(pattern);
        return ft.format(date);
    }
}
