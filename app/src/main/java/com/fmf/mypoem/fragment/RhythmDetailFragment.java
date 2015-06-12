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
import com.fmf.mypoem.data.MyPoem;
import com.fmf.mypoem.data.RhythmDao;
import com.fmf.mypoem.model.Rhythm;
import com.fmf.mypoem.poem.PoemFormater;
import com.fmf.mypoem.poem.PoemConstant;
import com.fmf.mypoem.poem.PoemLog;

public class RhythmDetailFragment extends BaseDetailFragment<Rhythm> {
    private TextView tvName;
    private TextView tvAlias;
    private TextView tvIntro;
    private TextView tvMeter;
    private TextView tvSample;
    private TextView tvComment;
    private ShareActionProvider shareProvider;

    private Rhythm rhythm;

    public static RhythmDetailFragment newInstance(long id) {
        RhythmDetailFragment fragment = new RhythmDetailFragment();
        Bundle args = new Bundle();
        args.putLong(PoemConstant.POEM_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    public RhythmDetailFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_rhythm_detail;
    }

    @Override
    protected void onInitViews(View root) {
        tvName = (TextView) root.findViewById(R.id.tv_name);
        tvAlias = (TextView) root.findViewById(R.id.tv_alias);
        tvIntro = (TextView) root.findViewById(R.id.tv_intro);
        tvMeter = (TextView) root.findViewById(R.id.tv_metre);
        tvSample = (TextView) root.findViewById(R.id.tv_sample);
        tvComment = (TextView) root.findViewById(R.id.tv_comment);
    }

    private void bindViewData(Rhythm rhythm) {
        if (rhythm == null){
            return ;
        }

        this.rhythm = rhythm;

        String name = rhythm.getName();
        String alias = rhythm.getAlias();
        String intro = rhythm.getIntro();
        String metre = rhythm.getMetre();
        String sample = rhythm.getSample();
        String comment = rhythm.getComment();

        tvName.setText(name);
        tvAlias.setText(alias);
        tvIntro.setText(intro);
        tvMeter.setText(PoemFormater.formatMetre(metre));
        tvSample.setText(sample);
//        tvComment.setText(comment);
        final int color = getActivity().getResources().getColor(R.color.primary_translucence);
        CharSequence commentSpan = PoemFormater.formatComment(comment, color);
        tvComment.setText(commentSpan);
    }

    @Override
    protected Rhythm onLoadDetail(long id) {
        return new RhythmDao(getActivity()).get(id);
    }

    @Override
    protected void onPostLoadDetail(Rhythm rhythm) {
        bindViewData(rhythm);

        setupShareIntent(rhythm);
    }

    @Override
    protected int onDelete(long id) {
        return new RhythmDao(getActivity()).delete(id);
    }

    protected String createShareText(Rhythm rhythm) {
        String name = rhythm.getName();
        String alias = rhythm.getAlias();
        String intro = rhythm.getIntro();
        String metre = rhythm.getMetre();
        String sample = rhythm.getSample();
        String comment = rhythm.getComment();

        final String LF = "\n";
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        if (!TextUtils.isEmpty(alias)) {
            sb.append("【").append(alias).append("】").append(LF);
        }
        if (!TextUtils.isEmpty(intro)) {
            sb.append(alias).append(LF);
        }
        sb.append(metre).append(LF).append(LF);
        sb.append(sample);
        if (!TextUtils.isEmpty(comment)) {
            sb.append(LF).append(LF).append(comment);
        }

        return sb.toString();
    }

    private void setupShareIntent(Rhythm rhythm) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        final String text = createShareText(rhythm);
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

        inflater.inflate(R.menu.fragment_rhythm_detail, menu);
        int shareItemId = R.id.action_share;
        MenuItem shareItem = menu.findItem(shareItemId);
        shareProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
//        shareProvider.setShareHistoryFileName(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_compose:
                gotoCompose();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void gotoCompose() {
        Intent intent = new Intent(getActivity(), ComposeActivity.class);
//        intent.putExtra(PoemConstant.RHYTHM_ID, id);
        intent.putExtra(PoemConstant.RHYTHM, rhythm);
        startActivity(intent);

        getActivity().finish();
    }
}