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

import com.fmf.mypoem.R;
import com.fmf.mypoem.poem.PoemConstant;
import com.fmf.mypoem.poem.PoemLog;

public abstract class BasePoemFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    protected CursorAdapter adapter;
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
    }

    private void init() {
        setEmptyText(onCreateEmptyText());

        registerForContextMenu(getListView());

        adapter = onCreateCursorAdapter();
        setListAdapter(adapter);
        getListView().setSelector(R.drawable.lv_bg);

        // Prepare the loader.  Either re-connect with an existing one, or start a new one.
        Bundle args = null;
        loaderManager = getLoaderManager();
        loaderManager.initLoader(PoemConstant.LOADER_ID, args, this);
    }

    public void query(String text) {
        Bundle args = new Bundle();
        args.putString(PoemConstant.QUERY, text);
        loaderManager.restartLoader(PoemConstant.LOADER_ID, args, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        PoemLog.i(this, "onCreateLoader");
        
        String text = null;
        if (args != null) {
            text = args.getString(PoemConstant.QUERY);
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
