package com.wuxiaolong.androidsamples.battery;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wuxiaolong.androidsamples.R;

public class BatteryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);

        // 充电状态
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        Log.d("wxl", "isCharging=" + isCharging);
//        Toast.makeText(this, "isCharging=" + isCharging, Toast.LENGTH_LONG).show();

        // 设备是通过 USB 还是交流充电器进行充电
        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        Log.d("wxl", "usbCharge=" + usbCharge + ",acCharge=" + acCharge);
//        Toast.makeText(this, "usbCharge=" + usbCharge + ",acCharge=" + acCharge, Toast.LENGTH_LONG).show();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
//        this.registerReceiver(new PowerConnectionReceiver(), intentFilter);
//        this.registerReceiver(new BatteryLevelReceiver(), intentFilter);


        intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(new NetworkChangedReceiver(), intentFilter);
    }
}
