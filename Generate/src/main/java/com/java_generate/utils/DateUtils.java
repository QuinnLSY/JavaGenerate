package com.java_generate.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：单纯同学
 * @ Date：2024-08-04-23:10
 * @ Description：时间工具
 */

public class DateUtils {
    public static final String YY_MM_DD = "yyyy-MM-dd";
    public static final String YY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYgMMgDD = "yyyy/MM/dd";
    public static final String YYMMDD = "yyyyMMdd";

    public static String format(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date parse(String date, String pattern) throws ParseException {
        new SimpleDateFormat(pattern).parse(date);
        return null;
    }
}
