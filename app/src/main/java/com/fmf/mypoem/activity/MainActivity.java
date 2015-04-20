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

public class MainActivity extends ActionBarActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 2000; // 毫秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        PoemLog.i("MainActivity--onCreate");

        splash(SPLASH_DISPLAY_LENGTH);

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

    @Override
    protected void onStart() {
        super.onStart();
//        PoemLog.i("MainActivity--onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        PoemLog.i("MainActivity--onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
//        PoemLog.i("MainActivity--onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PoemLog.i("MainActivity--onDestroy");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        PoemLog.i("MainActivity--onConfigurationChanged");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        Class<?> cls = null;
        switch (view.getId()) {

            case R.id.btn_poem:
                cls = PoemActivity.class;
                break;

            case R.id.btn_compose:
                cls = ComposeActivity.class;
                break;

            default:
        }
        startActivity(new Intent(this, cls));
    }
}
