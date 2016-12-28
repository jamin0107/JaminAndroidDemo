package com.jamin.android.demo.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.jamin.framework.util.LogUtil;

import static android.widget.ImageView.ScaleType.MATRIX;


public class ReboundImageView extends ImageView {


    int viewHeight;
    int viewWidth;
    Thread mTimer = null;
    //是否为反向滚动
    boolean isReverse = true;
    int imageWidth = 0;
    int imageHeight = 0;

    int offsetY = 0;
    private static final int FRAMES = 2;


    public ReboundImageView(Context context) {
        super(context);
        initView();
    }

    public ReboundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ReboundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    public ReboundImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }


    private void initView() {
        setScaleType(MATRIX);
    }

    public void setImageBitmap(Bitmap bitmap) {
        imageHeight = bitmap.getHeight();
        imageWidth = bitmap.getWidth();
        super.setImageBitmap(bitmap);
    }

    boolean isAnimating = false;

    private boolean isAnimating() {
        return isAnimating;
    }

    public void setAnimating(boolean animating) {
        isAnimating = animating;
    }

    public void startScroll() {
        if (mTimer != null)
            return;
        setAnimating(true);
        mTimer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isAnimating()) {
                    handler.sendEmptyMessage(0x123);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mTimer.start();
    }

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {


        public void handleMessage(Message msg) {
            if (viewHeight == 0 || viewWidth == 0) {
                return;
            }
            if (msg.what == 0x123) {
                if (!isReverse) {
                    offsetY += FRAMES;
                    if (offsetY + viewHeight < imageHeight) {
                        LogUtil.d("first offsetY = " + offsetY);
                        // 第一张图自己飞阶段
                        scrollTo(0, offsetY);
                    } else {
                        isReverse = true;
                        LogUtil.d("second offsetY = " + offsetY);
                    }
                } else {
                    offsetY -= FRAMES;
                    if (offsetY > 0) {
                        scrollTo(0, offsetY);
                    } else {
                        isReverse = false;
                    }
                }

            }
        }
    };


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.d("MeasureSpec.getSize(widthMeasureSpec) = " + MeasureSpec.getSize(widthMeasureSpec));
        LogUtil.d("MeasureSpec.getSize(heightMeasureSpec) = " + MeasureSpec.getSize(heightMeasureSpec));
        //如果宽高不相等，重新计算
        if (MeasureSpec.getSize(widthMeasureSpec) != viewWidth
                && MeasureSpec.getSize(heightMeasureSpec) != viewHeight) {

            viewWidth = MeasureSpec.getSize(widthMeasureSpec);
            viewHeight = MeasureSpec.getSize(heightMeasureSpec);
            float widthScale = (float) viewWidth / (float) imageWidth;
            LogUtil.d("imageHeight = " + imageHeight);
            imageWidth = (int) (imageWidth * widthScale);
            imageHeight = (int) (imageHeight * widthScale);
            LogUtil.d("imageHeight = " + imageHeight);
            Matrix matrix = new Matrix();
            matrix.setScale(widthScale, widthScale);
            setImageMatrix(matrix);
        }


    }


}
