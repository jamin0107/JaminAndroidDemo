package com.jamin.android.demo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.jamin.android.demo.R;
import com.jamin.framework.util.ImageUtil;

/**
 * Created by jamin on 2016/11/29.
 */

public class CornerLayout2 extends RelativeLayout {

    public CornerLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CornerLayout2(Context context) {
        super(context);
        init();
    }

    private final RectF roundRect = new RectF();
    private float rect_adius = 10;
    private final Paint maskPaint = new Paint();
    private final Paint zonePaint = new Paint();

    private void init() {
        maskPaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //
        zonePaint.setAntiAlias(true);
        zonePaint.setColor(Color.BLUE);
        //
        float density = getResources().getDisplayMetrics().density;
        rect_adius = rect_adius * density;
    }

    public void setCorner(float adius) {
        rect_adius = adius;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = getWidth();
        int h = getHeight();
        roundRect.set(0, 0, w, h);
    }

//    圆角rect区域
    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawRoundRect(roundRect, rect_adius, rect_adius, zonePaint);
        canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        canvas.restore();
    }

    //心形Bitmap做的Mask
//    @Override
//    public void draw(Canvas canvas) {
//        canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
//        Bitmap bitmap = ImageUtil.decodeResource(getResources(), R.mipmap.heart_mask);
//        Matrix mMatrix = new Matrix();
//        mMatrix.setScale(3.5f, 3.5f);
//        // 创建新的图片
//        Bitmap resizedBitmap = ImageUtil.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
//                bitmap.getHeight(), mMatrix, true);
//        canvas.drawBitmap(resizedBitmap, 0, 0, zonePaint);
//        canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
//        super.draw(canvas);
//        canvas.restore();
//    }


}