package com.jamin.rescue.upload;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jamin.rescue.io.FileUtil;
import com.jamin.rescue.model.LogModel;
import com.jamin.rescue.dao.LogModelDao;
import com.jamin.simpedb.BaseModel;
import com.jamin.simpedb.DBOperateSelectListener;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by wangjieming on 2017/8/2.
 *
 */

public class UploadManager {


    private static final String LOG_DIR = "/log/";
    private static final String ZIP_FILE_NAME = "log.zip";

    private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    private Application application;
    StringBuilder tag;
    private String logDir;
    private String tempDir;
    private Handler handler = new Handler(Looper.getMainLooper());
    private AtomicBoolean uploading = new AtomicBoolean(false);


    public UploadManager(Application context) {
        application = context;
    }


    /**
     * prepare the zipfile for upload
     * return the zipfile path and params through UploadListener
     *
     * @param uploadListener
     */
    public void upload(final UploadListener uploadListener) {
        if (uploading.get()) {
            return;
        } else {
            uploading.set(true);
        }
        //开线程准备上传资料
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                //删除log dir.上传前都重新创建
                deleteLogDir();
                boolean ready = prepareUploadData();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //主线程通知UI.
                        if (uploadListener != null) {
                            uploadListener.upload(getZipFileName(), String.valueOf(tag));
                        }
                    }
                });

            }
        });

    }


    /**
     * call this method  after uploaded
     * <p>
     * 1.delete cache and log dir.
     * 2.delete uploaded data from db.
     */
    public void uploaded() {
        deleteUploadedData();
    }


    /**
     * prepare data for upload.
     * <p>
     * 1.write the data from db to temp file.
     * 2.delete temp file.
     *
     * @return
     */
    private boolean prepareUploadData() {
        //数据库拿到日志数据
        new LogModelDao().getLogModelList(new DBOperateSelectListener() {
            @Override
            public <T extends BaseModel> void onSelectCallBack(List<T> list) {
                prepareTagAndWriteFile((List<LogModel>) list);
            }
        });
        return true;

    }

    private void prepareTagAndWriteFile(List<LogModel> list) {
        if (list == null || list.size() == 0)
            return;
        //取出所有tag
        tag = new StringBuilder();
        int i = 0;
        for (LogModel logModel : list) {
            i++;
            if (!TextUtils.isEmpty(logModel.tag)) {
                tag.append(logModel.tag);
                if (i < list.size()) {
                    tag.append(",");
                }
            }
        }
        //将log数据写入cache/log/路径下的文件
        String logDataStr = new Gson().toJson(list);
        String logFileName = String.valueOf(System.currentTimeMillis());
        File logDataFile = new File(logFileName);
        FileUtil.saveStringToFile(logDataStr, logDataFile, "UTF-8");
        //打压缩包
        FileUtil.compress(new File[]{logDataFile}, new File(getZipFileName()));
        //压缩之后删除临时文件.
        logDataFile.delete();
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
        File file = new File(logDir);
        file.mkdirs();
        return logDir;
    }


    /**
     * the log zip file absolute path
     *
     * @return
     */
    private String getZipFileName() {
        return getLogDirPath() + ZIP_FILE_NAME;
    }


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
     */
    private void deleteUploadedData() {
        //删除log目录
        deleteLogDir();
        //删除数据库中对应的数据
//        new LogModelDao().delete();

    }


}
