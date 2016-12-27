package com.jamin.android.demo.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
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


public class BackgroundVerticalFlyingBackLayout extends RelativeLayout {

    private static final String TAG = "BGVerticalFly";
    private static final boolean DEBUG = true;
    int offsetY = 0;
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
    Context ctx = null;

    private static final int FRAMES = 2;

    public BackgroundVerticalFlyingBackLayout(Context context, int ResId) {
        super(context);
        this.ctx = context;
        screen = ScreenTool.getScreenPix(context);
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
        if (resizedBitmap != null && !resizedBitmap.isRecycled()) {
            resizedBitmap.recycle();
            resizedBitmap = null;
        }

    }

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            if (imageView1 != null) {
                if (msg.what == 0x123) {
                    if (isFirstPic) {
                        offsetY += FRAMES;
                        if (offsetY + viewHeight < back_heigh) {
                            if (DEBUG)
                                Log.d(TAG, "first offsetY = " + offsetY);
                            // 第一张图自己飞阶段
                            imageView1.scrollTo(0, offsetY);
                        } else {
                            isFirstPic = false;
                            if (DEBUG)
                                Log.d(TAG, "second offsetY = " + offsetY);
                        }
                    } else {
                        offsetY -= FRAMES;
                        if (offsetY > 0) {
                            imageView1.scrollTo(0, offsetY);
                        } else {
                            isFirstPic = true;
                        }
                    }

                }
            }
        }
    };

    private void initImageView() {
        if (resizedBitmap != null) {
            // 初始化第一张图
            imageView1 = new ImageView(ctx);
            ViewGroup.LayoutParams lp1 = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            addView(imageView1, lp1);
            imageView1.setScaleType(ImageView.ScaleType.MATRIX);
            imageView1.setImageBitmap(resizedBitmap);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    int viewHeight;
    int viewWidth;

    private void intSize(int resId) {
        float density = getResources().getDisplayMetrics().density;
        viewHeight = (int) (density * 300 + 0.5);
        viewWidth = screen.widthPixels;
        // 获取屏幕尺寸
        try {
            // 得到原始图片
            Bitmap back = ImageUtil.decodeResource(getResources(), resId);
            if (back == null) {
                return;
            }
            // 获取Matrix对象 实现 图片拉伸
            Matrix mMatrix = new Matrix();
            float xScale = (float) viewWidth / (float) back.getWidth();
            mMatrix.setScale(xScale, xScale);
            // 创建新的图片
            resizedBitmap = ImageUtil.createBitmap(back, 0, 0, back.getWidth(),
                    back.getHeight(), mMatrix, true);
            // don't recycle the back sumsung may cause
            // "Cannot draw recycled bitmaps"
            // back.recycle();
            // 初始化图片尺寸
            if (resizedBitmap != null) {
                back_width = resizedBitmap.getWidth();
                back_heigh = resizedBitmap.getHeight();
            }
        } catch (OutOfMemoryError e) {
//            BitmapAjaxCallback.clearCache();
        }
    }


}
