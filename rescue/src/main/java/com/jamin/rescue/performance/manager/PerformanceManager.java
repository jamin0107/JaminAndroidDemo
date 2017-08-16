package com.jamin.rescue.performance.manager;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.jamin.rescue.Rescue;
import com.jamin.rescue.dao.PerformanceModelDao;
import com.jamin.rescue.db.RescueDBFactory;
import com.jamin.rescue.utils.FileUtil;
import com.jamin.rescue.utils.Utils;
import com.jamin.rescue.log.manager.PrepareDataListener;
import com.jamin.rescue.model.KeyPathPerformanceModel;
import com.jamin.simpedb.BaseModel;
import com.jamin.simpedb.DBOperateSelectListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjieming on 2017/8/12.
 */

public class PerformanceManager {


    private static final String PERFORMANCE_DIR = "/performance/";
    private static final String FILE_NAME_PREFIX_KEY_PATH = "keypath_";


    private Application application;
    private Handler handler = new Handler(Looper.getMainLooper());
    private String performanceDir = null;


    public PerformanceManager(Application context) {
        application = context;
    }


    @UiThread
    public void preparePerformanceData(final PrepareDataListener prepareDataListener) {
        if (prepareDataListener == null) {
            return;
        }

        final long currentTimeMillis = System.currentTimeMillis();
        PerformanceModelDao performanceDao = RescueDBFactory.getInstance().performanceDao;
        if (performanceDao == null) {
            return;
        }
        if (Rescue.DEBUG) {
            Log.d("PerformanceManager.", "preparePerformanceData currentTimeMillis = " + currentTimeMillis);
        }
        performanceDao.getPerformanceModelListByTime(currentTimeMillis, new DBOperateSelectListener() {
            @Override
            public <T extends BaseModel> void onSelectCallBack(List<T> list) {
                prepareFile(currentTimeMillis, (List<KeyPathPerformanceModel>) list, prepareDataListener);
            }
        });

    }


