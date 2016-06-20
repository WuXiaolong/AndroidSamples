package com.wuxiaolong.androidsamples.rxjava;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class RxBus {
    private static RxBus mRxBus = null;
    /**
     * PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
     */

    private Subject<Object, Object> mRxBusObserverable = new SerializedSubject<>(PublishSubject.create());

    public static synchronized RxBus getInstance() {
        if (mRxBus == null) {
            mRxBus = new RxBus();
        }
        return mRxBus;
    }

    public void post(Object o) {
        if (hasObservers())
            mRxBusObserverable.onNext(o);
    }

    public Observable<Object> toObserverable() {
        return mRxBusObserverable;
    }

    /**
     * 判断是否有订阅者
     */
    public boolean hasObservers() {
        return mRxBusObserverable.hasObservers();
    }
}
