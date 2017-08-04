package com.jamin.simpedb;

import java.util.List;

/**
 * insert ,update ,replace use this callback
 *
 */
public interface DBOperateAsyncListener {

    <T extends BaseModel> void onPostExecute(@DBOperateType int optionType, Class<T> claz, List<T> successModels, List<T> failModels);
}
