package com.jamin.framework.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * Created by jamin on 2016/11/29.
 */

public class ImageUtil {

    public static Bitmap createBitmap(int width, int height, Bitmap.Config config) {
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
//            BitmapAjaxCallback.clearCache();
        }

        return bitmap;
    }

    public static Bitmap createBitmap(Bitmap source, int x, int y, int width, int height, Matrix m, boolean filter) {
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap
                    .createBitmap(source, x, y, width, height, m, filter);
        } catch (OutOfMemoryError e) {
//            BitmapAjaxCallback.clearCache();
        }

        return bitmap;
    }

    public static Bitmap decodeResource(Resources res, int id) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeResource(res, id);
        } catch (OutOfMemoryError e) {
//            BitmapAjaxCallback.clearCache();
        }

        return bitmap;
    }

    public static int dp2px(Context context, float dp) {
        if (context == null) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
