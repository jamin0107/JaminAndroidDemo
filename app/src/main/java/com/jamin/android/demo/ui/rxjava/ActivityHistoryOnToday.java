package com.jamin.android.demo.ui.rxjava;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.adapter.CustomRecyclerViewAdapter;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.framework.util.LogUtil;
import com.jamin.http.cache.FileCache;
import com.jamin.http.model.CloudBeanHistoryOnToday;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import cn.dreamtobe.kpswitch.widget.KPSwitchFSPanelLinearLayout;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by jamin on 2016/12/23.
 */

public class ActivityHistoryOnToday extends BaseActivity {


    @BindView(R.id.recycler_history_list)
    RecyclerView mRecyclerHistoryList;
    @BindView(R.id.layout_swipeRefresh)
    SwipeRefreshLayout mLayoutSwipeRefresh;
    @BindView(R.id.keyboard_panel)
    KPSwitchFSPanelLinearLayout panelLayout;
    @BindView(R.id.activity_history_today_edittext)
    EditText editText;

    ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;

    private CustomRecyclerViewAdapter mAdapter;
    private BaseActivity activity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_on_today);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits;
            bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }

        ButterKnife.bind(this);
        activity = this;

        refresh();
        saveHashMap();
        mLayoutSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        keyBoardListener();
    }

    private void keyBoardListener() {

        onGlobalLayoutListener = KeyboardUtil.attach(this, panelLayout, new KeyboardUtil.OnKeyboardShowingListener() {
            @Override
            public void onKeyboardShowing(boolean isShowing) {
                LogUtil.d("onKeyboardShowing = " + isShowing);
            }
        });

        findViewById(R.id.open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.showKeyboard(editText);
            }
        });
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.hideKeyboard(editText);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyboardUtil.detach(this, onGlobalLayoutListener);
    }

    private void saveHashMap() {
        FileCache<HashMap> fileCache = new FileCache(this, HashMap.class);
        HashMap<String, Object> hashMap = new HashMap<>();
        CloudBeanHistoryOnToday cloudBeanHistoryOnToday = new CloudBeanHistoryOnToday();
        cloudBeanHistoryOnToday.errorCode = 1;
        cloudBeanHistoryOnToday.list = null;
        cloudBeanHistoryOnToday.reason = "test";
        hashMap.put("aaa", "bbb");
        hashMap.put("aaaa", 111);
        hashMap.put("aaaaa", 111);
        hashMap.put("aaaaaa", cloudBeanHistoryOnToday);
        fileCache.saveCache(hashMap);
        fileCache.getCache().subscribe(new Observer<HashMap>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(HashMap hashMap) {
                LogUtil.d(new Gson().toJson(hashMap));
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

        FileCache<ArrayList> fileCache1 = new FileCache<>(this, ArrayList.class);
        ArrayList<Object> list = new ArrayList<>();
        CloudBeanHistoryOnToday cloudBeanHistoryOnToday1 = new CloudBeanHistoryOnToday();
        cloudBeanHistoryOnToday1.errorCode = 1;
        cloudBeanHistoryOnToday1.list = null;
        cloudBeanHistoryOnToday1.reason = "test";
        list.add(111);
        list.add("aaa");
        list.add(cloudBeanHistoryOnToday1);
        fileCache1.saveCache(list);
        fileCache1.getCache().subscribe(new Observer<ArrayList>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ArrayList list) {
                LogUtil.d(new Gson().toJson(list));
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }


    private void refresh() {
        request();
    }

    private void request() {
        FileCache<CloudBeanHistoryOnToday> fileCache =
            new FileCache<>(this, CloudBeanHistoryOnToday.class);

        CloudBeanHistoryOnToday cloudBeanHistoryOnToday = new CloudBeanHistoryOnToday();
        cloudBeanHistoryOnToday.errorCode = 1;
        cloudBeanHistoryOnToday.list = null;
        cloudBeanHistoryOnToday.reason = "test";

        fileCache.saveCache(cloudBeanHistoryOnToday);

        fileCache.getCache().subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CloudBeanHistoryOnToday>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CloudBeanHistoryOnToday cloudBeanHistoryOnToday) {
                        LogUtil.d(new Gson().toJson(cloudBeanHistoryOnToday));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void refreshRecyclerView(List<BaseItem> baseItems) {

        if (baseItems == null || mRecyclerHistoryList == null) {
            return;
        }
        if (mAdapter == null) {
            mAdapter = new CustomRecyclerViewAdapter(baseItems);
            mRecyclerHistoryList.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerHistoryList.setAdapter(mAdapter);
        } else {
            mAdapter.setData(baseItems);
        }

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }

        // Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            Toast.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();
        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
        }
    }
}
