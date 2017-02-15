package com.jamin.android.demo.ui.rxjava;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseHolder;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.http.model.CloudBeanHistory;

/**
 * Created by alvin on 17/1/17.
 */

public class HistoryItem extends BaseItem {

    CloudBeanHistory history;


    public HistoryItem(BaseActivity activity,CloudBeanHistory history) {
        super(activity);
        this.history=history;
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_history;
    }

    @Override
    public void onBindView(BaseHolder holder, int position) {

        TextView tvTitle = holder.getView(R.id.title);
        tvTitle.setText(history.title);
        TextView tvDate = holder.getView(R.id.date);
        tvDate.setText(history.date);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ActivityHistoryDetail.class);
                intent.putExtra("e_id",history.e_id);
                getActivity().startActivity(intent);
            }
        });


    }
}
