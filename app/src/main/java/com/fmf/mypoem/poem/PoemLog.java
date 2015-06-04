package com.fmf.mypoem.poem;

import android.util.Log;

/**
 * Created by fmf on 15/4/2.
 */
public class PoemLog {
    private static final String POEM_LOG = "PoemLog";

    public static void v(String msg) {
        Log.v(POEM_LOG, msg);
    }

    public static void d(String msg) {
        Log.d(POEM_LOG, msg);
    }

    public static void i(String msg) {
        Log.i(POEM_LOG, msg);
    }

    public static void w(String msg) {
        Log.w(POEM_LOG, msg);
    }

    public static void e(String msg) {
        Log.e(POEM_LOG, msg);
    }

    public static void v(Object obj, String msg) {
       v(concatObjAndMsg(obj, msg));
    }
    public static void d(Object obj, String msg) {
       d(concatObjAndMsg(obj, msg));
    }
    public static void i(Object obj, String msg) {
       i(concatObjAndMsg(obj, msg));
    }
    public static void w(Object obj, String msg) {
       w(concatObjAndMsg(obj, msg));
    }
    public static void e(Object obj, String msg) {
       e(concatObjAndMsg(obj, msg));
    }

    private static String concatObjAndMsg(Object obj, String msg){
        return obj.getClass().getSimpleName() + ": " + msg;
    }
}
