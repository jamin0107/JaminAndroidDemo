package com.jamin.android.demo.ui.rxjava.detail;

import android.widget.TextView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseHolder;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.ui.base.BaseActivity;

/**
 * Created by alvin on 17/1/17.
 */

public class DetailContentItem extends BaseItem {

    String content;

    public DetailContentItem(BaseActivity activity, String content) {
        super(activity);
        this.content = content;
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_detail_content;
    }

    @Override
    public void onBindView(BaseHolder holder, int position) {

        TextView tvContent = holder.getView(R.id.content);
        tvContent.setText(content);

    }
}
