package com.fmf.mypoem.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.fmf.mypoem.R;
import com.fmf.mypoem.activity.ComposeActivity;
import com.fmf.mypoem.activity.DetailActivity;
import com.fmf.mypoem.data.MyPoem;
import com.fmf.mypoem.data.PoemDao;
import com.fmf.mypoem.data.RhythmDao;
import com.fmf.mypoem.model.Poem;
import com.fmf.mypoem.model.Rhythm;
import com.fmf.mypoem.poem.PoemConstant;
import com.fmf.mypoem.poem.PoemLog;
import com.fmf.mypoem.poem.PoemUtil;

public class RhythmsFragment extends BasePoemFragment {
    private static final String[] FORM = {MyPoem.Rhythm.NAME, MyPoem.Rhythm.ALIAS, MyPoem.Rhythm.INTRO};
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
        intent.putExtra(PoemConstant.POEM_ID, id);
        intent.putExtra(PoemConstant.TAB, PoemConstant.TAB_RHYTHM);
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.fragment_rhythms_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (getUserVisibleHint()) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final long id = info.id;
            final Cursor cursor = (Cursor) adapter.getItem(info.position);
            final Rhythm rhythm = RhythmDao.getRhythm(cursor);

            switch (item.getItemId()) {
                case R.id.action_compose:
                    gotoCompose(rhythm);
                    return true;
                case R.id.action_share:
                    doShare(rhythm);
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }

        return false;
    }

    private void doShare(Rhythm rhythm) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, PoemUtil.createShareText(rhythm));

        final Intent chooser = Intent.createChooser(intent, getString(R.string.tip_share_to));
        startActivity(chooser);
    }

    private void gotoCompose(Rhythm rhythm) {
        Intent intent = new Intent(getActivity(), ComposeActivity.class);
        intent.putExtra(PoemConstant.RHYTHM, rhythm);
        startActivity(intent);
    }

    @Override
    protected int onDelete(long id) {
        PoemLog.i(this, "onDelete: id=" + id);

        return new RhythmDao(getActivity()).delete(id);
    }
}