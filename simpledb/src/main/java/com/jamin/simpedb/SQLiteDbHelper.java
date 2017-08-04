package com.jamin.simpedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

public class SQLiteDbHelper extends SQLiteOpenHelper {
	private Context context;
	private String dbName;
	private List<Class<?>> models;
	
	public SQLiteDbHelper(Context context, String name, CursorFactory factory, int version, List<Class<?>> models){
		super(context, name, factory, version);
		this.context = context;
		this.dbName = name;
		this.models = models;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		DatabaseTools.onCreate(db,User.class);
		if(models != null && models.size() > 0){
			for(Class<?> claz : models){
				DatabaseTools.onCreate(db,claz);
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(newVersion > oldVersion){
//			DatabaseTools.onUpgrade(db, dbName, context, User.class);
			if(models != null && models.size() > 0){
				for(Class<?> claz : models){
					Log.d("simpledb upgrade" , "table name = " + claz.getClass().getSimpleName());
					DatabaseTools.onUpgrade(db, dbName, context, claz);
				}
			}
			onCreate(db);
		}else if(newVersion < oldVersion){
			if(context != null && !TextUtils.isEmpty(dbName)){
				context.deleteDatabase(dbName);
			}
		}
	}

	/**
	 * android sdk version 11 以上 降级会调用该函数
	 */
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(context != null && !TextUtils.isEmpty(dbName)){
			context.deleteDatabase(dbName);
		}
	}
	
}
