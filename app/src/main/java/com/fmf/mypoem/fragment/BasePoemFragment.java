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
import android.widget.Toast;

import com.fmf.mypoem.activity.DetailActivity;
import com.fmf.mypoem.data.MyPoem;
import com.fmf.mypoem.data.PoemDao;
import com.fmf.mypoem.util.PoemLog;

public abstract class BasePoemFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 0;
    private CursorAdapter adapter;

    public BasePoemFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private void init() {
        setEmptyText(onCreateEmptyText());

        // We have a menu item to show in action bar.
//        setHasOptionsMenu(true);

        adapter = onCreateCursorAdapter();
        setListAdapter(adapter);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        Bundle args = null;
        getLoaderManager().initLoader(LOADER_ID, args, this);
    }

    protected abstract Cursor onCreateCursor();

    protected abstract CursorAdapter onCreateCursorAdapter();

    protected CharSequence onCreateEmptyText() {
        return "";
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> loader = new AsyncTaskLoader<Cursor>(getActivity()) {
            @Override
            public Cursor loadInBackground() {
                return onCreateCursor();
            }

            @Override
            protected void onStartLoading() {
                forceLoad();
            }
        };
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        PoemLog.i(this.getClass().getSimpleName() + "-onLoadFinished");
        adapter.swapCursor(data);
//        closeCursor(); //不能关闭，否则apdater不能从cursor读取数据
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        closeCursor();
        adapter.swapCursor(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closeCursor();
    }

    private void closeCursor() {
        if (adapter != null) {
            Cursor cursor = adapter.getCursor();
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
