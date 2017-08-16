package com.jamin.logger;

import android.app.Application;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.jamin.logger.window.LogFloatLayerManager;
import com.jamin.logger.window.LogInfo;

/**
 * Created by jamin on 15-4-2.
 * 全局静态日志类
 */
public class Logger {

    private static boolean DEBUG = true;

    public static String customTagPrefix = "logger";


    private static LogHandler logHandler;

    private static LogFloatLayerManager logFloatLayerManager;


    public static void init(boolean bLogcatEnable, String bCustomTagPrefix) {
        DEBUG = bLogcatEnable;
        if (!DEBUG) {
            return;
        }
        customTagPrefix = bCustomTagPrefix;
        HandlerThread handlerThread = new HandlerThread("LogException");
        handlerThread.start();
        logHandler = new LogHandler(handlerThread.getLooper());
    }

    public static void initLogWindow(Application application) {
        if (logFloatLayerManager != null) {
            return;
        }
        logFloatLayerManager = new LogFloatLayerManager(application);
        logFloatLayerManager.showLogFloatLayer();
    }

    public static void registerLogReceiver() {
        if (logFloatLayerManager != null) {
            logFloatLayerManager.registerLogReceiver();
        }
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

    private static StackTraceElement getCallerStackTraceElement() {
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
                if (logFloatLayerManager != null) {
                    logFloatLayerManager.addItem(LogInfo.DEBUG, tag + " --> " + content);
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
                if (logFloatLayerManager != null) {
                    logFloatLayerManager.addItem(LogInfo.DEBUG, tag + " --> " + content);
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
                if (logFloatLayerManager != null) {
                    logFloatLayerManager.addItem(LogInfo.ERROR, tag + " --> " + content);
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
                if (logFloatLayerManager != null) {
                    logFloatLayerManager.addItem(LogInfo.ERROR, tag + " --> " + content);
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
                if (logFloatLayerManager != null) {
                    logFloatLayerManager.addItem(LogInfo.INFO, tag + " --> " + content);
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
                if (logFloatLayerManager != null) {
                    logFloatLayerManager.addItem(LogInfo.INFO, tag + " --> " + content);
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
                if (logFloatLayerManager != null) {
                    logFloatLayerManager.addItem(LogInfo.VERBOSE, tag + " --> " + content);
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
                if (logFloatLayerManager != null) {
                    logFloatLayerManager.addItem(LogInfo.VERBOSE, tag + " --> " + content);
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
                if (logFloatLayerManager != null) {
                    logFloatLayerManager.addItem(LogInfo.WARN, tag + " --> " + content);
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
                if (logFloatLayerManager != null) {
                    logFloatLayerManager.addItem(LogInfo.WARN, tag + " --> " + content);
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
                if (logFloatLayerManager != null) {
                    logFloatLayerManager.addItem(LogInfo.WARN, tag + " --> " + tr.getMessage());
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
                if (logFloatLayerManager != null) {
                    logFloatLayerManager.addItem(LogInfo.WTF, tag + " --> " + content);
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
//                if (customLogger != null) {
//                    customLogger.wtf(tag, content, tr);
//                } else {
                Log.wtf(tag, content, tr);
//                }
                if (logFloatLayerManager != null) {
                    logFloatLayerManager.addItem(LogInfo.WTF, tag + " --> " + content);
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
//                if (customLogger != null) {
//                    customLogger.wtf(tag, tr);
//                } else {
                Log.wtf(tag, tr);
//                }
                if (logFloatLayerManager != null) {
                    logFloatLayerManager.addItem(LogInfo.WTF, tag + " --> " + tr.getMessage());
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