package com.jamin.android.demo.ui.rxjava.detail;

import android.widget.TextView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseHolder;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.ui.base.BaseActivity;

/**
 * Created by alvin on 17/1/17.
 */

public class DetailTitleItem extends BaseItem {

    String title;

    public DetailTitleItem(BaseActivity activity, String title) {
        super(activity);
        this.title = title;
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_detail_title;
    }

    @Override
    public void onBindView(BaseHolder holder, int position) {

        TextView tvTitle = holder.findViewById(R.id.title);
        tvTitle.setText(title);

    }
}
