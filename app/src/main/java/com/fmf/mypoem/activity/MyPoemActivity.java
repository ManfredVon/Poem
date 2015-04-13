package com.fmf.mypoem.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.fmf.mypoem.R;
import com.fmf.mypoem.data.MyPoem;
import com.fmf.mypoem.util.PoemLog;

public class MyPoemActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_poem);

        PoemLog.i("MyPoemActivity--onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
//        PoemLog.i("MyPoemActivity--onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        PoemLog.i("MyPoemActivity--onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
//        PoemLog.i("MyPoemActivity--onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PoemLog.i("MyPoemActivity--onDestroy");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        PoemLog.i("MyPoemActivity--onConfigurationChanged");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_poem, menu);
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
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(MyPoem.Poem._ID, 1l);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
