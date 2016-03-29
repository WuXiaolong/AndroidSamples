package com.wuxiaolong.androidsamples;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by WuXiaolong on 2015/11/2.
 */
public class HelloBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 判断当前接收到的Broadcast是否是收到短信的action
        Log.d("wxl", "intent.getAction()====" + intent.getAction());
        if (intent.getAction().equals("com.wuxiaolong.apksample.HelloBroadcastReceiver")) {
            Toast.makeText(context,
                    "接收到Broadcast，消息为：" + intent.getStringExtra("msg"),
                    Toast.LENGTH_SHORT).show();
        }

    }
}
