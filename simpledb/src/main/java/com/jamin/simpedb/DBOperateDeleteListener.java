package com.jamin.simpedb;

public interface DBOperateDeleteListener {
	/**
	 * 
	 * @param claz
	 * @param rows 删除的条数
	 */
	<T extends BaseModel>void onDeleteCallback(Class<T> claz, int rows);
}
