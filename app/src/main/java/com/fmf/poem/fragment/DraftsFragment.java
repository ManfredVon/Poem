package com.fmf.poem.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.fmf.poem.R;
import com.fmf.poem.activity.ComposeActivity;
import com.fmf.poem.activity.DetailActivity;
import com.fmf.poem.data.PoemContract;
import com.fmf.poem.data.PoemDao;
import com.fmf.poem.poem.PoemConstant;
import com.fmf.poem.poem.PoemLog;

public class DraftsFragment extends BasePoemFragment {
    private static final String[] FORM = {PoemContract.Poem.TITLE, PoemContract.Poem.SUBTITLE, PoemContract.Poem.CONTENT};
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
        intent.putExtra(PoemConstant.POEM_ID, id);
        intent.putExtra(PoemConstant.TAB, PoemConstant.TAB_DRAFT);
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.fragment_drafts_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (getUserVisibleHint()) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final long id = info.id;
//        final Cursor cursor = (Cursor) adapter.getItem(info.position);
//        final Poem poem = PoemDao.getPoem(cursor);

            switch (item.getItemId()) {
                case R.id.action_update:
                    gotoCompose(id);
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }

        return false;
    }

    private void gotoCompose(long id) {
        Intent intent = new Intent(getActivity(), ComposeActivity.class);
        intent.putExtra(PoemConstant.POEM_ID, id);
        startActivity(intent);
    }

    @Override
    protected int onDelete(long id) {
        PoemLog.i(this, "onDelete: id=" + id);

        return new PoemDao(getActivity()).delete(id);
    }
}
