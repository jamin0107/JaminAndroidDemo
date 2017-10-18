package com.jamin.android.demo.ui.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.google.gson.Gson;
import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.adapter.CustomRecyclerViewAdapter;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.android.demo.ui.tab.item.model.Data;
import com.jamin.android.demo.ui.tab.item.model.TemplateAudioInfoList;
import com.jamin.framework.util.LogUtil;
import com.jamin.http.cache.FileCache;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjieming on 2017/10/13.
 */

public class JaminMusicActivity extends BaseActivity {

  public SwipeRefreshLayout mPullRefreshLayout;

  private CustomRecyclerViewAdapter mCustomRecyclerViewAdapter;
  private List<BaseItem> mListViewData = new ArrayList<>();
  private FileCache<TemplateAudioInfoList> mFileCache;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tab_music);
    initView();
    initData();
    readCache();
  }

  private void initData() {
    TemplateAudioInfoList templateAudioInfoList =
        new Gson().fromJson(Data.JSON_DATA, TemplateAudioInfoList.class);
    LogUtil.d(new Gson().toJson(templateAudioInfoList));
    mFileCache = new FileCache.Builder<>(this, TemplateAudioInfoList.class).build();
  }

  private void initView() {
    mPullRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.music_swipe_refresh_layout);
    mPullRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.music_recycle_view);
    mCustomRecyclerViewAdapter = new CustomRecyclerViewAdapter();
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(mCustomRecyclerViewAdapter);
  }

  private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener =
      new SwipeRefreshLayout.OnRefreshListener() {
        @Override public void onRefresh() {
          getDataFromServer();
        }
      };

  public void readCache() {
    mFileCache.getCache()
        .observeOn(Schedulers.io())
        .map(new Function<TemplateAudioInfoList, List<BaseItem>>() {
          @Override public List<BaseItem> apply(TemplateAudioInfoList templateAudioInfoList)
              throws Exception {
            return MusicHelper.bindDataFromAudioList(JaminMusicActivity.this,
                templateAudioInfoList);
          }
        })
        .subscribe(new Observer<List<BaseItem>>() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onNext(List<BaseItem> musicItems) {
            LogUtil.d("Read Cache onNext = " + musicItems.size());
            mListViewData = musicItems;
            mCustomRecyclerViewAdapter.setData(mListViewData);
            getDataFromServer();
          }

          @Override public void onError(Throwable e) {
            LogUtil.d("Read Cache onError = " + e.getMessage());
            mCustomRecyclerViewAdapter.setData(mListViewData);
            getDataFromServer();
          }

          @Override public void onComplete() {

          }
        });
  }

  public void getDataFromServer() {
    LogUtil.d("getDataFromServer");
    Observable.create(new ObservableOnSubscribe<TemplateAudioInfoList>() {
      @Override public void subscribe(ObservableEmitter<TemplateAudioInfoList> emitter)
          throws Exception {
        String jsonData = Data.JSON_DATA;
        TemplateAudioInfoList templateAudioInfoList =
            new Gson().fromJson(jsonData, TemplateAudioInfoList.class);
        emitter.onNext(templateAudioInfoList);
      }
    })
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .map(new Function<TemplateAudioInfoList, List<BaseItem>>() {
          @Override public List<BaseItem> apply(TemplateAudioInfoList templateAudioInfoList)
              throws Exception {
            return MusicHelper.bindDataFromAudioList(JaminMusicActivity.this,
                templateAudioInfoList);
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<List<BaseItem>>() {
          @Override public void onSubscribe(Disposable d) {

          }

          @Override public void onNext(List<BaseItem> musicItems) {
            LogUtil.d("getFromServer onSuccess = " + musicItems.size());
            mListViewData = musicItems;
            mCustomRecyclerViewAdapter.setData(musicItems);
            if (null != mPullRefreshLayout) {
              mPullRefreshLayout.setRefreshing(false);
            }
          }

          @Override public void onError(Throwable e) {

          }

          @Override public void onComplete() {

          }
        });
  }
}
