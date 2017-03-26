package com.jamin.android.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.jamin.android.demo.R;
import com.jamin.framework.util.LogUtil;
import com.jamin.framework.util.ScreenTool;


public class ParallaxScrollListView extends ListView implements OnScrollListener {

    public final static double NO_ZOOM = 1;
    public final static double ZOOM_X2 = 2;


    private RelativeLayout mImageLayout;
    private int mDrawableMaxHeight = -1;
    private int mImageLayoutHeight = -1;
    private int mLayoutHeight = -1;
    private int mDefaultImageViewHeight = 0;

    private interface OnOverScrollByListener {

        boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                             int scrollY, int scrollRangeX, int scrollRangeY,
                             int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent);
    }

    private interface OnTouchEventListener {
        void onTouchEvent(MotionEvent ev);
    }

    public ParallaxScrollListView(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ParallaxScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ParallaxScrollListView(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context) {
        mDefaultImageViewHeight = context.getResources().getDimensionPixelSize(R.dimen.size_default_parallax_header_height);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }


    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        boolean isCollapseAnimation = false;
        //LogUtil.d("overScrollBy isCollapseAnimation = " + isCollapseAnimation);
        isCollapseAnimation = scrollByListener.overScrollBy(deltaX, deltaY,
                scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
                maxOverScrollY, isTouchEvent);

        return isCollapseAnimation ? true : super.overScrollBy(deltaX, deltaY,
                scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
                maxOverScrollY, isTouchEvent);
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mImageLayout == null) {
            return;
        }
        LogUtil.d("onScrollChanged l = " + l + " ,  t = " + t + " , oldl = " + oldl + " , oldt = " + oldt);
        View firstView = (View) mImageLayout.getParent();
        // firstView.getTop < getPaddingTop means mImageLayout will be covered by top padding,
        // so we can layout it to make it shorter
        LogUtil.d("onScrollChanged firstView.getTop() = " + firstView.getTop() + " ,  getPaddingTop() = " + getPaddingTop() +
                " , mImageLayout.getHeight() = " + mImageLayout.getHeight() + " , mImageLayoutHeight = " + mImageLayoutHeight);
        if (firstView.getTop() < getPaddingTop() && mImageLayout.getHeight() > mImageLayoutHeight) {
            mImageLayout.getLayoutParams().height = Math.max(mImageLayout.getHeight() - (getPaddingTop() - firstView.getTop()), mImageLayoutHeight);
            // to set the firstView.mTop to 0,
            // maybe use View.setTop() is more easy, but it just support from Android 3.0 (API 11)
            firstView.layout(firstView.getLeft(), 0, firstView.getRight(), firstView.getHeight());
            mImageLayout.requestLayout();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        touchListener.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

//    public void setParallaxImageView(RoundedImageView iv) {
//        mImageLayout = iv;
//        mImageLayout.setScaleType(RoundedImageView.ScaleType.CENTER_CROP);
//    }

    public void setParallaxImageView(RelativeLayout iv) {
        mImageLayout = iv;
        //mImageLayout.setScaleType(RoundedImageView.ScaleType.CENTER_CROP);
    }

    public void setViewsBounds() {
        if (mImageLayout == null) {
            return;
        }
        if (mImageLayoutHeight == -1) {
            mImageLayoutHeight = mImageLayout.getHeight();
            if (mImageLayoutHeight <= 0) {
                mImageLayoutHeight = mDefaultImageViewHeight;
            }
            //double ratio = ((double) mImageLayout.getDrawable().getIntrinsicWidth()) / ((double) mImageLayout.getWidth());
            mDrawableMaxHeight = ScreenTool.getScreenPix(getContext()).heightPixels;
        }
    }

    private OnOverScrollByListener scrollByListener = new OnOverScrollByListener() {
        @Override
        public boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                    int scrollY, int scrollRangeX, int scrollRangeY,
                                    int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            LogUtil.d("overScrollBy deltaY = " + deltaY + ", scrollY = " + scrollY + ", maxOverScrollY = " + maxOverScrollY + ", scrollRangeY = " + scrollRangeY);
            if (mImageLayout.getHeight() <= mDrawableMaxHeight && isTouchEvent) {

                if (deltaY < 0) {
                    int deltaYDamp = deltaY;
                    //let first item offset become 1/2
                    //int deltaYDamp = deltaY / 2;
                    if (mImageLayout.getHeight() - deltaYDamp >= mImageLayoutHeight) {
                        mImageLayout.getLayoutParams().height = mImageLayout.getHeight() - deltaYDamp < mDrawableMaxHeight ?
                                mImageLayout.getHeight() - deltaYDamp : mDrawableMaxHeight;
                        mImageLayout.requestLayout();
                    }
                } else {
                    if (mImageLayout.getHeight() > mImageLayoutHeight) {
                        mImageLayout.getLayoutParams().height = mImageLayout.getHeight() - deltaY > mImageLayoutHeight ?
                                mImageLayout.getHeight() - deltaY : mImageLayoutHeight;
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
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                LogUtil.d("ACTION_UP = ");
                if (mImageLayoutHeight - 1 < mImageLayout.getHeight()) {
                    LogUtil.d("jamin - mImageLayoutHeight =" + mImageLayoutHeight + " , mImageLayout.getHeight() = " + mImageLayout.getHeight());
                    LogUtil.d("jamin - ScreenTool.getScreenPix(getContext()).heightPixels =" + ScreenTool.getScreenPix(getContext()).heightPixels);

                    ResetAnimation animation;
                    if (mImageLayout.getHeight() < ScreenTool.getScreenPix(getContext()).heightPixels / 3) {
                        animation = new ResetAnimation(mImageLayout, mImageLayoutHeight);
                    } else {
                        animation = new ResetAnimation(mImageLayout, ScreenTool.getScreenPix(getContext()).heightPixels);
                    }

                    animation.setDuration(300);
                    mImageLayout.startAnimation(animation);
                }
            }
        }
    };

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
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {
            //LogUtil.d("applyTransformation : interpolatedTime = " + interpolatedTime);
            int newHeight;
            newHeight = (int) (targetHeight - extraHeight * (1 - interpolatedTime));
            mView.getLayoutParams().height = newHeight;
            mView.requestLayout();
        }
    }

}
