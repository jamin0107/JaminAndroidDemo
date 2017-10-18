package com.jamin.android.demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/19.
 */
public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<BaseHolder> {


    private List<BaseItem> mList = new ArrayList<>();

    public CustomRecyclerViewAdapter() {

    }

    public CustomRecyclerViewAdapter(List<BaseItem> list) {
        if (list != null) {
            this.mList.addAll(list);
        }
    }

    public void setData(List<BaseItem> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }



    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (this.mList == null) {
            return null;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        BaseHolder holder = new BaseHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        if (this.mList == null || this.mList.size() <= position) {
            return;
        }

        BaseItem item = this.mList.get(position);
        if (item == null) {
            return;
        }

        item.onBindView(holder, position);
    }


    @Override
    public int getItemViewType(int position) {
        if (mList != null && mList.size() > position) {
            return mList.get(position).getLayoutId();
        }
        return super.getItemViewType(position);
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



}

