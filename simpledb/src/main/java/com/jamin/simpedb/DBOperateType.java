package com.jamin.simpedb;

import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Created by wangjieming on 2017/8/3.
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.SOURCE)
@IntDef({
        DBManager.OPERATE_INSERT,
        DBManager.OPERATE_REPLACE,
        DBManager.OPERATE_UPDATE,
        DBManager.OPERATE_DELETE,
        DBManager.OPERATE_SELECT,
})
@interface DBOperateType {

}
