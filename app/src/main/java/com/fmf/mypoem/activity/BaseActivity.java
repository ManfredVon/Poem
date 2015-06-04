package com.fmf.mypoem.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.fmf.mypoem.R;
import com.fmf.mypoem.poem.PoemLog;

/**
 * Created by fmf on 15/5/27.
 */
public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PoemLog.i(this, "onCreate");

        onBeforeSetContentLayout();

        if (getLayoutRes() != 0) {
            setContentView(getLayoutRes());
        }

        onInit(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        PoemLog.i(this, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        PoemLog.i(this, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        PoemLog.i(this, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PoemLog.i(this, "onDestroy");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        PoemLog.i(this, "onRestoreInstanceState");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        PoemLog.i(this, "onSaveInstanceState");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        PoemLog.i(this, "onNewIntent");
        if(intent != null){
            PoemLog.i(this, "intent="+intent.getAction());
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        PoemLog.i(this, "onConfigurationChanged");
    }

    private void initActionBar() {
        // toolbar.xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    protected void onInit(Bundle savedInstanceState) {
        if (hasToolBar()) {
            initActionBar();
        }
    }

    protected void onBeforeSetContentLayout() {
        if(hasToolBar()){
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
//            requestWindowFeature(Window.)
        }
    }

    protected boolean hasToolBar() {
        return false;
    }

    protected int getLayoutRes() {
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                break;

            case R.id.action_settings:
                takeActionSettings();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void takeActionSettings() {
        Toast.makeText(this, getString(R.string.action_settings), Toast.LENGTH_SHORT).show();
    }


}
