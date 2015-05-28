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
import com.fmf.mypoem.data.PoemDao;
import com.fmf.mypoem.util.PoemLog;

public class RhythmsFragment extends BasePoemFragment {
    private static final String[] FORM = {MyPoem.Rhythm.COLUMN_NAME_NAME, MyPoem.Rhythm.COLUMN_NAME_COUNT, MyPoem.Rhythm.COLUMN_NAME_INTRO};
    private static final int[] TO = {R.id.tv_name, R.id.tv_count, R.id.tv_intro};

    public RhythmsFragment() {
        super();
        PoemLog.i("PoemsFragment");
    }

    @Override
    protected CharSequence onCreateEmptyText() {
        return getString(R.string.empty_text_fragment_poems);
    }

    @Override
    protected Cursor onCreateCursor() {
        return new PoemDao(getActivity()).queryPoem();
    }

    @Override
    protected CursorAdapter onCreateCursorAdapter() {
        return new SimpleCursorAdapter(getActivity(),
                R.layout.poem_list_item, null, FORM, TO, 0);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(MyPoem.Poem._ID, id);
        startActivity(intent);
    }
}