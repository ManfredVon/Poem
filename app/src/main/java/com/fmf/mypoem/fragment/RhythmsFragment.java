package com.fmf.mypoem.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.fmf.mypoem.R;
import com.fmf.mypoem.activity.DetailActivity;
import com.fmf.mypoem.data.MyPoem;
import com.fmf.mypoem.data.RhythmDao;
import com.fmf.mypoem.poem.PoemConstant;

public class RhythmsFragment extends BasePoemFragment {
    private static final String[] FORM = {MyPoem.Rhythm.NAME, MyPoem.Rhythm.ALIAS, MyPoem.Rhythm.INTRO};
    private static final int[] TO = {R.id.tv_name, R.id.tv_alias, R.id.tv_intro};

    public RhythmsFragment() {
        super();
    }

    @Override
    protected CharSequence onCreateEmptyText() {
        return getString(R.string.empty_text_fragment_rhythm);
    }

    @Override
    protected Cursor onQuery(String text) {
        return new RhythmDao(getActivity()).query(text);
    }

    @Override
    protected CursorAdapter onCreateCursorAdapter() {
        return new SimpleCursorAdapter(getActivity(),
                R.layout.rhythm_list_item, null, FORM, TO, 0);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(PoemConstant.POEM_ID, id);
        intent.putExtra(PoemConstant.TAB, PoemConstant.TAB_RHYTHM);
        startActivity(intent);
    }
}