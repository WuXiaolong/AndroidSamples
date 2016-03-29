package com.wuxiaolong.androidsamples.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wuxiaolong.androidsamples.R;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class RxJavaActivity extends AppCompatActivity {
    String tag = "wxl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
    }


    private void rxJavaTest() {
        String[] from = {"Hello", "RxJava"};
//        Observable observable = Observable.from(from);
        Subscription subscription = Observable.just("Hello", "RxJava", "WuXiaolong")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Log.i("wxl", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("wxl", "onNext=" + s);
                    }
                });
        addSubscription(subscription);
//        observable.subscribe(new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                Log.i("wxl", "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.i("wxl", "onNext=" + s);
//            }
//        });
//        observable.subscribe(new Subscriber() {
//            @Override
//            public void onCompleted() {
//                Log.i("wxl", "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//                Log.i("wxl", "onNext=" + o);
//            }
//        });
//        Observable<Integer> observable1 = Observable.just(1, 3, 5);
//        Observable<Integer> observable2 = Observable.just(2, 4, 6, 9);
//        Observable.just(1, 2, 3)
//                .startWith(1)
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.i("wxl", "onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.i("wxl", "onNext=" + integer);
//                    }
//                });
//        List<String> list = new ArrayList<>();
//        list.add("Hello");
//        list.add("RxJava");
//
//        Observable.interval(1000,TimeUnit.MILLISECONDS)
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.i("wxl", "onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.i("wxl", "onNext=" + s);
//                    }
//                });
    }

    private CompositeSubscription mCompositeSubscription;

    public void addSubscription(Subscription subscription) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(subscription);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
