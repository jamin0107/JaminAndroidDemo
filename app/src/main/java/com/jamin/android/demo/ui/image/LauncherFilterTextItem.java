package com.jamin.android.demo.ui.image;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseHolder;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.android.demo.ui.image.filter.activity.ActivityMain;
import com.jamin.android.demo.ui.image.iconfont.IconFontGridActivity;


/**
 * Created by Administrator on 2016/5/19.
 */
public class LauncherFilterTextItem extends BaseItem<String> {


    public LauncherFilterTextItem(BaseActivity activity, String s) {
        super(activity, s);
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_launch;
    }

    @Override
    public void onBindView(BaseHolder holder, int position) {
        TextView textView = holder.getView(R.id.list_item_launch_tv);
        final String desc = getItemData();
        textView.setText(Html.fromHtml(desc));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LauncherImageActivity.LAUNCHER_FILTER.equals(desc)) {
                    getActivity().startActivity(new Intent(getActivity(), ActivityMain.class));
                } else if (LauncherImageActivity.LAUNCHER_ICON_FONT.equals(desc)) {
                    getActivity().startActivity(new Intent(getActivity(), IconFontGridActivity.class));
                }
            }
        });
    }


}
