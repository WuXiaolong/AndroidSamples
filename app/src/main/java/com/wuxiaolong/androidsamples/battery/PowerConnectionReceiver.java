package com.wuxiaolong.androidsamples.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

/**
 * Created by WuXiaolong on 2017/11/26.
 * 个人博客：http：//wuxiaolong.me
 */

public class PowerConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
        String batteryStatus = "";
        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                batteryStatus = "正在充电";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                batteryStatus = "正在放电";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                batteryStatus = "未充电";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                batteryStatus = "充满电";
                break;
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                batteryStatus = "未知道状态";
                break;
        }
        Toast.makeText(context, "batteryStatus=" + batteryStatus, Toast.LENGTH_LONG).show();

        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, BatteryManager.BATTERY_PLUGGED_AC);
        String chargePlug = "";
        switch (plugged) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                chargePlug = "AC充电";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                chargePlug = "USB充电";
                break;
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                chargePlug = "无线充电";
                break;
        }
        Toast.makeText(context, "chargePlug=" + chargePlug, Toast.LENGTH_LONG).show();
    }
}