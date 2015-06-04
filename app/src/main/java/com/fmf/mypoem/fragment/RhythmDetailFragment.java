package com.fmf.mypoem.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.fmf.mypoem.R;
import com.fmf.mypoem.data.RhythmDao;
import com.fmf.mypoem.model.Rhythm;

public class RhythmDetailFragment extends BaseDetailFragment<Rhythm> {
    private TextView tvName;
    private TextView tvAlias;
    private TextView tvIntro;
    private TextView tvMeter;
    private TextView tvSample;
    private TextView tvComment;

    public static RhythmDetailFragment newInstance(long id) {
        RhythmDetailFragment fragment = new RhythmDetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ID, id);
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
        String name = rhythm.getName();
        String alias = rhythm.getAlias();
        String intro = rhythm.getIntro();
        String metre = rhythm.getMetre();
        String sample = rhythm.getSample();
        String comment = rhythm.getComment();

        tvName.setText(name);
        tvAlias.setText(alias);
        tvIntro.setText(intro);
        tvMeter.setText(metre);
        tvSample.setText(sample);
        tvComment.setText(comment);
    }

    @Override
    protected Rhythm onLoadDetail(long id) {
        return new RhythmDao(getActivity()).get(id);
    }

    @Override
    protected void onPostLoadDetail(Rhythm rhythm) {
        bindViewData(rhythm);
    }

    @Override
    protected String onCreateShareText(Rhythm rhythm) {
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
}
