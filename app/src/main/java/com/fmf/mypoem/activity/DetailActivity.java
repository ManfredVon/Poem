package com.fmf.mypoem.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.fmf.mypoem.R;
import com.fmf.mypoem.fragment.BaseDetailFragment;
import com.fmf.mypoem.fragment.DraftDetailFragment;
import com.fmf.mypoem.fragment.PoemDetailFragment;
import com.fmf.mypoem.fragment.RhythmDetailFragment;
import com.fmf.mypoem.poem.PoemConstant;
import com.fmf.mypoem.poem.PoemLog;

public class DetailActivity extends BaseActivity {
    private FragmentManager fm;

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);

        fm = getFragmentManager();

        handleIntent(getIntent());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null) {
            long id = intent.getLongExtra(PoemConstant.POEM_ID, 0);
            final String tab = intent.getStringExtra(PoemConstant.TAB);
            PoemLog.i(this, "id=" + id + ", tab=" + tab);

            if (id > 0) {
                FragmentTransaction ft = fm.beginTransaction();
                BaseDetailFragment fragment = null;
                if (PoemConstant.TAB_POEM.equals(tab)) {
                    setTitle(R.string.title_activity_detail);
                    fragment = PoemDetailFragment.newInstance(id);
                } else if (PoemConstant.TAB_DRAFT.equals(tab)) {
                    setTitle(R.string.title_activity_detail_draft);
                    fragment = DraftDetailFragment.newInstance(id);
                } else if (PoemConstant.TAB_RHYTHM.equals(tab)) {
                    setTitle(R.string.title_activity_detail_rhythm);
                    fragment = RhythmDetailFragment.newInstance(id);
                }
                int containerId = R.id.fl_container;
                ft.replace(containerId, fragment);
                ft.commit();
            }
        }
    }

}
