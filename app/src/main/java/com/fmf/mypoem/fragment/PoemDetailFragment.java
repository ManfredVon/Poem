package com.fmf.mypoem.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fmf.mypoem.R;
import com.fmf.mypoem.activity.ComposeActivity;
import com.fmf.mypoem.data.PoemDao;
import com.fmf.mypoem.model.Poem;
import com.fmf.mypoem.poem.PoemConstant;
import com.fmf.mypoem.poem.PoemLog;

public class PoemDetailFragment extends BaseDetailFragment<Poem> {
    private TextView tvTitle;
    private TextView tvSubtitle;
    private TextView tvContent;
    private TextView tvAuthor;
    private TextView tvCreated;

    private ShareActionProvider shareProvider;

    public static PoemDetailFragment newInstance(long id) {
        PoemDetailFragment fragment = new PoemDetailFragment();
        Bundle args = new Bundle();
        args.putLong(PoemConstant.POEM_ID, id);
        fragment.setArguments(args);
        return fragment;
    }
    
    public PoemDetailFragment() {
        // Required empty public constructor
//        super(); // 可有可无
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_poem_detail;
    }

    @Override
    protected void onInitViews(View root) {
        tvTitle = (TextView) root.findViewById(R.id.tv_title);
        tvSubtitle = (TextView) root.findViewById(R.id.tv_subtitle);
        tvContent = (TextView) root.findViewById(R.id.tv_content);
        tvAuthor = (TextView) root.findViewById(R.id.tv_author);
        tvCreated = (TextView) root.findViewById(R.id.tv_created);
    }

    private void bindViewData(Poem poem) {
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
    protected Poem onLoadDetail(long id) {
        return new PoemDao(getActivity()).get(id);
    }

    @Override
    protected void onPostLoadDetail(Poem poem) {
        bindViewData(poem);

        setupShareIntent(poem);
    }

    @Override
    protected int onDelete(long id) {
        return new PoemDao(getActivity()).delete(id);
    }

    private String createShareText(Poem poem) {
        String title = poem.getTitle();
        String subtitle = poem.getSubtitle();
        String content = poem.getContent();
        String author = poem.getAuthor();
//        String created = poem.getCreated();

        StringBuilder sb = new StringBuilder();
        final String LF = "\n";
        final String DOT = "•";
        sb.append(title);
        if (!TextUtils.isEmpty(subtitle)) {
            sb.append(DOT).append(subtitle);
        }
        sb.append(DOT).append(author).append(LF);
        sb.append(content);

        return sb.toString();
    }

    private void setupShareIntent(Poem poem) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        final String text = createShareText(poem);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        if (shareProvider != null) {
            shareProvider.setShareIntent(shareIntent);
        } else {
            // todo: need to figure out why
            PoemLog.i(this, "shareProvider is null, but share still work !");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_poem_detail, menu);
        int shareItemId = R.id.action_share;
        MenuItem shareItem = menu.findItem(shareItemId);
        shareProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
//        shareProvider.setShareHistoryFileName(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update:
                gotoCompose();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void gotoCompose() {
        Intent intent = new Intent(getActivity(), ComposeActivity.class);
        intent.putExtra(PoemConstant.POEM_ID, id);
        startActivity(intent);
    }
}
