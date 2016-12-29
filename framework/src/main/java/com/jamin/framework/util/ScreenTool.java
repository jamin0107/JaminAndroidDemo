package com.jamin.framework.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

//获得屏幕的像素
public class ScreenTool {

    // 返回屏幕宽和高
    public static Screen getScreenPix(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return new Screen(dm.widthPixels, dm.heightPixels, dm.density);
    }

    public static class Screen {

        public float density;
        public int widthPixels;
        public int heightPixels;

        public Screen() {
        }

        public Screen(int widthPixels, int heightPixels, float density) {
            this.widthPixels = widthPixels;
            this.heightPixels = heightPixels;
            this.density = density;
        }

        @Override
        public String toString() {
            return "(" + widthPixels + "," + heightPixels + "," + density + ")";
        }
    }
}
