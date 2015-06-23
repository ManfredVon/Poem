package com.fmf.poem.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.fmf.poem.R;
import com.fmf.poem.model.Rhythm;
import com.fmf.poem.util.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmf on 15/6/12.
 */
public class RhythmListAdapter extends BaseListAdapter<Rhythm> implements Filterable {
    private RhythmFilter mFilter;
    private List<Rhythm> mUnfilteredData;

    public RhythmListAdapter(Context context, List<Rhythm> data) {
        super(context, data);
    }

    @Override
    protected void bindViewData(View view, Rhythm data) {
        final TextView tvName = ViewHolder.get(view, R.id.tv_name);
        final TextView tvAlias = ViewHolder.get(view, R.id.tv_alias);
        final TextView tvIntro = ViewHolder.get(view, R.id.tv_intro);

        tvName.setText(data.getName());
        tvAlias.setText(data.getAlias());
        tvIntro.setText(data.getIntro());
    }

    @Override
    protected int getLayoutRes(Rhythm data) {
        return R.layout.rhythm_list_item;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new RhythmFilter();
        }
        return mFilter;
    }

    private class RhythmFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList<Rhythm>(mData);
            }

            if (TextUtils.isEmpty(constraint)) {
                List<Rhythm> list = mUnfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                String query = constraint.toString().trim();

                List<Rhythm> unfilteredValues = mUnfilteredData;
                int count = unfilteredValues.size();

                List<Rhythm> newValues = new ArrayList<>(count);

                for (int i = 0; i < count; i++) {
                    Rhythm rhythm = unfilteredValues.get(i);
                    String name = rhythm.getName();
                    String alias = rhythm.getAlias();
                    boolean nameMatch = !TextUtils.isEmpty(name) && name.contains(query);
                    boolean aliasMatch = !TextUtils.isEmpty(alias) && alias.contains(query);
                    if (nameMatch || aliasMatch){
                        newValues.add(rhythm);
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mData = (List<Rhythm>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
