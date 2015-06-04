package com.fmf.mypoem.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;

import com.fmf.mypoem.R;
import com.fmf.mypoem.data.MyPoem;
import com.fmf.mypoem.data.MyPoemDao;
import com.fmf.mypoem.data.PoemDao;
import com.fmf.mypoem.fragment.BaseDetailFragment;
import com.fmf.mypoem.fragment.PoemDetailFragment;
import com.fmf.mypoem.fragment.RhythmDetailFragment;
import com.fmf.mypoem.model.Poem;
import com.fmf.mypoem.poem.PoemLog;

import java.util.List;

public class DetailActivity extends BaseActivity {
    private FragmentManager fm;

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);

        fm = getFragmentManager();

        handleIntent(getIntent());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null) {
            long id = intent.getLongExtra(BaseDetailFragment.ARG_ID, 0);
            final String table = intent.getStringExtra(BaseDetailFragment.ARG_TABLE);
            PoemLog.i(this, "id=" + id + ", table=" + table);

            if (id > 0) {
                FragmentTransaction ft = fm.beginTransaction();
                BaseDetailFragment fragment = null;
                if (MyPoem.Poem.TABLE_NAME.equals(table)) {
                    fragment = PoemDetailFragment.newInstance(id);
                } else if (MyPoem.Rhythm.TABLE_NAME.equals(table)) {
                    fragment = RhythmDetailFragment.newInstance(id);
                }
                int containerId = R.id.fl_container;
                ft.add(containerId, fragment);
                ft.commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
//            case R.id.action_:
//
//                break;

            default:
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }

}
