package com.fmf.mypoem.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fmf.mypoem.R;
import com.fmf.mypoem.model.Model;
import com.fmf.mypoem.poem.PoemConstant;
import com.fmf.mypoem.poem.PoemLog;

public abstract class BaseDetailFragment<T extends Model> extends Fragment {

    protected long id;

    public BaseDetailFragment() {
        // Required empty public constructor
        PoemLog.i(this, "constructor");
    }

    protected abstract int getLayoutRes();
    
    protected abstract void onInitViews(View root);

    protected abstract T onLoadDetail(long id);

    protected abstract void onPostLoadDetail(T model);

    protected abstract int onDelete(long id);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PoemLog.i(this, "onCreate");

        // perform onCreateOptionsMenu
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            id = getArguments().getLong(PoemConstant.POEM_ID);
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
                    onPostLoadDetail(model);
                }
            }.execute();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_base_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                confirmDelete();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void confirmDelete() {
        new AlertDialog.Builder(getActivity())
//                .setTitle("确认删除")
                .setMessage("确定删除？")
                .setPositiveButton(R.string.action_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete();
                    }
                })
                .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void delete() {
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
                    if (isSuccess){
                        tipId = R.string.tip_delete_success;
                        BaseDetailFragment.this.getActivity().finish();
                    }
                    Toast.makeText(BaseDetailFragment.this.getActivity(), tipId, Toast.LENGTH_SHORT).show();
                }
            }.execute();
        }
    }
}
