package com.wuxiaolong.androidsamples.observer;

import java.util.Observable;

/**
 * Created by WuXiaolong
 * on: 2017-06-08 15:14
 * 个人博客: http://wuxiaolong.me
 * 被观察者，一个被观察者对象可以有数个观察者对象
 */
public class ConcreteObservable extends Observable {
    private int data = 0;

    public int getData() {
        return data;
    }

    public void setData(int i) {
        //具体逻辑按需
        if (data != i) {
            this.data = i;
            setChanged();
        }
    }

    private static ConcreteObservable observable;

    public static synchronized ConcreteObservable getInstance() {
        if (observable == null) {
            observable = new ConcreteObservable();
        }
        return observable;
    }

    public ConcreteObservable post() {
        //只有在setChange()被调用后，notifyObservers()才会去调用update()，否则什么都不干。
        notifyObservers();
        return observable;
    }

    public ConcreteObservable post(Object arg) {
        setChanged();
        //只有在setChange()被调用后，notifyObservers()才会去调用update()，否则什么都不干。
        notifyObservers(arg);
        return observable;
    }

}