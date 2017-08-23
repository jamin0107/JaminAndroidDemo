package com.jamin.android.demo.ui.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.jamin.android.demo.R;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.framework.util.LogUtil;

/**
 * Created by wangjieming on 2017/8/22.
 */

public class BitmapTestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_test);
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.nasa, bitmapOptions);
        BitmapFactory.Options bitmapOptions1 = new BitmapFactory.Options();
        bitmapOptions1.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.nasa, bitmapOptions1);
        ImageView imageView = (ImageView) findViewById(R.id.bitmap_test);
        imageView.setImageBitmap(bitmap);
        LogUtil.d("bitmap.getByteCount() = " + bitmap.getByteCount() + ", bitmap.getHeight() = " + bitmap.getHeight() + ", bitmap.getWidth() = " + bitmap.getWidth());
        LogUtil.d("bitmap.getByteCount() = " + bitmap1.getByteCount() + ", bitmap.getHeight() = " + bitmap1.getHeight() + ", bitmap.getWidth() = " + bitmap1.getWidth());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
