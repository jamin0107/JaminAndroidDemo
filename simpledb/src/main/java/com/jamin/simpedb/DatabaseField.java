package com.jamin.simpedb;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
public @interface DatabaseField {
	
	String columnName() default "";//列名
	
	boolean generatedId() default false;//是否是自增长字段 
	
	boolean primaryKey() default false;//是否是primarykey
	
	boolean index() default false; //是否需要索引
	
	boolean canBeNull() default true;//是否可以为空
	
	boolean unique() default false; //是否唯一
}
