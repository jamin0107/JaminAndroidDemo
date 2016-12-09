package com.jamin.android.demo.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.jamin.framework.util.ImageUtil;
import com.jamin.framework.util.ScreenTool;

import java.util.Timer;
import java.util.TimerTask;


public class BackgroundFlyingLayout extends RelativeLayout {

    private static final String TAG = "BackgroundFlyingLayout";
    private static final boolean DEBUG = false;
    // 两张图Scroll的距离
    int startX = 0;
    int startX2 = 0;
    // 背景图宽高
    int back_width = 0;
    int back_heigh = 0;
    // 得到屏幕尺寸
    ScreenTool.Screen screen = null;
    Timer mTimer = null;
    // 两张图轮播。是否是第一张图在播放
    boolean isFirstPic = true;
    // 根据屏幕尺寸调整过的新图
    Bitmap resizedBitmap = null;
    // 两张图
    ImageView imageView1 = null;
    ImageView imageView2 = null;
    Context ctx = null;

    private static final int FRAMES = 1;

    public BackgroundFlyingLayout(Context context, int ResId) {
        super(context);
        this.ctx = context;
        // 初始化图片尺寸和屏幕尺寸
        intSize(ResId);
        // 初始化两张图
        initImageView();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0x123);
            }
        }, 0, 10);
    }

    /**
     * 销毁
     */
    public void destory() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (imageView1 != null) {
            imageView1.setImageBitmap(null);
            imageView1 = null;
        }
        if (imageView2 != null) {
            imageView2.setImageBitmap(null);
            imageView2 = null;
        }
        if (resizedBitmap != null && !resizedBitmap.isRecycled()) {
            resizedBitmap.recycle();
            resizedBitmap = null;
        }

    }

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            if (imageView1 != null && imageView2 != null) {
                if (msg.what == 0x123) {
                    if (isFirstPic) {
                        startX += FRAMES;
                        if (startX + screen.widthPixels < back_width) {
                            if (DEBUG)
                                Log.d(TAG, "first startX = " + startX);
                            // 第一张图自己飞阶段
                            imageView1.scrollTo(startX, 0);
                        } else if (startX < back_width) {
                            if (DEBUG)
                                Log.d(TAG, "second startX = " + startX);
                            if (DEBUG)
                                Log.d(TAG, "second startX2 = " + startX2);
                            // 两图拼接飞
                            startX2 += FRAMES;
                            imageView1.scrollTo(startX, 0);
                            imageView2.scrollTo(startX2, 0);
                        } else {
                            // 第一张图完整飞出，第二张图自己飞
                            startX = -screen.widthPixels;
                            isFirstPic = false;
                            if (DEBUG)
                                Log.d(TAG, "end startX = " + startX);
                            if (DEBUG)
                                Log.d(TAG, "end startX2 = " + startX2);
                        }
                    } else {
                        startX2 += FRAMES;
                        if (startX2 + screen.widthPixels < back_width) {
                            if (DEBUG)
                                Log.d(TAG, "first startX2 = " + startX2);
                            // 第二张图自己飞阶段
                            imageView2.scrollTo(startX2, 0);

                        } else if (startX2 < back_width) {
                            if (DEBUG)
                                Log.d(TAG, "second startX = " + startX);
                            if (DEBUG)
                                Log.d(TAG, "second startX2 = " + startX2);
                            // 两图拼接飞
                            startX += FRAMES;
                            imageView2.scrollTo(startX2, 0);
                            imageView1.scrollTo(startX, 0);
                        } else {
                            // 第二张图完整飞出，第一张图自己飞
                            startX2 = -screen.widthPixels;
                            isFirstPic = true;
                            if (DEBUG)
                                Log.d(TAG, "end startX = " + startX);
                            if (DEBUG)
                                Log.d(TAG, "end startX2 = " + startX2);
                        }
                    }

                }
            }
        }
    };

    private void initImageView() {
        if( resizedBitmap != null){
            // 初始化第一张图
            imageView1 = new ImageView(ctx);
            ViewGroup.LayoutParams lp1 = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            addView(imageView1, lp1);
            imageView1.setScaleType(ImageView.ScaleType.MATRIX);
            imageView1.setImageBitmap(resizedBitmap);
            // 初始化第二张图
            imageView2 = new ImageView(ctx);
            addView(imageView2, lp1);
            imageView2.setScaleType(ImageView.ScaleType.MATRIX);
            imageView2.setImageBitmap(resizedBitmap);
            // 第二张图在右侧屏幕外做准备
            imageView2.scrollTo(-screen.widthPixels, 0);
        }
    }

    private void intSize(int resId) {
        // 获取屏幕尺寸
        screen = ScreenTool.getScreenPix(ctx);
        startX2 = -screen.widthPixels;

        try {
            // 得到原始图片
            Bitmap back = ImageUtil.decodeResource(getResources(), resId);
            if (back == null) {
                return;
            }
            // 获取Matrix对象 实现 图片拉伸
            Matrix mMatrix = new Matrix();
            float y = (float) screen.heightPixels / (float) back.getHeight();
            float x = getFloatX(back, y);
            mMatrix.setScale(x, y);
            // 创建新的图片
            resizedBitmap = ImageUtil.createBitmap(back, 0, 0, back.getWidth(),
                    back.getHeight(), mMatrix, true);
            // don't recycle the back sumsung may cause
            // "Cannot draw recycled bitmaps"
            // back.recycle();
            // 初始化图片尺寸
            if(resizedBitmap != null){
                back_width = resizedBitmap.getWidth();
                back_heigh = resizedBitmap.getHeight();
            }
        } catch (OutOfMemoryError e) {
//            BitmapAjaxCallback.clearCache();
        }
    }

    private float getFloatX(Bitmap back, float y) {
        float x = 1;
        if (back.getWidth() * y > 2048 && Build.MANUFACTURER.contains("HTC")) {
            x = (float) 2048 / (float) back.getWidth();
            return x;
        } else {
            return y;
        }
    }

}
