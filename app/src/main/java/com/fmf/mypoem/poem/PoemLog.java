package com.fmf.mypoem.poem;

import android.util.Log;

/**
 * Created by fmf on 15/4/2.
 */
public class PoemLog {
    private static final String POEM_LOG = "PoemLog";

    public static void v(String msg){
        Log.v(POEM_LOG, msg);
    }
    public static void d(String msg){
        Log.d(POEM_LOG, msg);
    }
    public static void i(String msg){
        Log.i(POEM_LOG, msg);
    }
    public static void w(String msg){
        Log.w(POEM_LOG, msg);
    }
    public static void e(String msg){
        Log.e(POEM_LOG, msg);
    }
}
