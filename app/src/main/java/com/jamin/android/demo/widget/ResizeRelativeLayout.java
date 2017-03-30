package com.jamin.android.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class ResizeRelativeLayout extends RelativeLayout {

    private ResizeLayoutCallback m_callback = null;

    public ResizeRelativeLayout(Context context) {
        super(context);
        if (isInEditMode()) {
            return;
        }
    }

    public ResizeRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
    }

    public void setCallback(ResizeLayoutCallback callback) {
        m_callback = callback;
        if (isInEditMode()) {
            return;
        }
    }

    @Override
    protected void onSizeChanged(int w, final int h, int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (isInEditMode()) {
            return;
        }
        // HelperFunc.debug("onSizeChanged w=" + w + ",h=" + h + ",oldw=" + oldw
        // + ",oldh=" + oldh);
        if (m_callback == null)
            return;

        m_callback.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (isInEditMode()) {
            return;
        }
        // HelperFunc
        // .debug("onLayout l=" + l + ", t=" + t + ",r=" + r + ",b=" + b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isInEditMode()) {
            return;
        }
        // HelperFunc.debug("onMeasure widthMeasureSpec=" + widthMeasureSpec
        // + ", heightMeasureSpec=" + heightMeasureSpec);
    }

    public interface ResizeLayoutCallback {
        void onSizeChanged(final int w, final int h, int oldw, final int oldh);
    }
}

