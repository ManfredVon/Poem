package com.fmf.mypoem.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fmf.mypoem.R;
import com.fmf.mypoem.data.PoemDao;
import com.fmf.mypoem.model.Model;
import com.fmf.mypoem.model.Poem;
import com.fmf.mypoem.poem.PoemLog;

public class PoemDetailFragment extends BaseDetailFragment<Poem> {
    private TextView tvTitle;
    private TextView tvSubtitle;
    private TextView tvContent;
    private TextView tvAuthor;
    private TextView tvCreated;

    public static PoemDetailFragment newInstance(long id) {
        PoemDetailFragment fragment = new PoemDetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ID, id);
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
    protected void initViews(View root) {
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

        StringBuilder sb = new StringBuilder();
        final String LF = "\n";
        final String DOT = "•";
        sb.append(title);
        if (!TextUtils.isEmpty(subtitle)) {
            sb.append(DOT).append(subtitle);
        }
        sb.append(DOT).append(author).append(LF);
        sb.append(content);

        shareText = sb.toString();
    }

    @Override
    protected Poem onLoadDetail() {
        return new PoemDao(getActivity()).get(id);
    }

    @Override
    protected void onPostLoadDetail(Poem poem) {
        bindViewData(poem);
    }

}