    @WorkerThread
    private void prepareFile(long currentTimeMillis, List<KeyPathPerformanceModel> list, final PrepareDataListener prepareDataListener) {
        List<KeyPathPerformanceModel> groupByPerformanceList = groupByData(list);
//        FileOutputStream fos = new FileOutputStream();
        String logFileName = String.valueOf(currentTimeMillis) + ".txt";
        //删除log dir.上传前都重新创建
        deletePerformanceDir();
        //重新创建log文件.
        final File performanceFile = new File(getPerformanceDirPath() + logFileName);
        try {
            new File(getPerformanceDirPath()).mkdirs();
            performanceFile.createNewFile();
            writeData2File(groupByPerformanceList, performanceFile);
//            FileUtil.saveStringToFile(logDataStr, logDataFile, "UTF-8");
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
                    prepareDataListener.prepared(performanceFile.getPath(), null);
                }
            }
        });


    }

    private void writeData2File(List<KeyPathPerformanceModel> groupByPerformanceList, File performanceFile) {
        FileOutputStream outputStream = null;
        for (KeyPathPerformanceModel model : groupByPerformanceList) {
            if (Rescue.DEBUG) {
                Log.d("PerformanceManger", "PerformanceManager = " + new Gson().toJson(model));
            }
        }
        try {
            //Header
            outputStream = new FileOutputStream(performanceFile);
            String deviceInfo = "Device:" + Utils.getPhoneBrand() + " " + Utils.getPhoneModel() + "\n";
            outputStream.write(deviceInfo.getBytes());
            String apiInfo = "API_LEVEL:" + Utils.getBuildLevel() + "\n";
            outputStream.write(apiInfo.getBytes());
            String buildInfo = "SDK:" + Utils.getBuildVersion() + "\n";
            outputStream.write(buildInfo.getBytes());
            String versionName = "AppVersionName:" + Utils.getVersionName(application) + "\n";
            outputStream.write(versionName.getBytes());
            String versionCode = "AppVersionCode:" + Utils.getVersionCode(application) + "\n";
            outputStream.write(versionCode.getBytes());
            String enter = "\n";
            outputStream.write(enter.getBytes());
            if (Rescue.DEBUG) {
                Log.d("PerformanceManger", deviceInfo + apiInfo + buildInfo + versionName + versionCode + enter);
            }
            //Body
            for (KeyPathPerformanceModel model : groupByPerformanceList) {
                String key = "key:" + model.key_path + "\n";
                outputStream.write(key.getBytes());
                String page = "FromPage:" + model.fromPage + ",ToPage:" + model.toPage + "\n";
                outputStream.write(page.getBytes());
                String times = "Times:" + model.times + "\n";
                outputStream.write(times.getBytes());
                String cost = "AverageCost:" + (model.totalTime / model.times) + "ms,MaxCost:" + model.maxCost + "ms,MinCost:" + model.minCost + "ms\n";
                outputStream.write(cost.getBytes());
                outputStream.write(enter.getBytes());
                if (Rescue.DEBUG) {
                    Log.d("PerformanceManger", key + page + times + cost + enter);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                }
            }
        }


    }


    /**
     * get log cache dir
     *
     * @return
     */
    private String getPerformanceDirPath() {
        if (!TextUtils.isEmpty(performanceDir)) {
            return performanceDir;
        }
        performanceDir = application.getExternalCacheDir() + PERFORMANCE_DIR;
        return performanceDir;
    }


    /**
     * delete log dir
     */
    private void deletePerformanceDir() {
        FileUtil.deleteDirWithFile(new File(getPerformanceDirPath()));
    }


    @WorkerThread
    private List<KeyPathPerformanceModel> groupByData(List<KeyPathPerformanceModel> list) {
        //将正序的model按分组合并,并算出minCost,maxCost,times
        List<KeyPathPerformanceModel> newKeyPathModelList = new ArrayList<>();
        KeyPathPerformanceModel tempModel = null;
        int keyPath = -1;
        for (int i = 0; i < list.size(); i++) {
            KeyPathPerformanceModel model = list.get(i);
            //第一次,初始化
            if (keyPath < 0) {
                tempModel = new KeyPathPerformanceModel();
                tempModel.fromPage = model.fromPage;
                tempModel.toPage = model.toPage;
                tempModel.key_path = model.key_path;
                tempModel.maxCost = model.cost_time;
                tempModel.minCost = model.cost_time;
                tempModel.totalTime = model.cost_time;
                tempModel.times = 1;
            }

            //如果是同一keypath,只需计算maxCost,minCoust,averageCost
            if (tempModel != null && keyPath > 0 && model.key_path == keyPath) {
                if (tempModel.maxCost < model.cost_time) {
                    tempModel.maxCost = model.cost_time;
                }
                if (tempModel.minCost > model.cost_time) {
                    tempModel.minCost = model.cost_time;
                }
                tempModel.times++;
                tempModel.totalTime += model.cost_time;
            }
            //如果不是同一个keypath了.
            if (tempModel != null && keyPath > 0 && model.key_path != keyPath) {
                //把之前的加入列表
                KeyPathPerformanceModel resultModel = tempModel;
                newKeyPathModelList.add(resultModel);
                //下一次循环开始了.
                tempModel = new KeyPathPerformanceModel();
                tempModel.fromPage = model.fromPage;
                tempModel.toPage = model.toPage;
                tempModel.key_path = model.key_path;
                tempModel.maxCost = model.cost_time;
                tempModel.minCost = model.cost_time;
                tempModel.totalTime = model.cost_time;
                tempModel.times = 1;
            }
            keyPath = model.key_path;
            //如果是最后一次,直接加入列表
            if (i == list.size() - 1) {
                newKeyPathModelList.add(tempModel);
            }
        }
        return newKeyPathModelList;
    }


}
