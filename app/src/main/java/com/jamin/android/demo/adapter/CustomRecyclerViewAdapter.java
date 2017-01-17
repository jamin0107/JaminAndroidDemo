package com.jamin.android.demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/5/19.
 */
public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<BaseHolder> {


    List<BaseItem> mList;


    public CustomRecyclerViewAdapter(List<BaseItem> list) {
        this.mList = list;
    }


    /**
     * TODO:有点小问题 万一传入的是mList的引用，clear就清空掉了。
     * @param list
     */
    public void setData(List<BaseItem> list) {
        if (mList != null) {
            mList.clear();
        }
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

