package com.jamin.framework.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by jamin on 15-4-2.
 */
public class LogUtil {

    private static boolean DEBUG = true;

    public static String customTagPrefix = "JaminDebug";


    private static LogHandler logHandler;
    private static boolean logcatEnable = false;


    public static void init(boolean bLogcatEnable, String bCustomTagPrefix) {
        logcatEnable = bLogcatEnable;
        customTagPrefix = bCustomTagPrefix;
        HandlerThread handlerThread = new HandlerThread("LogException");
        handlerThread.start();
        logHandler = new LogHandler(handlerThread.getLooper());
    }


    /**
     * 生成TAG.
     *
     * @param caller
     * @return
     */
    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }


    public static void d(final String content) {
        if (!DEBUG) return;
        StackTraceElement caller = getCallerStackTraceElement();
        final String tag = generateTag(caller);
        logHandler.post(new Runnable() {
            @Override
            public void run() {
                if (customLogger != null) {
                    customLogger.d(tag, content);
                } else {
                    Log.d(tag, content);
                }
            }
        });

    }

    public static void d(final String content, final Throwable tr) {
        if (!DEBUG) return;
        StackTraceElement caller = getCallerStackTraceElement();
        final String tag = generateTag(caller);
        logHandler.post(new Runnable() {
            @Override
            public void run() {
                if (customLogger != null) {
                    customLogger.d(tag, content, tr);
                } else {
                    Log.d(tag, content, tr);
                }
            }

        });
    }

    public static void e(final String content) {
        if (!DEBUG) return;
        StackTraceElement caller = getCallerStackTraceElement();
        final String tag = generateTag(caller);
        logHandler.post(new Runnable() {
            @Override
            public void run() {
                if (customLogger != null) {
                    customLogger.e(tag, content);
                } else {
                    Log.e(tag, content);
                }
            }
        });
    }

    public static void e(final String content, final Throwable tr) {
        if (!DEBUG) return;
        StackTraceElement caller = getCallerStackTraceElement();
        final String tag = generateTag(caller);
        logHandler.post(new Runnable() {
            @Override
            public void run() {
                if (customLogger != null) {
                    customLogger.e(tag, content, tr);
                } else {
                    Log.e(tag, content, tr);
                }
            }
        });
    }

    public static void i(final String content) {
        if (!DEBUG) return;
        StackTraceElement caller = getCallerStackTraceElement();
        final String tag = generateTag(caller);
        logHandler.post(new Runnable() {
            @Override
            public void run() {
                if (customLogger != null) {
                    customLogger.i(tag, content);
                } else {
                    Log.i(tag, content);
                }
            }
        });
    }

    public static void i(final String content, final Throwable tr) {
        if (!DEBUG) return;
        StackTraceElement caller = getCallerStackTraceElement();
        final String tag = generateTag(caller);
        logHandler.post(new Runnable() {
            @Override
            public void run() {
                if (customLogger != null) {
                    customLogger.i(tag, content, tr);
                } else {
                    Log.i(tag, content, tr);
                }
            }
        });
    }

    public static void v(final String content) {
        if (!DEBUG) return;
        StackTraceElement caller = getCallerStackTraceElement();
        final String tag = generateTag(caller);
        logHandler.post(new Runnable() {
            @Override
            public void run() {
                if (customLogger != null) {
                    customLogger.v(tag, content);
                } else {
                    Log.v(tag, content);
                }
            }
        });

    }

    public static void v(final String content, final Throwable tr) {
        if (!DEBUG) return;
        StackTraceElement caller = getCallerStackTraceElement();
        final String tag = generateTag(caller);
        logHandler.post(new Runnable() {
            @Override
            public void run() {
                if (customLogger != null) {
                    customLogger.v(tag, content, tr);
                } else {
                    Log.v(tag, content, tr);
                }
            }
        });
    }

    public static void w(final String content) {
        if (!DEBUG) return;
        StackTraceElement caller = getCallerStackTraceElement();
        final String tag = generateTag(caller);
        logHandler.post(new Runnable() {
            @Override
            public void run() {
                if (customLogger != null) {
                    customLogger.w(tag, content);
                } else {
                    Log.w(tag, content);
                }
            }
        });
    }

    public static void w(final String content, final Throwable tr) {
        if (!DEBUG) return;
        StackTraceElement caller = getCallerStackTraceElement();
        final String tag = generateTag(caller);
        logHandler.post(new Runnable() {
            @Override
            public void run() {
                if (customLogger != null) {
                    customLogger.w(tag, content, tr);
                } else {
                    Log.w(tag, content, tr);
                }
            }
        });
    }


    public static void w(final Throwable tr) {
        if (!DEBUG) return;
        StackTraceElement caller = getCallerStackTraceElement();
        final String tag = generateTag(caller);
        logHandler.post(new Runnable() {
            @Override
            public void run() {
                if (customLogger != null) {
                    customLogger.w(tag, tr);
                } else {
                    Log.w(tag, tr);
                }
            }
        });
    }


    public static void wtf(final String content) {
        if (!DEBUG) return;
        StackTraceElement caller = getCallerStackTraceElement();
        final String tag = generateTag(caller);

        logHandler.post(new Runnable() {
            @Override
            public void run() {
                if (customLogger != null) {
                    customLogger.wtf(tag, content);
                } else {
                    Log.wtf(tag, content);
                }
            }
        });
    }

    public static void wtf(final String content, final Throwable tr) {
        if (!DEBUG) return;
        StackTraceElement caller = getCallerStackTraceElement();
        final String tag = generateTag(caller);
        logHandler.post(new Runnable() {
            @Override
            public void run() {
                if (customLogger != null) {
                    customLogger.wtf(tag, content, tr);
                } else {
                    Log.wtf(tag, content, tr);
                }
            }
        });
    }

    public static void wtf(final Throwable tr) {
        if (!DEBUG) return;
        StackTraceElement caller = getCallerStackTraceElement();
        final String tag = generateTag(caller);
        logHandler.post(new Runnable() {
            @Override
            public void run() {
                if (customLogger != null) {
                    customLogger.wtf(tag, tr);
                } else {
                    Log.wtf(tag, tr);
                }
            }
        });
    }


    public static CustomLogger customLogger;


    //可自定义
    public interface CustomLogger {

        void d(String tag, String content);

        void d(String tag, String content, Throwable tr);

        void e(String tag, String content);

        void e(String tag, String content, Throwable tr);

        void i(String tag, String content);

        void i(String tag, String content, Throwable tr);

        void v(String tag, String content);

        void v(String tag, String content, Throwable tr);

        void w(String tag, String content);

        void w(String tag, String content, Throwable tr);

        void w(String tag, Throwable tr);

        void wtf(String tag, String content);

        void wtf(String tag, String content, Throwable tr);

        void wtf(String tag, Throwable tr);
    }

    static class LogHandler extends Handler {

        LogHandler(Looper looper) {
            super(looper);
        }

    }

}