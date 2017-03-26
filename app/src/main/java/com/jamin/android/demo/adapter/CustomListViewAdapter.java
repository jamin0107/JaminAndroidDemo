package com.jamin.android.demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/5/19.
 */
public class CustomListViewAdapter extends BaseAdapter {


    List<BaseItem> mList;


    public CustomListViewAdapter(List<BaseItem> list) {
        this.mList = list;
    }


    /**
     * TODO:有点小问题 万一传入的是mList的引用，clear就清空掉了。
     *
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
    public int getItemViewType(int position) {
        if (mList != null && mList.size() > position) {
            return mList.get(position).getLayoutId();
        }
        return super.getItemViewType(position);
    }


    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (this.mList == null || this.mList.size() <= position) {
            return convertView;
        }
        BaseHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(getItemViewType(position), parent, false);
            holder = new BaseHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (BaseHolder) convertView.getTag();
        }


        BaseItem item = this.mList.get(position);
        if (item == null) {
            return convertView;
        }
        item.onBindView(holder, position);
        return convertView;
    }


}

