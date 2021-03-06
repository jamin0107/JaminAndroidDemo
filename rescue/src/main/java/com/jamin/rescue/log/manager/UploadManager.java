package com.jamin.rescue.log.manager;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.UiThread;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.jamin.rescue.Rescue;
import com.jamin.rescue.dao.LogModelDao;
import com.jamin.rescue.db.RescueDBFactory;
import com.jamin.rescue.model.LogModel;
import com.jamin.rescue.utils.FileUtil;
import com.jamin.simpedb.BaseModel;
import com.jamin.simpedb.DBOperateDeleteListener;
import com.jamin.simpedb.DBOperateSelectListener;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by wangjieming on 2017/8/2.
 */


public class UploadManager {


    private static final String LOG_DIR = "/log/";
    //private static final String ZIP_FILE_NAME = "log.zip";

    private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    private Application application;
    //StringBuilder tag;
    private String logDir;
    //private String tempDir;
    private Handler handler = new Handler(Looper.getMainLooper());
    private AtomicBoolean uploading = new AtomicBoolean(false);
    private long uploadFlag;

    public UploadManager(Application context) {
        application = context;
    }


    /**
     * prepare the zipfile for upload
     * return the zipfile path and params through UploadListener
     *
     * @param prepareDataListener
     */
    @UiThread
    public void prepareLogData(final PrepareDataListener prepareDataListener) {
        if(prepareDataListener == null){
            return;
        }
        if (uploading.get()) {
            prepareDataListener.uploading();
            if (Rescue.DEBUG) {
                Log.d("Rescue", "prepareLogData , please call uploaded() to finish upload action");
            }
            return;
        } else {
            uploading.set(true);
        }

        uploadFlag = System.currentTimeMillis();
        LogModelDao logModelDao = RescueDBFactory.getInstance().logModelDao;
        if (logModelDao == null) {
            return;
        }
        if (Rescue.DEBUG) {
            Log.d("Rescue.", "UploadManager prepareLogData uploadFlag = " + uploadFlag);
        }
        logModelDao.getLogModelListByTime(uploadFlag, new DBOperateSelectListener() {
            @Override
            public <T extends BaseModel> void onSelectCallBack(List<T> list) {
                prepareTagAndWriteFile((List<LogModel>) list, prepareDataListener);
            }
        });

    }


    /**
     * call this method  after uploaded
     * <p>
     * 1.delete cache and log dir.
     * 2.delete uploaded data from db.
     */
    @UiThread
    public void uploaded() {
        if (Rescue.DEBUG) {
            Log.d("Rescue.UploadManager", "uploaded uploadFlag = " + uploadFlag);
        }
        deleteUploadedData(uploadFlag);
        uploading.set(false);
    }


    /**
     * /**
     * 1.prepare tag
     * 2.write upload db data to file
     * 3.
     *
     * @param list
     * @param prepareDataListener
     */
    @UiThread
    private void prepareTagAndWriteFile(final List<LogModel> list, final PrepareDataListener prepareDataListener) {
        //开线程准备上传资料
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {

                if (list == null || list.size() == 0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //主线程通知UI.
                            if (prepareDataListener != null) {
                                prepareDataListener.prepared(null, null);
                            }
                        }
                    });
                    return;
                }
                //取出所有tag
                if (Rescue.DEBUG) {
                    Log.d("Rescue.UploadManager", "list.size  = " + list.size());
                }
                final String tag = getTagStr(list);
                //将log数据写入cache/log/路径下的文件
                String logDataStr = new Gson().toJson(list);
                String logFileName = String.valueOf(System.currentTimeMillis()) + ".txt";
                //删除log dir.上传前都重新创建
                deleteLogDir();
                //重新创建log文件.
                final File logDataFile = new File(getLogDirPath() + logFileName);
                try {
                    new File(getLogDirPath()).mkdirs();
                    logDataFile.createNewFile();
                    FileUtil.saveStringToFile(logDataStr, logDataFile, "UTF-8");
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //主线程通知UI.
                            if (prepareDataListener != null) {
                                prepareDataListener.prepared(null, null);
                            }
                        }
                    });
                    return;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //主线程通知UI.
                        if (prepareDataListener != null) {
                            prepareDataListener.prepared(logDataFile.getPath(), tag);
                        }
                    }
                });

            }
        });

    }


    private String getTagStr(List<LogModel> list) {
        StringBuilder stringBuilder = new StringBuilder();
        HashSet<String> set = new HashSet<>();
        for (LogModel logModel : list) {
            set.add(logModel.tag);
        }
        int i = 0;
        for (String tag : set) {
            i++;
            if (!TextUtils.isEmpty(tag)) {
                stringBuilder.append(tag);
                if (i < set.size()) {
                    stringBuilder.append(",");
                }
            }
        }
        if (Rescue.DEBUG) {
            Log.d("Rescue.UploadManager", "tag.size =" + set.size() + ", tag=(" + stringBuilder + ")");
        }
        return String.valueOf(stringBuilder);

    }


    /**
     * get log cache dir
     *
     * @return
     */
    private String getLogDirPath() {
        if (!TextUtils.isEmpty(logDir)) {
            return logDir;
        }
        logDir = application.getExternalCacheDir() + LOG_DIR;
        return logDir;
    }


//    /**
//     * the log zip file absolute path
//     *
//     * @return
//     */
//    private String getZipFileName() {
//        return getLogDirPath() + ZIP_FILE_NAME;
//    }
//

    /**
     * delete log dir
     */
    private void deleteLogDir() {
        FileUtil.deleteDirWithFile(new File(getLogDirPath()));
    }


    /**
     * call this method after uploaded.
     * <p>
     * 1.delete log folder
     * 2.delete uploaded data from db
     *
     * @param uploadedFlag
     */
    @UiThread
    private void deleteUploadedData(long uploadedFlag) {
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                //删除log目录
                deleteLogDir();
            }
        });
        //删除数据库中对应的数据
        LogModelDao logModelDao = RescueDBFactory.getInstance().logModelDao;
        if (logModelDao != null) {
            logModelDao.deleteByTime(uploadedFlag, new DBOperateDeleteListener() {
                @Override
                public <T extends BaseModel> void onDeleteCallback(Class<T> claz, int rows) {
                    if (Rescue.DEBUG) {
                        Log.d("Rescue.UploadManager", "deleteUploadedData.size  = " + rows);
                    }
                }
            });
        }
    }


}
