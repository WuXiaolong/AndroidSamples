package com.wuxiaolong.androidsamples.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.wuxiaolong.androidsamples.R;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RxBusActivity extends AppCompatActivity {
    private CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);
        rxBusObservers();
        rxBusPost();
    }

    private void rxBusPost() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getInstance().post(HandleEvent.getInstance());
            }
        });

    }

    public void addSubscription(Subscription subscription) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscription);
    }


    private void rxBusObservers() {
        Subscription subscription = RxBus.getInstance()
                .toObserverable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object event) {
                        if (event instanceof HandleEvent) {
                            //do something
                            Log.d("wxl", "rxBusHandle");
                        }
                    }
                });
        addSubscription(subscription);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("wxl", "onDestroy");
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();//取消注册，以避免内存泄露
        }
    }

}
