package com.jamin.framework.db.base;

public interface DBOperateDeleteListener {
	/**
	 * 
	 * @param claz
	 * @param rows 删除的条数
	 */
	public <T extends BaseModel>void onDeleteCallback(Class<T> claz, int rows);
}
