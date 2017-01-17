package com.jamin.android.demo.ui.image.filter;

import android.widget.TextView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseHolder;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.android.demo.ui.image.filter.data.FilterInfo;
import com.jamin.framework.util.ColorUtil;

/**
 * Created by jamin on 2017/1/17.
 */

public class ListItemFilterGroup extends BaseItem<FilterInfo> {


    public ListItemFilterGroup(BaseActivity activity, FilterInfo filterInfo) {
        super(activity, filterInfo);
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_filter;
    }

    @Override
    public void onBindView(BaseHolder holder, int position) {
        TextView textView = holder.getView(R.id.list_item_filter_text_view);
        FilterInfo info = getItemData();
        textView.setText(info.filterName);
        textView.setTextColor(ColorUtil.getColor(0.44f));
    }


}
