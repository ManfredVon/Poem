package com.fmf.mypoem.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.fmf.mypoem.R;
import com.fmf.mypoem.activity.DetailActivity;
import com.fmf.mypoem.data.MyPoem;
import com.fmf.mypoem.data.PoemDao;
import com.fmf.mypoem.poem.PoemLog;

public class DraftsFragment extends BasePoemFragment {
    private static final String[] FORM = {MyPoem.Poem.COLUMN_NAME_TITLE, MyPoem.Poem.COLUMN_NAME_SUBTITLE, MyPoem.Poem.COLUMN_NAME_CONTENT};
    private static final int[] TO = {R.id.tv_title, R.id.tv_subtitle, R.id.tv_content};

    public DraftsFragment() {
        super();
    }

    @Override
    protected CharSequence onCreateEmptyText() {
        return getString(R.string.empty_text_fragment_drafts);
    }

    @Override
    protected Cursor onQuery(String text) {
//        PoemLog.i("onCreateCursor:"+Thread.currentThread().getName());
        // call in background
        return new PoemDao(getActivity()).queryDraft(text);
    }

    @Override
    protected CursorAdapter onCreateCursorAdapter() {
        return new SimpleCursorAdapter(getActivity(),
                R.layout.poem_list_item, null, FORM, TO, 0);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(BaseDetailFragment.ARG_ID, id);
        intent.putExtra(BaseDetailFragment.ARG_TABLE, MyPoem.Poem.TABLE_NAME);
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        PoemLog.i(this, "onCreateContextMenu");

        getActivity().getMenuInflater().inflate(R.menu.menu_context_draft, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final long id = info.id;
        final Cursor cursor = (Cursor) adapter.getItem(info.position);
        if (cursor != null) {
            final String title = cursor.getString(cursor.getColumnIndex(MyPoem.Poem.COLUMN_NAME_TITLE));
            PoemLog.i(this, "id=" + id + ", title=" + title);
        }

//        adapter.getItemId()
//        adapter.getItem()
        switch (item.getItemId()) {
            case R.id.action_update:
                break;
            case R.id.action_delete:
                delete(id);
                break;
            default:
                break;
        }

        Toast.makeText(getActivity(), item.getTitle().toString() + info.id, Toast.LENGTH_SHORT).show();
        return true;

//        return super.onContextItemSelected(item);
    }

    private void delete(long id) {
        PoemLog.i(this, "id=" + id);
        new AlertDialog.Builder(getActivity())
                .setTitle("确认")
                .setMessage("确定吗？")
                .setPositiveButton(R.string.action_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

}
