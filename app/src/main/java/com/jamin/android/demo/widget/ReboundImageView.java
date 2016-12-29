package com.jamin.android.demo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.jamin.framework.util.ImageUtil;
import com.jamin.framework.util.LogUtil;
import com.jamin.framework.util.ScreenTool;

import java.lang.ref.WeakReference;

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
    private static final int FRAMES = 1;
    ScreenTool.Screen screen;
    private int DURATION = 10;


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


    ReboundImageViewHandler mHandler;

    private void initView() {
        setScaleType(MATRIX);
        screen = ScreenTool.getScreenPix(getContext());
        mHandler = new ReboundImageViewHandler(this);
    }


    public void setSpeed(int dpPerSecond) {
        int pxPerSecond = ImageUtil.dp2px(getContext(), dpPerSecond);
        DURATION = 1000 / pxPerSecond;
        LogUtil.d("Speed = " + DURATION);
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

    public void stop() {
        isAnimating = false;
    }

    public void start() {
        isAnimating = true;
    }

    public void startScroll() {
        if (mTimer != null)
            return;
        start();
        mTimer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isAnimating()) {
                    if (mHandler == null) {
                        stop();
                        return;
                    }
                    mHandler.sendEmptyMessage(0x123);
                    try {
                        Thread.sleep(DURATION);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        mTimer.start();
    }

    private static class ReboundImageViewHandler extends Handler {


        private final WeakReference<ReboundImageView> m_reBoundImageView;

        ReboundImageViewHandler(ReboundImageView reboundImageView) {
            m_reBoundImageView = new WeakReference<>(reboundImageView);
        }


        @Override
        public void handleMessage(Message msg) {
            if (m_reBoundImageView.get() != null) {
                m_reBoundImageView.get().handleStateMessage(msg);
            }
        }

    }

    private void handleStateMessage(Message msg) {
        if (viewHeight == 0 || viewWidth == 0) {
            return;
        }
        if (msg.what == 0x123) {
            if (!isReverse) {
                offsetY += FRAMES;
                //正向
                if (offsetY + viewHeight < imageHeight) {
                    scrollTo(0, offsetY);
                } else {
                    isReverse = true;

                }
            } else {
                //反向
                offsetY -= FRAMES;
                if (offsetY > 0) {
                    scrollTo(0, offsetY);
                } else {
                    isReverse = false;
                }
            }

        }
    }

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
            imageWidth = (int) (imageWidth * widthScale);
            imageHeight = (int) (imageHeight * widthScale);
            //将图片的宽高变换成view等宽，进行等比扩大或者缩放
            Matrix matrix = new Matrix();
            matrix.setScale(widthScale, widthScale);
            setImageMatrix(matrix);
        }


    }


}
