package com.jamin.android.demo.ui.image.filter;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.adapter.CustomRecyclerViewAdapter;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.android.demo.ui.base.BaseFragment;
import com.jamin.android.demo.ui.image.filter.data.FilterDataManager;
import com.jamin.android.demo.ui.image.filter.data.FilterInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamin on 2017/1/16.
 */

public class FilterPanelFragment extends BaseFragment {

    RecyclerView recyclerView;
    CustomRecyclerViewAdapter mCustomRecyclerViewAdapter;
    List<BaseItem> filterInfoList = new ArrayList<>();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_panel, container, false);
        initView(view);
        bindView();
        return view;

    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.filter_panel_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }


    private void bindView() {
        FilterInfo[] filterInfos = new FilterDataManager().filter_infos;
        List<BaseItem> list = new ArrayList<>();
        for (FilterInfo filterInfo : filterInfos) {
            list.add(new ListItemFilterGroup((BaseActivity) getActivity(), filterInfo));
        }
        recyclerView.setAdapter(new CustomRecyclerViewAdapter(list));
    }


}
