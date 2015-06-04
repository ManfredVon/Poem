package com.fmf.mypoem.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.fmf.mypoem.poem.PoemLog;

public abstract class BasePoemFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 0;
    public static final String PAGE_TITLE = "PageTitle";
    public static final String ARG_QUERY = "QueryText";
    private CursorAdapter adapter;
    private LoaderManager loaderManager;

    public BasePoemFragment() {
        PoemLog.i(this, "constructor");
    }

    protected abstract Cursor onQuery(@Nullable String text);

    protected abstract CursorAdapter onCreateCursorAdapter();

    protected CharSequence onCreateEmptyText() {
        return "";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PoemLog.i(this, "onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PoemLog.i(this, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PoemLog.i(this, "onViewCreated");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PoemLog.i(this, "onActivityCreated");

        init();

    }

    @Override
    public void onStart() {
        super.onStart();
        PoemLog.i(this, "onStart");

//        init();
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
        loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_ID, args, this);
    }

    public void query(String text) {
        Bundle args = new Bundle();
        args.putString(ARG_QUERY, text);
        loaderManager.restartLoader(LOADER_ID, args, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        PoemLog.i(this, "onCreateLoader");
        
        String text = null;
        if (args != null) {
            text = args.getString(BasePoemFragment.ARG_QUERY);
        }

        final String finalText = text == null ? null : text.trim();
        Loader<Cursor> loader = new AsyncTaskLoader<Cursor>(getActivity()) {
            @Override
            public Cursor loadInBackground() {
                return onQuery(finalText);
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
        PoemLog.i(this, "onLoadFinished");
        adapter.swapCursor(data);
//        closeCursor(); //不能关闭，否则apdater不能从cursor读取数据
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        PoemLog.i(this, "onLoaderReset");
        closeCursor();
        adapter.swapCursor(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PoemLog.i(this, "onDestroy");
        
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
