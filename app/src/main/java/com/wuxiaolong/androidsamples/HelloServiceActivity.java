package com.wuxiaolong.androidsamples;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wuxiaolong.androidsamples.service.HelloService;

public class HelloServiceActivity extends AppCompatActivity {
    HelloService.HelloBinder mBinder;
    //Service和Activity通信
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (HelloService.HelloBinder) service;
            mBinder.startDownload();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_service);
        findViewById(R.id.start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HelloService.class);
                startService(intent);
                Intent broadcast = new Intent();
                broadcast.setAction("com.wuxiaolong.apksample.HelloBroadcastReceiver");
                if (android.os.Build.VERSION.SDK_INT >= 12) {
                    intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);//3.1以后的版本需要设置Intent.FLAG_INCLUDE_STOPPED_PACKAGES
                }
                broadcast.putExtra("msg", "这是一个普通广播");
                sendBroadcast(broadcast);
            }
        });
        findViewById(R.id.stop_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HelloService.class);
                stopService(intent);
            }
        });
        findViewById(R.id.bind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用者与服务绑定在了一起，调用者一旦退出，服务也就终止
                Intent intent = new Intent(getApplicationContext(), HelloService.class);
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
            }
        });
        findViewById(R.id.unbind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //让Service和Activity解除关联
                unbindService(serviceConnection);
            }
        });

    }

}
