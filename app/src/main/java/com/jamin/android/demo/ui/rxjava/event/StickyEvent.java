package com.jamin.android.demo.ui.rxjava.event;

import com.jamin.framework.rxjava.BaseEvent;

/**
 * Created by jamin on 2017/5/10.
 */

public class StickyEvent extends BaseEvent {

    public int number;


    public StickyEvent(int number) {
        super();
        this.number = number;
    }

}
