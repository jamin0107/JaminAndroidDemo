package com.jamin.android.demo.ui.image.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;


/**
 * Created by jamin on 2017/4/20.
 */

public class FilterGroup {

    static List<GPUImageFilter> filterList = new ArrayList<>();

    static HashMap<String, Float> mAdjustMap = new HashMap<>();


    public static GPUImageFilter addFilter(GPUImageFilter gpuImageFilter) {
        for (GPUImageFilter filter : filterList) {
            if (filter.getClass().getSimpleName().equals(gpuImageFilter.getClass().getSimpleName())) {
                return filter;
            }
        }
        filterList.add(gpuImageFilter);
        return gpuImageFilter;
    }

    public static void addRange(String filterKey, float value) {
        mAdjustMap.put(filterKey, value);
    }


    public static GPUImageFilter getFilterGroup() {
        GPUImageFilterGroup filterGroup = new GPUImageFilterGroup(filterList);
        return filterGroup;
    }


    public static StringBuilder getFilterName() {
        StringBuilder stringBuilder = new StringBuilder("");
        for (GPUImageFilter filter : filterList) {
            String key = filter.getClass().getSimpleName();
            stringBuilder.append(key + "-" + mAdjustMap.get(key) + "-\n");
        }

        return stringBuilder;
    }

    public static void clear() {
        filterList.clear();
        mAdjustMap.clear();
    }
}
