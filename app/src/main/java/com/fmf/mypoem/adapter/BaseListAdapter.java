package com.fmf.mypoem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmf on 15/6/12.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected List<T> mData;
    private LayoutInflater mInflater;

    public BaseListAdapter(Context context, List<T> data) {
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int res = getLayoutRes(mData.get(position));
        final View v = createViewFromResource(position, convertView, parent, res);

        bindViewData(v, mData.get(position));

        return v;
    }

    private View createViewFromResource(int position, View convertView,
                                        ViewGroup parent, int resource) {
        View v;
        if (convertView == null) {
            v = mInflater.inflate(resource, parent, false);
        } else {
            v = convertView;
        }

        return v;
    }

    public List<T> getData() {
        return mData;
    }

    public void add(List<T> newData){
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    public void add(T newData){
        mData.add(newData);
        notifyDataSetChanged();
    }

    public void remove(List<T> oldData){
        mData.removeAll(oldData);
        notifyDataSetChanged();
    }

    public void remove(T oldData){
        mData.remove(oldData);
        notifyDataSetChanged();
    }

    public void replace(List<T> newData){
        mData.clear();
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    public void clear(){
        mData.clear();
        notifyDataSetChanged();
    }

    protected abstract void bindViewData(View view, T data);

    protected abstract int getLayoutRes(T data);

}
