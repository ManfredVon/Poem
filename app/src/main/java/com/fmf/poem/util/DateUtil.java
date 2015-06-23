package com.fmf.poem.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fmf on 15/4/12.
 */
public class DateUtil {
    public static final String MYPOME_DATE_PATTERN = "yyyy年M月d日";
    public static final String SQLITE_DATE_PATTERN = "yyyy-MM-dd";
    public static final String MYPOME_DATETIME_PATTERN = "yyyy年M月d日 HH:mm:ss";
    public static final String SQLITE_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String formatDateToMyPome(Date date) {
        return formatDate(date, MYPOME_DATE_PATTERN);
    }

    public static String formatDateToSqlite(Date date) {
        return formatDate(date, SQLITE_DATE_PATTERN);
    }
    public static String formatDatetimeToMyPome(Date date) {
        return formatDate(date, MYPOME_DATETIME_PATTERN);
    }

    public static String formatDatetimeToSqlite(Date date) {
        return formatDate(date, SQLITE_DATETIME_PATTERN);
    }

    public static String formatDateToMyPome(int year, int month, int day) {
        return new StringBuilder()
                .append(year).append("年")
                .append(month + 1).append("月")
                .append(day).append("日")
                .toString();
    }

    public static String formatDateToSqlite(int year, int month, int day) {
        return new StringBuilder()
                .append(year).append("-")
                .append(month + 1).append("-")
                .append(day).append("-")
                .toString();
    }

}
