package com.jamin.android.demo.ui.image.iconfont;

import android.widget.TextView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseHolder;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.framework.widget.IconFontTextView;

/**
 * Created by jamin on 2017/1/13.
 */

public class ListItemIconFont extends BaseItem<IconFont> {


    public ListItemIconFont(BaseActivity activity, IconFont iconFont) {
        super(activity, iconFont);
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_iconfont_item;
    }

    @Override
    public void onBindView(BaseHolder holder, int position) {
        IconFontTextView iconFontTextView = holder.findViewById(R.id.list_item_iconfont_ico);
        IconFont iconFont = getItemData();
        TextView textView = holder.findViewById(R.id.list_item_iconfont_text);
        if (iconFont == null) {
            return;
        }
        iconFontTextView.setText(iconFont.icon);
        iconFontTextView.setTextColor(iconFont.iconColor);
//        textView.setText(iconFont.text);
    }


}
