package com.fmf.mypoem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.fmf.mypoem.R;
import com.fmf.mypoem.data.MyPoem;
import com.fmf.mypoem.data.MyPoemDao;
import com.fmf.mypoem.model.Poem;

public class DetailActivity extends Activity {
    private TextView tvTitle;
    private TextView tvSubtitle;
    private TextView tvContent;
    private TextView tvAuthor;
    private TextView tvCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();

        handleIntent(getIntent());
    }

    private void initViews() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvSubtitle = (TextView) findViewById(R.id.tv_subtitle);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvAuthor = (TextView) findViewById(R.id.tv_author);
        tvCreated = (TextView) findViewById(R.id.tv_created);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null){
            long id = intent.getLongExtra(MyPoem.Poem._ID, 0);
            if (id > 0){
                new AsyncTask<Long, Void, Poem>(){

                    @Override
                    protected Poem doInBackground(Long... params) {
                        long id = params[0];
                        return new MyPoemDao(DetailActivity.this).get(id);
                    }

                    @Override
                    protected void onPostExecute(Poem poem) {
                        setText(poem);
                    }
                }.execute(id);
            }
        }
    }

    private void setText(Poem poem) {
        String title = poem.getTitle();
        String subtitle = poem.getSubtitle();
        String content = poem.getContent();
        String author = poem.getAuthor();
        String created = poem.getCreated();

        tvTitle.setText(title);
        tvSubtitle.setText(subtitle);
        tvContent.setText(content);
        tvAuthor.setText(author);
        tvCreated.setText(created);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
}
