package com.jamin.android.demo.ui.image.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;

/**
 * Created by jamin on 2017/4/20.
 */

public class FilterGroup {

    HashMap<String, GPUImageFilter> filterMap = new HashMap<String, GPUImageFilter>();

    HashMap<String, Float> mAdjustMap = new HashMap<>();


    public void addFilter(GPUImageFilter gpuImageFilter) {
        if (!filterMap.containsKey(gpuImageFilter.getClass().getSimpleName())) {
            filterMap.put(gpuImageFilter.getClass().getSimpleName(), gpuImageFilter);
        }
    }


    public GPUImageFilter getFilterGroup() {
        Iterator iterator = filterMap.keySet().iterator();
        List<GPUImageFilter> list = new ArrayList<>();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            list.add(filterMap.get(key));
        }
        GPUImageFilterGroup filterGroup = new GPUImageFilterGroup(list);
        return filterGroup;
    }


    public StringBuilder getFilterName() {
        StringBuilder stringBuilder = new StringBuilder("");
        Iterator iterator = filterMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            stringBuilder.append(key + "-\n");
        }
        return stringBuilder;
    }

    public void clear() {
        filterMap.clear();
        mAdjustMap.clear();
    }
}
