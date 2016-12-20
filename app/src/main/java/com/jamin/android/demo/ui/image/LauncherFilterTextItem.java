package com.jamin.android.demo.ui.image;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseHolder;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.ui.base.BaseActivity;


/**
 * Created by Administrator on 2016/5/19.
 */
public class LauncherFilterTextItem extends BaseItem {

    String desc;


    public LauncherFilterTextItem(BaseActivity ctx, String desc) {
        super(ctx);
        this.desc = desc;
    }


    @Override
    public int getLayoutId() {
        return R.layout.list_item_launch;
    }

    @Override
    public void onBindView(BaseHolder holder, int position) {
        TextView textView = holder.getView(R.id.list_item_launch_tv);
        textView.setText(Html.fromHtml(desc));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LauncherImageActivity.LAUNCHER_FILTER.equals(desc)) {
                    getActivity().startActivity(new Intent(getActivity(), FilterActivity.class));
                }
            }
        });
    }


}
