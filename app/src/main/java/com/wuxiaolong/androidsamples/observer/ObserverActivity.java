package com.wuxiaolong.androidsamples.observer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wuxiaolong.androidsamples.R;

public class ObserverActivity extends AppCompatActivity {
    private SimpleObservable simpleObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer);
        SimpleObservable.getInstance().post().setData(1);
        SimpleObservable.getInstance().post().setData(2);
    }

}
