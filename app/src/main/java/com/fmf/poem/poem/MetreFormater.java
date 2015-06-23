package com.fmf.poem.poem;

import android.text.SpannableStringBuilder;

import com.fmf.poem.util.SpanUtil;

/**
 * Created by fmf on 15/6/9.
 */
public class MetreFormater {
    // “○”标平声 “●”标仄声 “⊙”标应平可仄 “◎”标应仄可平
    // “△”标平韵 “▲”标仄韵 “，”“。”标句 “、”标读
    public static final char CHAR_PING_SHENG_SRC = '○';
    public static final char CHAR_PING_SHENG_DEST = '平';
    public static final char CHAR_ZE_SHENG_SRC = '●';
    public static final char CHAR_ZE_SHENG_DEST = '仄';
    public static final char CHAR_PING_ZHONG_SRC = '⊙';
    public static final char CHAR_PING_ZHONG_DEST = '中';
    public static final char CHAR_ZE_ZHONG_SRC = '◎';
//    public static final char CHAR_ZE_ZHONG_DEST = '中';
    public static final char CHAR_ZE_ZHONG_DEST = '㊥';
    public static final char CHAR_PING_YUN_SRC = '△';
    public static final char CHAR_PING_YUN_DEST = '韵';
    public static final char CHAR_ZE_YUN_SRC = '▲';
    public static final char CHAR_ZE_YUN_DEST = '韵';

//    public static final SparseArray<Character> MAP = new SparseArray<>(6);
//    static {
//        MAP.put(CHAR_PING_SHENG_SRC, CHAR_PING_SHENG_DEST);
//    }

    public static CharSequence formatSpan(String text) {
        return formatString(replace(text));
    }

    private static CharSequence formatString(String text) {
        final StringBuilder sb = new StringBuilder(text);
        final char[] chars = text.toCharArray();
        final SpannableStringBuilder ssb = new SpannableStringBuilder();

        for (int i = 0, len = chars.length; i < len; i++) {
            char c = sb.charAt(i);
            switch (c){
                case CHAR_PING_ZHONG_SRC:
                    ssb.append(SpanUtil.decorateUnderline(String.valueOf(CHAR_PING_ZHONG_DEST)));
                    break;
                case CHAR_PING_YUN_SRC:
                    ssb.append(SpanUtil.decorateUnderline(String.valueOf(CHAR_PING_YUN_DEST)));
                    break;
                default:
                    ssb.append(c);
            }
        }
        return ssb.subSequence(0, ssb.length());
    }

    private static String replace(String text) {
        if (text == null){
            return null;
        }
        final StringBuilder sb = new StringBuilder(text);
        final char[] chars = text.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            char c = sb.charAt(i);
            switch (c){
                case CHAR_PING_SHENG_SRC:
                    sb.setCharAt(i, CHAR_PING_SHENG_DEST);
                    break;
                case CHAR_ZE_SHENG_SRC:
                    sb.setCharAt(i, CHAR_ZE_SHENG_DEST);
                    break;
                case CHAR_ZE_ZHONG_SRC:
                    sb.setCharAt(i, CHAR_ZE_ZHONG_DEST);
                    break;
                case CHAR_ZE_YUN_SRC:
                    sb.setCharAt(i, CHAR_ZE_YUN_DEST);
                    break;
            }
        }
        return sb.toString();
    }

    public static String format(String metre) {
        if (metre == null){
            return null;
        }
        final StringBuilder sb = new StringBuilder(metre);
        final char[] chars = metre.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            char c = sb.charAt(i);
            switch (c){
                case CHAR_PING_SHENG_SRC:
                    sb.setCharAt(i, CHAR_PING_SHENG_DEST);
                    break;
                case CHAR_ZE_SHENG_SRC:
                    sb.setCharAt(i, CHAR_ZE_SHENG_DEST);
                    break;
                case CHAR_PING_ZHONG_SRC:
                    sb.setCharAt(i, CHAR_PING_ZHONG_DEST);
                    break;
                case CHAR_ZE_ZHONG_SRC:
                    sb.setCharAt(i, CHAR_ZE_ZHONG_DEST);
                    break;
                case CHAR_PING_YUN_SRC:
                    sb.setCharAt(i, CHAR_PING_YUN_DEST);
                    break;
                case CHAR_ZE_YUN_SRC:
                    sb.setCharAt(i, CHAR_ZE_YUN_DEST);
                    break;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args){
        final String STR = "○○○，●○▲，●●○，○○▲ \n ○○○●●○○，●●○○○●▲";
        System.out.println(replace(STR));
    }
}
