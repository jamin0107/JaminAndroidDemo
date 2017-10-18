package com.jamin.android.demo.ui.anim.parallaxscroll;

import android.widget.RelativeLayout;

import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseHolder;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.ui.base.BaseActivity;

/**
 * Created by jamin on 2017/3/24.
 */

public class HeaderItem extends BaseItem<Void> {

    RelativeLayout relativeLayout;

    public HeaderItem(BaseActivity activity) {
        super(activity);
    }


    public HeaderItem(BaseActivity activity, Void aVoid) {
        super(activity, aVoid);
    }


    @Override
    public int getLayoutId() {
        return R.layout.list_item_parallax_header;
    }


    @Override
    public void onBindView(BaseHolder holder, int position) {
        relativeLayout = holder.findViewById(R.id.list_item_cover_layout);
    }


    public RelativeLayout getLayout() {
        return relativeLayout;
    }


}
