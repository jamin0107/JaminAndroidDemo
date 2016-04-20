package com.jamin.framework.db.base;

import java.util.List;

public interface DBOperateAsyncListener {

    /**
     *
     * @param optionType
     * @param claz
     * @param successModels
     * @param failModels
     * @param <T>
     */
	public <T extends BaseModel> void onPostExecute(DatabaseOptionType optionType, Class<T> claz, List<T> successModels, List<T> failModels);
}
