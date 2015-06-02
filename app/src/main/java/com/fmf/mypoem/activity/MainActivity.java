package com.fmf.mypoem.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fmf.mypoem.R;
import com.fmf.mypoem.util.PoemLog;

public class MainActivity extends BaseActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 1000; // 毫秒

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);

        splash(SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected boolean hasActionBar() {
        return false;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    private void splash(int splashDisplayLength) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, PoemActivity.class);
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
            }
        }, splashDisplayLength);
    }
}
