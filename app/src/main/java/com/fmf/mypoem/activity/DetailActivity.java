package com.fmf.mypoem.activity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.fmf.mypoem.model.Poem;

import java.util.List;

public class DetailActivity extends BaseActivity {
    private TextView tvTitle;
    private TextView tvSubtitle;
    private TextView tvContent;
    private TextView tvAuthor;
    private TextView tvCreated;
    private String shareText;

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);

        initViews();

        handleIntent(getIntent());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_detail;
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
        if (intent != null) {
            long id = intent.getLongExtra(MyPoem.Poem._ID, 0);
            if (id > 0) {
                new AsyncTask<Long, Void, Poem>() {

                    @Override
                    protected Poem doInBackground(Long... params) {
                        long id = params[0];
                        return new PoemDao(DetailActivity.this).get(id);
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

        StringBuilder sb = new StringBuilder();
        final String LF = "\n";
        final String COMMA = "，";
        sb.append(title).append(LF);
        if (!TextUtils.isEmpty(subtitle)){
            sb.append(subtitle).append(LF);
        }
        sb.append(content).append(LF);
        sb.append(author).append(COMMA).append(created);
        this.shareText = sb.toString();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        // Set up ShareActionProvider's default share intent
        int shareItemId = R.id.action_share;
        MenuItem shareItem = menu.findItem(shareItemId);
        ShareActionProvider shareProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        Intent shareIntent = getShareIntent();
        if (shareIntent != null){
            shareProvider.setShareIntent(shareIntent);
        } else {
            menu.removeItem(shareItemId);
        }

        return true;
    }

    @Nullable
    private Intent getShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/*");

        //检查手机上是否存在可以处理这个动作的应用
        List<ResolveInfo> infoList = getPackageManager().queryIntentActivities(shareIntent, 0);
        if (!infoList.isEmpty()){
            shareIntent.putExtra(Intent.EXTRA_TEXT, this.shareText);
            return shareIntent;
        }

        return null;
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
