package com.jamin.http.cache;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.jamin.framework.util.LogUtil;
import com.jamin.framework.util.MD5Util;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.IOException;

/**
 * Created by wangjieming on 2017/8/22.
 * 简单的file Cache
 */
public class FileCache<T> implements ICache<T> {

  private File cacheFile;

  private Class<T> classOfT;

  private volatile T mData;

  private final String FILE_CACHE = "CACHE_";

  private boolean encrypt = true;//默认加密存储.不加密需要手动设置.

  /**
   * 默认一个response对应一份cache的话，用类名做文件名
   * 请使用FileCache.Builder的方式构建FileCache
   */
  @Deprecated public FileCache(Context context, @NonNull Class<T> classOfT) {
    //用name，防止混淆过后导致SimpleName重复
    this(context, classOfT.getName(), classOfT);
  }

  /**
   * 一个response对应多个cache的情况，key允许自定义
   * 请使用FileCache.Builder的方式构建FileCache
   */
  @Deprecated public FileCache(Context context, @NonNull String cacheKey, Class<T> classOfT) {
    if (TextUtils.isEmpty(cacheKey)) {
      cacheKey = classOfT.getName();
    }
    //缓存文件cache file name md5,对应多个cache file，所以允许自定义
    String fileName = FILE_CACHE + classOfT.getSimpleName() + MD5Util.getMd5ByString(cacheKey);
    this.classOfT = classOfT;
    if (context == null) {
      return;
    }
    createCacheFileExternal(context, "", fileName);
  }

  /**
   * 一个response对应多个chache的情况，key允许自定义
   */
  FileCache(Context context, Class<T> classOfT, boolean internal, String relativeDir,
      @NonNull String cacheKey) {
    if (TextUtils.isEmpty(cacheKey)) {
      cacheKey = classOfT.getSimpleName();
    }
    /**
     * 缓存文件cache file name md5,对应多个cache file，所以允许自定义
     * CACHE_MD5
     */
    String fileName = FILE_CACHE + MD5Util.getMd5ByString(cacheKey);
    this.classOfT = classOfT;
    if (context == null) {
      return;
    }
    if (internal) {
      createCacheFileInternal(context, relativeDir, fileName);
    } else {
      createCacheFileExternal(context, relativeDir, fileName);
    }
  }

