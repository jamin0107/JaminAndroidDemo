package com.jamin.android.demo.ui.base;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

/**
 * Created by jamin on 2016/12/14.
 */

public class BaseFragment extends Fragment {

    /**
     * 是否当前处于暂停状态
     */
    private boolean mIsPause;

    protected Context context;
    private Handler m_handler = null;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        m_handler = new JaminHandler();
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsPause = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsPause = true;
    }

    protected boolean isPause() {
        return mIsPause;
    }


    public final Handler getHandler() {
        return m_handler;
    }


    class JaminHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            processMessage(msg);
        }
    }


    public void processMessage(Message msg) {

    }

    public final void sendMessage(int what, Object obj) {
        Handler handler = getHandler();
        if (handler == null)
            return;
        Message msg = handler.obtainMessage(what);
        if (obj != null) {
            msg.obj = obj;
        }
        handler.sendMessage(msg);
    }

    public final void sendMessage(int what) {
        if (what == -1)
            return;
        sendMessage(what, null);
    }

    public final boolean postDelayed(Runnable r, long delay) {
        Handler h = getHandler();
        if (h == null) {
            return false;
        }
        return h.postDelayed(r, delay);
    }


    public final boolean post(Runnable r) {
        Handler h = getHandler();
        if (h == null) {
            return false;
        }
        return h.post(r);
    }


}
