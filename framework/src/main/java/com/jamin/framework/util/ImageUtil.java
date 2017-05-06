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


    /**
     * 通过bitmap转灰阶图片，可用于人脸识别
     * @param inputWidth
     * @param inputHeight
     * @param scaled
     * @return
     */
    public static  byte[] getNV12FromBitmap(int inputWidth, int inputHeight, Bitmap scaled) {
        // Reference (Variation) : https://gist.github.com/wobbals/5725412

        int[] argb = new int[inputWidth * inputHeight];

        //Log.i(TAG, "scaled : " + scaled);
        scaled.getPixels(argb, 0, inputWidth, 0, 0, inputWidth, inputHeight);

//        byte[] yuv = new byte[inputWidth * inputHeight * 3 / 2];
        byte[] yuv = new byte[inputWidth * inputHeight];
        encodeYUV420SP(yuv, argb, inputWidth, inputHeight);

        scaled.recycle();

        return yuv;
    }

    private static  void encodeYUV420SP(byte[] yuv420sp, int[] argb, int width, int height) {
        final int frameSize = width * height;

        int yIndex = 0;
        int uvIndex = frameSize;

        int a, R, G, B, Y, U, V;
        int index = 0;
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

//                a = (argb[index] & 0xff000000) >> 24; // a is not used obviously
                R = (argb[index] & 0xff0000) >> 16;
                G = (argb[index] & 0xff00) >> 8;
                B = (argb[index] & 0xff) >> 0;

                // well known RGB to YUV algorithm
                Y = ((66 * R + 129 * G + 25 * B + 128) >> 8) + 16;
//                V = ((-38 * R - 74 * G + 112 * B + 128) >> 8) + 128; // Previously U
//                U = ((112 * R - 94 * G - 18 * B + 128) >> 8) + 128; // Previously V

                yuv420sp[yIndex++] = (byte) ((Y < 0) ? 0 : ((Y > 255) ? 255 : Y));
//                if (j % 2 == 0 && index % 2 == 0) {
//                    yuv420sp[uvIndex++] = (byte) ((V < 0) ? 0 : ((V > 255) ? 255 : V));
//                    yuv420sp[uvIndex++] = (byte) ((U < 0) ? 0 : ((U > 255) ? 255 : U));
//                }

                index++;
            }
        }
    }
}
