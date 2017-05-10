package com.jamin.android.demo.ui.base;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by jamin on 2016/12/14.
 */

public abstract class BaseFragment extends Fragment {

    /**
     * 是否当前处于暂停状态
     */
    private boolean mIsPause;

    protected Context context;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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


}
