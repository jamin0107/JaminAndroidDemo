package com.jamin.android.demo.ui.image.iconfont;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.adapter.CustomRecyclerViewAdapter;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.framework.util.ColorUtil;
import com.jamin.framework.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamin on 2017/1/13.
 */

public class IconFontGridActivity extends BaseActivity {


    RecyclerView mRecyclerView;
    public static final int[] PICKCOLORBAR_COLORS = new int[]{
            0xFFFFFFFF, 0xFFFF3030, 0xFFF4A460,
            0xFFFFFF00, 0xFF66CD00,
            0xFF458B00, 0xFF0000EE,
            0xFF912CEE, 0xFF000000};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iconfont);
        intView();
        bindView();
    }

    private void intView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.iconfont_recycle_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void bindView() {
        String[] iconfont_array = getResources().getStringArray(R.array.iconfont_lists);
        List<BaseItem> iconFontList = new ArrayList<>();
        for (int i = 0; i < iconfont_array.length; i++) {
            int index = this.getResources().getIdentifier(iconfont_array[i], "string", this.getPackageName());
            if (index != 0) {
                int color = ColorUtil.getColor((float) i / (float) iconfont_array.length);
                ListItemIconFont listItemIconFont = new ListItemIconFont(getActivity(), new IconFont(getResources().getString(index), iconfont_array[i], color));
                iconFontList.add(listItemIconFont);
            } else {
                LogUtil.d("index == 0, " + iconfont_array[i] + " appears missing");
            }
        }
        mRecyclerView.setAdapter(new CustomRecyclerViewAdapter(iconFontList));

    }



}
