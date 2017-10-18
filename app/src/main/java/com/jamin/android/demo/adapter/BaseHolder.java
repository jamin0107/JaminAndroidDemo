package com.jamin.android.demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by Administrator on 2016/5/19.
 */
public class BaseHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews = new SparseArray<>();


    public BaseHolder(View itemView) {
        super(itemView);
    }


    public <T extends View> T findViewById(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mViews.put(id, view);
        }
        return (T) view;
    }

}
