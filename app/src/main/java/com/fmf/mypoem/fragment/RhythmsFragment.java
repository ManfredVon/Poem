package com.fmf.mypoem.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.fmf.mypoem.R;
import com.fmf.mypoem.activity.DetailActivity;
import com.fmf.mypoem.data.MyPoem;
import com.fmf.mypoem.data.RhythmDao;
import com.fmf.mypoem.poem.PoemLog;

public class RhythmsFragment extends BasePoemFragment {
    private static final String[] FORM = {MyPoem.Rhythm.COLUMN_NAME_NAME, MyPoem.Rhythm.COLUMN_NAME_ALIAS, MyPoem.Rhythm.COLUMN_NAME_INTRO};
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
        intent.putExtra(BaseDetailFragment.ARG_ID, id);
        intent.putExtra(BaseDetailFragment.ARG_TABLE, MyPoem.Rhythm.TABLE_NAME);
        startActivity(intent);
    }
}