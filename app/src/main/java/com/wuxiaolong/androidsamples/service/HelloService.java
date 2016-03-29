package com.wuxiaolong.androidsamples.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by WuXiaolong on 2015/11/2.
 */
public class HelloService extends Service {
    int mStartMode;       // indicates how to behave if the service is killed
    //    IBinder mBinder;      // interface for clients that bind
    boolean mAllowRebind; // indicates whether onRebind should be used
    private HelloBinder mBinder = new HelloBinder();

    @Override
    public void onCreate() {
        // The service is being created
        Log.d("wxl", "HelloService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        Log.d("wxl", "HelloService onStartCommand");
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // A client is binding to the service with bindService()
        Log.d("wxl", "HelloService onBind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // All clients have unbound with unbindService()
        Log.d("wxl", "HelloService onUnbind");
        return mAllowRebind;
    }

    @Override
    public void onRebind(Intent intent) {
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
        Log.d("wxl", "HelloService onRebind");
    }

    public class HelloBinder extends Binder {

        public void startDownload() {
            Log.d("wxl", "HelloService startDownload() executed");
            // 执行具体的下载任务
        }

    }

    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
        Log.d("wxl", "HelloService onDestroy");
        Intent intent = new Intent(this, HelloService.class);
        startService(intent);
    }
}
