package com.fmf.mypoem.util;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by fmf on 15/6/7.
 */
public class ViewHolder {

    public static <T extends View> T get(View view, int id) {
        Object obj = view.getTag();
        boolean exist = obj instanceof SparseArray;
        SparseArray<View> viewHolder = null;
        if (exist) {
            viewHolder = (SparseArray<View>) obj;
        } else {
            viewHolder = new SparseArray<>();
            view.setTag(viewHolder);
        }

        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }

        try {
            return (T) childView;
        } catch (ClassCastException e) {
            return null;
        }
    }
}