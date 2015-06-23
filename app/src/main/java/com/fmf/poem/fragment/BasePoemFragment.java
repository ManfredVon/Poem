package com.fmf.poem.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.Toast;

import com.fmf.poem.R;
import com.fmf.poem.poem.PoemConstant;
import com.fmf.poem.poem.PoemLog;

public abstract class BasePoemFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    protected CursorAdapter adapter;
    private LoaderManager loaderManager;

    public BasePoemFragment() {
        PoemLog.i(this, "constructor");
    }

    protected abstract Cursor onQuery(@Nullable String text);

    protected abstract CursorAdapter onCreateCursorAdapter();

    protected abstract int onDelete(long id);

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

    @Override
    public void onPause() {
        super.onPause();
        PoemLog.i(this, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        PoemLog.i(this, "onStop");
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
//        adapter.swapCursor(data);
        adapter.changeCursor(data); // close old cursor
//        closeCursor(); //不能关闭，否则apdater不能从cursor读取数据
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        PoemLog.i(this, "onLoaderReset");
//        closeCursor();
//        adapter.swapCursor(null);
        adapter.changeCursor(null); // close old cursor
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        PoemLog.i(this, "onCreateContextMenu");

        getActivity().getMenuInflater().inflate(R.menu.fragment_base_poem_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (getUserVisibleHint()) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final long id = info.id;

            switch (item.getItemId()) {
                case R.id.action_delete:
                    confirmDelete(id);
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }

        return false;
    }

    protected void confirmDelete(final long id) {
        new AlertDialog.Builder(getActivity())
//                .setTitle("确认删除")
                .setMessage("确定删除？")
                .setPositiveButton(R.string.action_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(id);
                    }
                })
                .setNegativeButton(R.string.action_cancel, null)
                .show();
    }

    private void delete(final long id) {
        if (id > 0) {
            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... params) {
                    return onDelete(id);
                }

                @Override
                protected void onPostExecute(Integer rows) {
                    int tipId = R.string.tip_delete_fail;
                    final boolean isSuccess = rows > 0;
                    if (isSuccess) {
                        tipId = R.string.tip_delete_success;

                        // loader没有监听数据源，需手动通知adapter
//                        adapter.notifyDataSetChanged(); // did not work

                        // restart loader
                        query(null);
                    }
                    Toast.makeText(BasePoemFragment.this.getActivity(), tipId, Toast.LENGTH_SHORT).show();
                }
            }.execute();
        }
    }

}