  /**
   * 在内部存储files目录下创建缓存文件
   * /data/data/packageName/files/relativeDirPath/fileName
   *
   * @param relativeDirPath template/  或者template/dir/
   */
  private void createCacheFileInternal(Context context, String relativeDirPath, String fileName) {
    cacheFile = new File(context.getFilesDir().getPath() + "/" + relativeDirPath + "/" + fileName);
    File dir = new File(context.getFilesDir().getPath() + "/" + relativeDirPath + "/");
    if (!dir.exists()) {
      dir.mkdirs();
    }
    if (!cacheFile.exists()) {
      try {
        cacheFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 外部存储位置.APP卸载会删掉.
   * /sdcard/Android/data/packageName/.file_cache/relativeDirPath/fileName
   */
  private void createCacheFileExternal(Context context, String relativeDirPath, String fileName) {
    File externalFileDir = context.getExternalFilesDir(null);
    //无法打开外部存储.就在内部创建
    if (externalFileDir == null) {
      createCacheFileInternal(context, relativeDirPath, fileName);
      return;
    }
    //外部存储空间,创建一个FileCache的根目录.
    String cacheFileRootDirPath = context.getExternalFilesDir(null).getPath() + "/.file_cache/";
    cacheFile = new File(cacheFileRootDirPath + relativeDirPath + "/" + fileName);
    File dir = new File(cacheFileRootDirPath + relativeDirPath + "/");
    if (!dir.exists()) {
      dir.mkdirs();
    }
    if (!cacheFile.exists()) {
      try {
        cacheFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 异步存储
   *
   * @return 不反回结果
   */
  @Override public void saveCache(final T data) {
    Observable.just(data)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .map(new Function<T, Boolean>() {
          @Override public Boolean apply(T t) throws Exception {
            return saveCacheSync(t);
          }
        })
        .subscribe();
  }

  /**
   * 异步存储,返回结果.
   */
  @Override public void saveCache(T data, @NonNull Observer<Boolean> observer) {
    Observable.just(data)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .map(new Function<T, Boolean>() {
          @Override public Boolean apply(T t) throws Exception {
            return saveCacheSync(t);
          }
        })
        .subscribe(observer);
  }

  @Override public Observable<T> getCache() {
    LogUtil.d("getCache");
    return Observable.just(cacheFile)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .map(new Function<File, T>() {
          @Override public T apply(File t) throws Exception {
            T tt = getCacheSync();
            if (tt == null) {
              throw Exceptions.propagate(new Throwable("No Cache"));
            }
            return tt;
          }
        });
  }

  /**
   * 也许会耗时.慎用,最好在线程中使用.
   */
  @WorkerThread public T getCacheSync() {
    LogUtil.d("readCacheSync Thread ID = " + Thread.currentThread().getId());
    if (mData != null) {
      return mData;
    }
    if (cacheFile == null) {
      return null;
    }
    synchronized (FileCache.class) {
      String str;
      if (encrypt) {
        str = EncryptIOUtils.readFileToString(cacheFile, "UTF-8");
      } else {
        str = IOUtils.readFileToString(cacheFile, "UTF-8");
      }

      if (TextUtils.isEmpty(str)) {
        return null;
      }
      try {
        mData = new Gson().fromJson(str, classOfT);
      } catch (Exception ignore) {
      }
      return mData;
    }
  }

  @Override public void clearCache() {
    Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override public void subscribe(ObservableEmitter<Integer> e) throws Exception {
        e.onNext(1);
      }
    })
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .map(new Function<Integer, Boolean>() {
          @Override public Boolean apply(Integer t) throws Exception {
            return deleteCacheSync();
          }
        })
        .subscribe();
  }

  @Override public void clearCache(Observer<Boolean> observable) {
    Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override public void subscribe(ObservableEmitter<Integer> e) throws Exception {
        e.onNext(1);
      }
    })
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .map(new Function<Integer, Boolean>() {
          @Override public Boolean apply(Integer t) throws Exception {
            return deleteCacheSync();
          }
        })
        .subscribe(observable);
  }

  /**
   * 同步save
   */
  @WorkerThread public Boolean saveCacheSync(T data) {
    LogUtil.d("saveCacheSync Thread ID = " + Thread.currentThread().getId());
    if (cacheFile == null) {
      return false;
    }
    mData = data;
    synchronized (FileCache.class) {
      String jsonStr = new Gson().toJson(data);
      LogUtil.d("HttpFileCache save Cache - " + jsonStr + "");
      if (encrypt) {
        EncryptIOUtils.saveStringToFile(jsonStr, cacheFile, "UTF-8");
      } else {
        IOUtils.saveStringToFile(jsonStr, cacheFile, "UTF-8");
      }
    }
    return true;
  }

  /**
   * 同步删除
   */
  @WorkerThread private boolean deleteCacheSync() {
    LogUtil.d("deleteCacheSync");
    mData = null;
    if (cacheFile != null && cacheFile.exists()) {
      return cacheFile.delete();
    }
    return false;
  }

  private void setUnEncrypted() {
    this.encrypt = false;
  }

  /********************************************************************
   * Builder 模式构建一个FileCache对象
   ********************************************************************/
  public static class Builder<T> {

    private Context context;
    private boolean internal;//是否存在内部存储空间
    private String relativeDir;//配置目录
    private String cacheKey;
    private Class<T> classOfT;
    private boolean unEncrypted;

    public Builder(Context context, @NonNull Class<T> classOfT) {
      this.context = context;
      this.classOfT = classOfT;
    }

    /**
     * 指定:内部存储或者外部存储
     *
     * 默认为外部存储
     *
     * @param saveInternal true 内部存储.
     */
    public Builder<T> setFileSaveInternal(boolean saveInternal) {
      this.internal = saveInternal;
      return this;
    }

    /**
     * 设置相对路径
     *
     * @param relativeDir 举例:"/Template/Audio/"
     */
    public Builder<T> setRelativeDir(String relativeDir) {
      this.relativeDir = relativeDir;
      return this;
    }

    /**
     * 自定义缓存文件的名字,生成FileName的规则 "CACHE_" + MD5(cacheKey);
     */
    public Builder<T> setCacheKey(String cacheKey) {
      this.cacheKey = cacheKey;
      return this;
    }

    /**
     * 关闭加密.
     */
    public Builder<T> unEncrypted() {
      this.unEncrypted = true;
      return this;
    }

    public FileCache<T> build() {
      FileCache<T> fileCache = new FileCache<>(context, classOfT, internal, relativeDir, cacheKey);
      if (unEncrypted) {
        fileCache.setUnEncrypted();
      }
      return fileCache;
    }
  }
}
