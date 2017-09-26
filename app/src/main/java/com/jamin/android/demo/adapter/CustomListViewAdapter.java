package com.jamin.android.demo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/19.
 */
public class CustomListViewAdapter extends BaseAdapter {


    private List<BaseItem> mList = new ArrayList<>();


    public CustomListViewAdapter(List<BaseItem> list) {
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

