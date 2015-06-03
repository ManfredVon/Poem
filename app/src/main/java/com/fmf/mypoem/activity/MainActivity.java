package com.fmf.mypoem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.fmf.mypoem.R;

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
