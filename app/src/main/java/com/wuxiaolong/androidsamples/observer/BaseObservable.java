package com.wuxiaolong.androidsamples.observer;

import java.util.Observable;

/**
 * Created by WuXiaolong
 * on: 2017-06-08 15:53
 * 个人博客: http://wuxiaolong.me
 */
public class BaseObservable<T> extends Observable {
    public T observable;

    public  T  post() {
        setChanged();
        //只有在setChange()被调用后，notifyObservers()才会去调用update()，否则什么都不干。
        notifyObservers();
        return observable;
    }
}
