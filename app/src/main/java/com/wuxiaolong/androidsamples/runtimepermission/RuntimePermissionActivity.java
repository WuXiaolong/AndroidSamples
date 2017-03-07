package com.wuxiaolong.androidsamples.runtimepermission;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wuxiaolong.androidsamples.BaseActivity;
import com.wuxiaolong.androidsamples.R;
import com.wuxiaolong.androidsamples.retrofit.OnPermissionCallbackListener;
import com.wuxiaolong.androidutils.library.LogUtil;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

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
public class RuntimePermissionActivity extends BaseActivity implements OnPermissionCallbackListener, EasyPermissions.PermissionCallbacks {

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
            case R.id.easyPermissions:
                methodRequiresTwoPermission();
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


    @AfterPermissionGranted(1010)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, SEND_SMS};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
            LogUtil.d("Already have permission, do the thing");
        } else {
            // Do not have permissions, request them now
            LogUtil.d("Do not have permissions, request them now");
            EasyPermissions.requestPermissions(this, "camera_and_send_sms", 1010, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        // ...
        LogUtil.d("Some permissions have been granted=" + requestCode);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
        LogUtil.d("Some permissions have been denied=" + requestCode);
        if (EasyPermissions.somePermissionPermanentlyDenied(this, list)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(this, "returned_from_app_settings_to_activity", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
