package com.fmf.poem.util;

/**
 * Created by fmf on 15/6/2.
 */
public class StringUtil {
    public static String wrap(String text, String wrap){
        return wrap + text + wrap;
    }

    public static String wrap(String prefix, String text, String suffix){
        return prefix + text + suffix;
    }

    public static String prefix(String prefix, String text){
        return prefix + text;
    }

    public static String suffix(String text, String suffix){
        return text + suffix;
    }
}
