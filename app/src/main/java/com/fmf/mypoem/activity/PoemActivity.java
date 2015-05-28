package com.fmf.mypoem.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fmf.mypoem.R;
import com.fmf.mypoem.util.PoemLog;

public class PoemActivity extends ActionBarActivity {
    
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_poem);

//        getActionBar().setDisplayShowTitleEnabled(false);
        PoemLog.i("PoemActivity--onCreate");

        initToolbar();

        handleIntent(getIntent());
    }
    
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Title"); // before setSupportActionBar
//        toolbar.setSubtitle("subtitle");
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

//        这些通过ActionBar来设置也是一样的，注意要在setSupportActionBar(toolbar);之后，不然就报错了
//        getSupportActionBar().setTitle("标题");
//        getSupportActionBar().setSubtitle("副标题");
//        getSupportActionBar().setLogo(R.drawable.ic_launcher);

//        菜单的监听可以在toolbar里设置，也可以像ActionBar那样，通过Activity的onOptionsItemSelected回调方法来处理
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.action_settings:
//                        Toast.makeText(MainActivity.this, "action_settings", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.action_share:
//                        Toast.makeText(MainActivity.this, "action_share", Toast.LENGTH_SHORT).show();
//                        break;
//                    default:
//                        break;
//                }
//                return true;
//            }
//        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        PoemLog.i("PoemActivity--onNewIntent");
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        // 搜索
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            PoemLog.i("query: " + query);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        PoemLog.i("PoemActivity--onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        PoemLog.i("PoemActivity--onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        PoemLog.i("PoemActivity--onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PoemLog.i("PoemActivity--onDestroy");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        PoemLog.i("PoemActivity--onConfigurationChanged");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_poem, menu);

        // 关联searchable.xml配置和SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent intent = new Intent(this, DetailActivity.class);
//            intent.putExtra(MyPoem.Poem._ID, 1l);
//            startActivity(intent);
//
//            return true;
//        }

        switch (id) {
            case R.id.action_compose:
                gotoCompose();
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(this, DemoActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void gotoCompose() {
        Intent intent = new Intent(this, ComposeActivity.class);
        startActivity(intent);
    }
}