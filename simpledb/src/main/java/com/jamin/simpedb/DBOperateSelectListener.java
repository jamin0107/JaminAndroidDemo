package com.jamin.simpedb;

import java.util.List;

public interface DBOperateSelectListener {

    <T extends BaseModel> void onSelectCallBack(List<T> list);

}
