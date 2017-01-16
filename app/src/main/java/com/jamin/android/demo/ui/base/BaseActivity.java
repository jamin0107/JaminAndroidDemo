package com.jamin.android.demo.ui.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jamin.framework.util.HardWareEventListener;

import java.lang.ref.WeakReference;

/**
 * Created by jamin on 2016/12/14.
 */

public class BaseActivity extends AppCompatActivity {

    /**
     * 该activity持有的handler类
     */
    private BaseHandler mHandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new BaseHandler(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public BaseActivity getActivity() {
        return this;
    }


    private static class BaseHandler extends Handler {


        private final WeakReference<BaseActivity> m_activity;

        BaseHandler(BaseActivity ac) {
            m_activity = new WeakReference(ac);
        }


        @Override
        public void handleMessage(Message msg) {
            if (m_activity.get() != null) {
                m_activity.get().handleStateMessage(msg);
            }
        }

    }

    protected void handleStateMessage(Message msg) {

    }

    /**
     * 获取hander对象<BR>
     *
     * @return 返回handler对象
     */
    protected final Handler getHandler() {
        return mHandler;
    }

    /**
     * 发送消息
     *
     * @param what 消息标识
     */
    protected final void sendEmptyMessage(int what) {
        if (mHandler != null) {
            mHandler.sendEmptyMessage(what);
        }
    }

    /**
     * 延迟发送空消息
     *
     * @param what        消息标识
     * @param delayMillis 延迟时间
     */
    protected final void sendEmptyMessageDelayed(int what, long delayMillis) {
        if (mHandler != null) {
            mHandler.sendEmptyMessageDelayed(what, delayMillis);
        }
    }

    /**
     * post一段操作到UI线程
     *
     * @param runnable Runnable
     */
    protected final void post(Runnable runnable) {
        if (mHandler != null) {
            mHandler.post(runnable);
        }
    }

    /**
     * post一段操作到UI线程
     *
     * @param runnable Runnable
     */
    protected final void postDelay(Runnable runnable, int delayMillis) {
        if (mHandler != null) {
            mHandler.postDelayed(runnable, delayMillis);
        }
    }

    /**
     * 发送消息
     *
     * @param msg 消息对象
     */
    protected final void sendMessage(Message msg) {
        if (mHandler != null) {
            mHandler.sendMessage(msg);
        }
    }

    /**
     * 延迟发送消息
     *
     * @param msg         消息对象
     * @param delayMillis 延迟时间
     */
    protected final void sendMessageDelayed(Message msg, long delayMillis) {
        if (mHandler != null) {
            mHandler.sendMessageDelayed(msg, delayMillis);
        }
    }

}
