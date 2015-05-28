package com.fmf.mypoem.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.fmf.mypoem.R;
import com.fmf.mypoem.activity.DetailActivity;
import com.fmf.mypoem.data.MyPoem;
import com.fmf.mypoem.data.PoemDao;
import com.fmf.mypoem.util.PoemLog;

public class DraftsFragment extends BasePoemFragment {
    private static final String[] FORM = {MyPoem.Poem.COLUMN_NAME_TITLE, MyPoem.Poem.COLUMN_NAME_SUBTITLE, MyPoem.Poem.COLUMN_NAME_CONTENT};
    private static final int[] TO = {R.id.tv_title, R.id.tv_subtitle, R.id.tv_content};

    public DraftsFragment() {
        super();
        PoemLog.i("DraftsFragment");
    }

    @Override
    protected CharSequence onCreateEmptyText() {
        return getString(R.string.empty_text_fragment_drafts);
    }

    @Override
    protected Cursor onCreateCursor() {
//        PoemLog.i("onCreateCursor:"+Thread.currentThread().getName());
        // call in background
        return new PoemDao(getActivity()).queryDraft();
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
