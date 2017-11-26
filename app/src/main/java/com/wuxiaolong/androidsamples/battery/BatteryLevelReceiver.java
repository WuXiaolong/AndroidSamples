package com.wuxiaolong.androidsamples.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by WuXiaolong on 2017/11/26.
 * 个人博客：http：//wuxiaolong.me
 */

public class BatteryLevelReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //当前剩余电量
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        //电量最大值
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        //电量百分比
        float batteryPct = level / (float) scale;
        Log.d("wxl", "batteryPct=" + batteryPct);
        Toast.makeText(context, "batteryPct=" + batteryPct, Toast.LENGTH_LONG).show();
    }
}
