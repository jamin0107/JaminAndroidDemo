package com.jamin.rescue.io;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by wangjieming on 2017/8/2.
 */

public class Utils {


    public static int getVersionCode(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException ignored) {
        } catch (RuntimeException ignored) {
        }
        return 0;
    }


    public static String getVersionName(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            if (packageInfo != null) {
                return packageInfo.versionName;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        } catch (RuntimeException ignored) {
        }

        return "";

    }

}
