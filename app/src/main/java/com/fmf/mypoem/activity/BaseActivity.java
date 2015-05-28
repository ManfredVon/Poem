package com.fmf.mypoem.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.fmf.mypoem.R;

/**
 * Created by fmf on 15/5/27.
 */
public class BaseActivity extends ActionBarActivity {
//    protected LayoutInflater mInflater;
//    private Toolbar mTall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onBeforeSetContentLayout();

        if (getLayoutRes() != 0) {
            setContentView(getLayoutRes());
        }

        onInit(savedInstanceState);
    }

    private void initActionBar() {
        // toolbar.xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    protected void onInit(Bundle savedInstanceState) {
        if (hasActionBar()) {
            initActionBar();
        }
    }

    protected void onBeforeSetContentLayout() {
        if (!hasActionBar()) {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }

    protected boolean hasActionBar() {
        return true;
    }

    protected int getLayoutRes() {
        return 0;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                break;
//
//            default:
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
