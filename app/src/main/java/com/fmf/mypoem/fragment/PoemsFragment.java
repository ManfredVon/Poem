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
import com.fmf.mypoem.poem.PoemLog;

public class PoemsFragment extends BasePoemFragment {
    private static final String[] FORM = {MyPoem.Poem.COLUMN_NAME_TITLE, MyPoem.Poem.COLUMN_NAME_SUBTITLE, MyPoem.Poem.COLUMN_NAME_CONTENT};
    private static final int[] TO = {R.id.tv_title, R.id.tv_subtitle, R.id.tv_content};

    public PoemsFragment() {
        super();
        PoemLog.i("PoemsFragment");
    }

    @Override
    protected CharSequence onCreateEmptyText() {
        return getString(R.string.empty_text_fragment_poems);
    }

    @Override
    protected Cursor onQuery(String text) {
        return new PoemDao(getActivity()).queryPoem(text);
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