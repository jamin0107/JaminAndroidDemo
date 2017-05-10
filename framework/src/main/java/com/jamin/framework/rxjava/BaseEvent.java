package com.jamin.framework.rxjava;

/**
 * Created by jamin on 2017/5/10.
 */

public abstract class BaseEvent {

    public long id;//事件ID

    public BaseEvent() {
        id = System.currentTimeMillis();
    }





}
