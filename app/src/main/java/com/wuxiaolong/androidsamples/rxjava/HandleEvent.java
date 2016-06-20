package com.wuxiaolong.androidsamples.rxjava;

/**
 * Created by Administrator
 * on 2016/6/20.
 */
public class HandleEvent {
    public static HandleEvent handleEvent;

    public static HandleEvent getInstance() {
        if (handleEvent == null) {
            handleEvent = new HandleEvent();
        }
        return handleEvent;
    }
}
