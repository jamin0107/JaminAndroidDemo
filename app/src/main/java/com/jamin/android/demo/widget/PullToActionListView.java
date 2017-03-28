package com.jamin.android.demo.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.jamin.android.demo.R;
import com.jamin.framework.util.ImageUtil;
import com.jamin.framework.util.LogUtil;
import com.jamin.framework.util.ScreenTool;

import java.util.Random;


/**
 * Created by jamin on 2017/3/24.
 * 下拉实现某些功能的ListView
 */
public class PullToActionListView extends ListView implements OnScrollListener {

    private boolean DEBUG = true;

    private int max_alpha_height = 0;//当item下滑15%的时候，从透明变成不透明
    private int cam_animation_height = 0;//当item扩大到60%的时候,背景中的相机开始淡入
    private int camAnimationScaleHeight = 0;//当item在60%的基础上，再下滑15%的过程中调整比例扩大到60dp结束
    private int expand_height = 0;//展开70%之后不可收回


    //视差效果参数，暂时不用
    public final static double NO_ZOOM = 1;
    public final static double ZOOM_X2 = 2;

    float touchRawY = 0;//記錄touch down的高度，如果大於第一個item的高度，則不會拉開第一個item

    private RelativeLayout mImageLayout;
    private int mDrawableMaxHeight = -1;//第一个item被允许拉到的极限值，屏幕高度
    private int mDefaultImageViewHeight = 0;//第一个item的默认初始值，屏幕40%高度
    private ExpandCallback expandedListener;//header item展开之后的回调

    String[] randomCoverColor = {"#ffe45f", "#5fd491", "#709fff", "#ff7a8c"};
    boolean colorInited = false;//顏色值，每當回到初始位置就重新初始化
    int colorPosition = 0;//記錄隨機顏色的color position 對應randomCoverColor的顏色值


    private interface OnOverScrollByListener {

        boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY,
                             int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent);

    }

    private interface OnTouchEventListener {
        void onTouchEvent(MotionEvent ev);
    }

    public void setExpandedListener(ExpandCallback callback) {
        this.expandedListener = callback;
    }

    public PullToActionListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public PullToActionListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToActionListView(Context context) {
        super(context);
        init();
    }

    public void init() {
        int screenHeight = ScreenTool.getScreenPix(getContext()).heightPixels;

        //极限值设定为屏幕高度
        mDrawableMaxHeight = screenHeight;
        cam_animation_height = (int) (0.6 * screenHeight);
        max_alpha_height = (int) (0.15 * screenHeight);
        camAnimationScaleHeight = (int) (0.15 * screenHeight);
        expand_height = (int) (0.3 * screenHeight);

    }

    /**
     * 设置第一个item的默认高度。
     * 有什么好的设置办法，以后在考虑。
     *
     * @param defaultHeight
     */
    public void setDefaultImageViewHeight(int defaultHeight) {
        mDefaultImageViewHeight = defaultHeight;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }


    /**
     * 得到本次下拉時的背景
     * @return
     */
    public String getRadomColor() {
        return randomCoverColor[colorPosition];
    }


    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        boolean isCollapseAnimation = scrollByListener.overScrollBy(deltaX, deltaY,
                scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
                maxOverScrollY, isTouchEvent);
        return isCollapseAnimation || super.overScrollBy(deltaX, deltaY,
                scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
                maxOverScrollY, isTouchEvent);
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mImageLayout == null) {
            return;
        }
        int imageLayoutHeight = mImageLayout.getHeight();
        //无论上滑，下滑，都根据目前位置重新计算mask的alpha和各种动画效果
        setMaskAlpha(imageLayoutHeight);
        setCamImgScale(imageLayoutHeight);
        if (DEBUG) {
            LogUtil.d("onScrollChanged l = " + l + " ,  t = " + t + " , oldl = " + oldl + " , oldt = " + oldt);
            // firstView.getTop < getPaddingTop means mImageLayout will be covered by top padding,
            // so we can layout it to make it shorter
            LogUtil.d("onScrollChanged firstView.getTop() = " + mImageLayout.getTop() + " ,  getPaddingTop() = " + getPaddingTop() +
                    " , imageLayoutHeight = " + imageLayoutHeight + " , mImageLayoutHeight = " + mDefaultImageViewHeight);
        }
        if (mImageLayout.getTop() < getPaddingTop() && imageLayoutHeight > mDefaultImageViewHeight) {
            mImageLayout.getLayoutParams().height = Math.max(imageLayoutHeight - (getPaddingTop() - mImageLayout.getTop()), mDefaultImageViewHeight);
            // to set the firstView.mTop to 0,
            // maybe use View.setTop() is more easy, but it just support from Android 3.0 (API 11)
            mImageLayout.layout(mImageLayout.getLeft(), 0, mImageLayout.getRight(), imageLayoutHeight);
            mImageLayout.requestLayout();
        }

        if (expandedListener != null) {
            float expandpersent = (float) (mImageLayout.getHeight() - mDefaultImageViewHeight) / expand_height;
            expandedListener.statusChange(expandpersent);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        touchListener.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }


    private OnOverScrollByListener scrollByListener = new OnOverScrollByListener() {
        @Override
        public boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY,
                                    int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            if (DEBUG) {
                LogUtil.d("overScrollBy deltaY = " + deltaY + ", scrollY = " + scrollY + ", maxOverScrollY = " + maxOverScrollY + ", scrollRangeY = " + scrollRangeY);
            }
            if (touchRawY >= mDefaultImageViewHeight) {
                return true;
            }
            int imageLayoutHeight = mImageLayout.getHeight();

            if (imageLayoutHeight <= mDrawableMaxHeight && isTouchEvent) {
                if (deltaY < 0) {
                    int deltaYDamp = deltaY;
                    //设置第一个item跟随手势放大的速率 ，可以指定比例，算是加阻尼
                    //let first item offset become 1/2
                    //int deltaYDamp = deltaY / 2;
                    if (imageLayoutHeight - deltaYDamp >= mDefaultImageViewHeight) {
                        mImageLayout.getLayoutParams().height = imageLayoutHeight - deltaYDamp < mDrawableMaxHeight ?
                                imageLayoutHeight - deltaYDamp : mDrawableMaxHeight;
                        mImageLayout.requestLayout();
                    }
                } else {
                    if (imageLayoutHeight >= mDefaultImageViewHeight) {
                        mImageLayout.getLayoutParams().height = imageLayoutHeight - deltaY > mDefaultImageViewHeight ?
                                imageLayoutHeight - deltaY : mDefaultImageViewHeight;
                        mImageLayout.requestLayout();
                        return true;
                    }
                }
            }
            return false;
        }
    };

    private OnTouchEventListener touchListener = new OnTouchEventListener() {
        @Override
        public void onTouchEvent(MotionEvent ev) {
            if (mImageLayout == null) {
                return;
            }
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                //記錄touch的位置
                touchRawY = ev.getRawY();
                if (DEBUG) {
                    LogUtil.d("ACTION_DOWN = touchRawY = " + touchRawY);
                }
            }
            //松手之后，确定是回弹，还是继续下滑
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                if (DEBUG) {
                    LogUtil.d("ACTION_UP = ");
                }

                if (mDefaultImageViewHeight - 1 < mImageLayout.getHeight()) {
                    if (DEBUG) {
                        LogUtil.d("jamin - mImageLayoutHeight =" + mDefaultImageViewHeight + " , mImageLayout.getHeight() = " + mImageLayout.getHeight());
                        LogUtil.d("jamin - ScreenTool.getScreenPix(getContext()).heightPixels =" + ScreenTool.getScreenPix(getContext()).heightPixels);
                    }

                    ResetAnimation animation;
                    //第一个item扩大200以内，回弹
                    if (mImageLayout.getHeight() - mDefaultImageViewHeight <= expand_height) {
                        //回弹
                        animation = new ResetAnimation(mImageLayout, mDefaultImageViewHeight);
                    } else {
                        //继续下滑
                        animation = new ResetAnimation(mImageLayout, ScreenTool.getScreenPix(getContext()).heightPixels);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if (expandedListener != null) {
                                    expandedListener.expanded();
                                }
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                    animation.setDuration(300);
                    mImageLayout.startAnimation(animation);
                }
            }
        }
    };

    /**
     * 回弹动画
     */
    public class ResetAnimation extends Animation {
        int targetHeight;
        int originalHeight;
        int extraHeight;
        View mView;

        protected ResetAnimation(View view, int targetHeight) {
            this.mView = view;
            this.targetHeight = targetHeight;
            originalHeight = view.getHeight();
            extraHeight = this.targetHeight - originalHeight;
        }


        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            //LogUtil.d("applyTransformation : interpolatedTime = " + interpolatedTime);
            int newHeight;
            newHeight = (int) (targetHeight - extraHeight * (1 - interpolatedTime));
            mView.getLayoutParams().height = newHeight;
            mView.requestLayout();
        }
    }


    public void setParallaxImageView(RelativeLayout iv) {
        mImageLayout = iv;
    }


    public void setViewsBounds() {
//        if (mImageLayout == null) {
//            return;
//        }
//        if (mImageLayoutHeight == -1) {
//            mImageLayoutHeight = mImageLayout.getHeight();
//            if (mImageLayoutHeight <= 0) {
//                mImageLayoutHeight = mDefaultImageViewHeight;
//            }
//            //double ratio = ((double) mImageLayout.getDrawable().getIntrinsicWidth()) / ((double) mImageLayout.getWidth());
//            mDrawableMaxHeight = ScreenTool.getScreenPix(getContext()).heightPixels;
//        }
    }

    /**
     * 第一个item从原始高度扩大MAX_ALPHA_HEIGHT，动态改变alpha
     *
     * @param imageLayoutHeight 当前headeritem的高度
     */
    private void setMaskAlpha(int imageLayoutHeight) {
        if (mImageLayout == null) {
            return;
        }
        LogUtil.d("setMaskAlpha imageLayoutHeight = " + imageLayoutHeight + ",  mDefaultImageViewHeight = " + mDefaultImageViewHeight);
        ImageView imageView = (ImageView) mImageLayout.findViewById(R.id.list_item_cover_mask);
        if (imageLayoutHeight - mDefaultImageViewHeight <= 0) {
            imageView.setAlpha(0f);
            if (!colorInited) {
                colorInited = true;
                Random random = new Random();
                colorPosition = random.nextInt(randomCoverColor.length);
                imageView.setBackgroundColor(Color.parseColor(randomCoverColor[colorPosition]));
                if (DEBUG) {
                    LogUtil.d("setMaskAlpha alpha = " + 0 + ", colorPosition = " + colorPosition);
                }
            }
        } else if (imageLayoutHeight - mDefaultImageViewHeight >= max_alpha_height) {
            //下滑距離大於300px，設置不透明
            imageView.setAlpha(1f);
            if (colorInited) {
                colorInited = false;
            }
            if (DEBUG) {
                LogUtil.d("setMaskAlpha alpha = " + 1);
            }
        } else if (imageLayoutHeight - mDefaultImageViewHeight <= max_alpha_height) {
            float alpha = ((float) (imageLayoutHeight - mDefaultImageViewHeight)) / max_alpha_height;
            if (DEBUG) {
                LogUtil.d("setMaskAlpha alpha = " + alpha);
            }
            imageView.setAlpha(alpha);
        }

    }

    /**
     * @param imageLayoutHeight
     */
    private void setCamImgScale(int imageLayoutHeight) {
        if (mImageLayout == null) {
            return;
        }
        if (DEBUG) {
            LogUtil.d("setCamImgScale imageLayoutHeight = " + imageLayoutHeight + ",  mDefaultImageViewHeight = " + mDefaultImageViewHeight);
        }
        ImageView camImageView = (ImageView) mImageLayout.findViewById(R.id.list_item_cam);
        if (imageLayoutHeight <= cam_animation_height) {
            camImageView.getLayoutParams().height = 0;
            camImageView.getLayoutParams().width = 0;
            camImageView.setVisibility(GONE);
            if (DEBUG) {
                LogUtil.d("setCamImgScale hide");
            }
            return;
        }

        int camSize = ImageUtil.dp2px(getContext() , 60);
        if (imageLayoutHeight - cam_animation_height <= camAnimationScaleHeight) {
            //下滑距離大於300px，設置不透明
            float textSizeScale = ((float) (imageLayoutHeight - cam_animation_height)) / camAnimationScaleHeight;
            camImageView.getLayoutParams().height = (int) (textSizeScale * camSize);
            camImageView.getLayoutParams().width = (int) (textSizeScale * camSize);
            camImageView.setVisibility(VISIBLE);
            if (DEBUG) {
                LogUtil.d("setCamImgScale text size = " + textSizeScale * camSize);
            }
        } else {
            camImageView.getLayoutParams().height = camSize;
            camImageView.getLayoutParams().width = camSize;
            camImageView.setVisibility(VISIBLE);
            if (DEBUG) {
                LogUtil.d("setCamImgScale text max size = " + camSize);
            }
        }


    }

    public interface ExpandCallback {
        /**
         * 首頁展開動畫結束
         */
        void expanded();

        void statusChange(float status);
    }
}
