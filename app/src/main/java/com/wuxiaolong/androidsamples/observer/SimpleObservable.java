package com.wuxiaolong.androidsamples.observer;

import java.util.Observable;

/**
 * Created by WuXiaolong
 * on: 2017-06-08 15:14
 * 个人博客: http://wuxiaolong.me
 */
public class SimpleObservable extends Observable {
    private int data = 0;

    public int getData() {
        return data;
    }

    public void setData(int i) {
        this.data = i;
    }

    static SimpleObservable observable;

    public static synchronized SimpleObservable getInstance() {
        if (observable == null) {
            observable = new SimpleObservable();
        }
        return observable;
    }

    public SimpleObservable post() {
        setChanged();
        //只有在setChange()被调用后，notifyObservers()才会去调用update()，否则什么都不干。
        notifyObservers();
        return observable;
    }

    public SimpleObservable post(Object arg) {
        setChanged();
        //只有在setChange()被调用后，notifyObservers()才会去调用update()，否则什么都不干。
        notifyObservers(arg);
        return observable;
    }

}