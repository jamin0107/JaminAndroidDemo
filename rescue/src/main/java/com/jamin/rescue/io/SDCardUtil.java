package com.jamin.rescue.io;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by wangjieming on 2017/8/2.
 *
 */

public class SDCardUtil {


    static final  boolean desireAssertionStatus = (!SDCardUtil.class.desiredAssertionStatus());


    public static SDCardSize getSdCardSize() {

        SDCardSize sdCardSize = null;
        File  file = Environment.getDataDirectory();
        if (file != null) {
            try {
                StatFs statFs = new StatFs(file.getPath());
                long availableBlocksLong = SDCardUtil.getAvailableBlocksLong(statFs);
                long blockCountLong = SDCardUtil.getBlockCountLong(statFs);
                long blockSizeLong = SDCardUtil.getBlockSizeLong(statFs);
                sdCardSize = new SDCardSize();
                sdCardSize.total = blockCountLong * blockSizeLong;
                sdCardSize.used = availableBlocksLong * blockSizeLong;
                if (sdCardSize.total < sdCardSize.used) {
                    sdCardSize.used = sdCardSize.total;
                }
            } catch (Exception e) {

            }
        }
        return sdCardSize;
    }

    private static long getAvailableBlocksLong(StatFs statFs) {
        if (!desireAssertionStatus && statFs == null) {
            throw new AssertionError();
        } else if (Build.VERSION.SDK_INT < 18) {
            return (long) statFs.getAvailableBlocks();
        } else {
            Method method;
            try {
                method = statFs.getClass().getMethod("getAvailableBlocksLong", new Class[0]);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                method = null;
            }
            if (method == null) {
                return (long) statFs.getAvailableBlocks();
            }
            try {
                return (Long) method.invoke(statFs, new Object[0]);
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
                return (long) statFs.getAvailableBlocks();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
                return (long) statFs.getAvailableBlocks();
            } catch (InvocationTargetException e4) {
                e4.printStackTrace();
                return (long) statFs.getAvailableBlocks();
            }
        }
    }

    private static long getBlockCountLong(StatFs statFs) {
        if (!desireAssertionStatus && statFs == null) {
            throw new AssertionError();
        } else if (Build.VERSION.SDK_INT < 18) {
            return (long) statFs.getBlockCount();
        } else {
            Method method;
            try {
                method = statFs.getClass().getMethod("getBlockCountLong", new Class[0]);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                method = null;
            }
            if (method == null) {
                return (long) statFs.getBlockCount();
            }
            try {
                return (Long) method.invoke(statFs, new Object[0]);
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
                return (long) statFs.getBlockCount();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
                return (long) statFs.getBlockCount();
            } catch (InvocationTargetException e4) {
                e4.printStackTrace();
                return (long) statFs.getBlockCount();
            }
        }
    }

    private static long getBlockSizeLong(StatFs statFs) {
        if (!desireAssertionStatus && statFs == null) {
            throw new AssertionError();
        } else if (Build.VERSION.SDK_INT < 18) {
            return (long) statFs.getBlockSize();
        } else {
            Method method;
            try {
                method = statFs.getClass().getMethod("getBlockSizeLong", new Class[0]);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                method = null;
            }
            if (method == null) {
                return (long) statFs.getBlockSize();
            }
            try {
                return (Long) method.invoke(statFs, new Object[0]);
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
                return (long) statFs.getBlockSize();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
                return (long) statFs.getBlockSize();
            } catch (InvocationTargetException e4) {
                e4.printStackTrace();
                return (long) statFs.getBlockSize();
            }
        }
    }
}
