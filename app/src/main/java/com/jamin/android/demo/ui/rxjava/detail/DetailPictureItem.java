package com.jamin.android.demo.ui.rxjava.detail;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseHolder;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.http.model.detail.DetailPicUrlBean;

/**
 * Created by alvin on 17/1/17.
 */

public class DetailPictureItem extends BaseItem {


    DetailPicUrlBean picture;

    public DetailPictureItem(BaseActivity activity, DetailPicUrlBean detailPicUrlBean) {
        super(activity);
        this.picture = detailPicUrlBean;
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_detail_picture;
    }

    @Override
    public void onBindView(BaseHolder holder, int position) {

        TextView tvTitle = holder.getView(R.id.pic_title);
        tvTitle.setText(picture.getPic_title());
        TextView tvUrl = holder.getView(R.id.pic_url);
        tvUrl.setText(picture.getUrl());
        ImageView imgPic=holder.getView(R.id.pic_image);
        Glide.with(getActivity()).load(picture.getUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgPic);


    }
}
