package com.jamin.android.demo.ui.image.filter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.framework.util.ImageUtil;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;

/**
 * Created by jamin on 2016/12/20.
 */

public class FilterActivity extends BaseActivity {

    ImageView imageView;
    Button grayScaleBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        imageView = (ImageView) findViewById(R.id.activity_filter_iamgeview);
        grayScaleBtn = (Button) findViewById(R.id.activity_filter_btn_gray_scale);
        grayScaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doFilter();
            }
        });
    }


    private void doFilter() {
        Bitmap bitmap = ImageUtil.decodeResource(getResources(), R.mipmap.img_login_window);
        GPUImage gpuImage = new GPUImage(this);
        gpuImage.setImage(bitmap);
        gpuImage.setFilter(new GPUImageGrayscaleFilter());
        bitmap = gpuImage.getBitmapWithFilterApplied();
        imageView.setImageBitmap(bitmap);
    }


}
