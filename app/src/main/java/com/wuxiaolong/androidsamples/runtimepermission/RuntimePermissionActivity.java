package com.wuxiaolong.androidsamples.runtimepermission;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wuxiaolong.androidsamples.BaseActivity;
import com.wuxiaolong.androidsamples.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.BODY_SENSORS;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CALENDAR;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.SEND_SMS;

/**
 * Created by WuXiaolong
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public class RuntimePermissionActivity extends BaseActivity implements onPermissionCallbackListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime_permission);
    }

    public void onPermission(View v) {
        switch (v.getId()) {
            case R.id.calendar:
                //日历
                requestRuntimePermission(READ_CALENDAR, this);
                break;
            case R.id.camera:
                //照相机
                requestRuntimePermission(CAMERA, this);
                break;
            case R.id.contacts:
                //通讯录
                requestRuntimePermission(READ_CONTACTS, this);
                break;
            case R.id.location:
                //定位
                requestRuntimePermission(ACCESS_FINE_LOCATION, this);
                break;
            case R.id.microPhone:
                //录音
                requestRuntimePermission(RECORD_AUDIO, this);
                break;
            case R.id.readPhone:
                //读取手机状态
                requestRuntimePermission(READ_PHONE_STATE, this);
                break;
            case R.id.sensors:
                //传感器
                requestRuntimePermission(BODY_SENSORS, this);
                break;
            case R.id.sms:
                //短信
                requestRuntimePermission(SEND_SMS, this);
                break;
            case R.id.storage:
                //文件管理
                requestRuntimePermission(READ_EXTERNAL_STORAGE, this);
                break;
            case R.id.contactsLocation:
                requestContactsLocation();
                break;
        }
    }

    @Override
    public void onGranted() {
        Log.i("wxl", "授权请求通过");
    }

    @Override
    public void onDenied() {
        Log.i("wxl", "授权请求拒绝");
    }
}
