package com.fmf.mypoem.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.fmf.mypoem.R;
import com.fmf.mypoem.model.Model;
import com.fmf.mypoem.poem.PoemLog;

import java.util.List;

public abstract class BaseDetailFragment<T extends Model> extends Fragment {
    private long id;
    public static final String ARG_ID = "id";
    public static final String ARG_TABLE = "table";

    private Intent shareIntent;

    public BaseDetailFragment() {
        // Required empty public constructor
        PoemLog.i(this, "constructor");
    }

    protected abstract int getLayoutRes();
    
    protected abstract void onInitViews(View root);

    protected abstract T onLoadDetail(long id);

    protected abstract void onPostLoadDetail(T model);

    protected abstract String onCreateShareText(T model);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PoemLog.i(this, "onCreate");

        // perform onCreateOptionsMenu
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            id = getArguments().getLong(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        PoemLog.i(this, "onCreateView");

        View view = inflater.inflate(getLayoutRes(), null);
        onInitViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PoemLog.i(this, "onActivityCreated");

        loadDetail();
    }

    private void loadDetail() {
        if (id > 0) {
            new AsyncTask<Void, Void, T>() {

                @Override
                protected T doInBackground(Void... params) {
                    return onLoadDetail(id);
                }

                @Override
                protected void onPostExecute(T model) {
                    setUpShareText(model);

                    onPostLoadDetail(model);
                }
            }.execute();
        }
    }

    private void setUpShareText(T model) {
        if (shareIntent != null) {
            final String text = onCreateShareText(model);
            PoemLog.i(text);
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        }else{
            PoemLog.i("shareIntent is null");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);

        int shareItemId = R.id.action_share;
        MenuItem shareItem = menu.findItem(shareItemId);
        ShareActionProvider shareProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        shareIntent = createShareIntent();
        if (shareIntent != null) {
            // Set up ShareActionProvider's default share intent
            shareProvider.setShareIntent(shareIntent);
        } else {
            menu.removeItem(shareItemId);
        }
    }

    @Nullable
    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/*");
//        shareIntent.putExtra(Intent.EXTRA_TEXT, onCreateShareText(model)); // the model didn't load yet

        //检查手机上是否存在可以处理这个动作的应用
        List<ResolveInfo> infoList = getActivity().getPackageManager().queryIntentActivities(shareIntent, 0);
        if (infoList.isEmpty()) {
            return null;
        }

        return shareIntent;
    }
}
