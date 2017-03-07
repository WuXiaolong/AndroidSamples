package com.wuxiaolong.androidsamples;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.wuxiaolong.androidsamples.runtimepermission.OnPermissionCallbackListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_CONTACTS;
import static android.os.Build.VERSION_CODES.M;

/**
 * Created by WuXiaolong on 2014/11/10.
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public class BaseActivity extends SwipeBackActivity {
    public Context context;
    public static int REQUEST_PERMISSION = 100;
    public static int REQUEST_READ_CONTACTS = 101;
    public static int REQUEST_READ_CONTACTS_LOCATION = 102;
    public OnPermissionCallbackListener onPermissionCallbackListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }

    public Toolbar initToolbarAsHome(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);

        }
        return toolbar;
    }

    public Toolbar initToolbar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 单独请求通讯录权限
     */
    @TargetApi(M)
    public void requestContacts() {
        switch (checkSelfPermission(READ_CONTACTS)) {
            case PackageManager.PERMISSION_GRANTED:
                // 已有授权
                Log.i("wxl", "已有授权");
                break;
            case PackageManager.PERMISSION_DENIED:
                // 1、没有权限：尚未请求过权限；
                // 2、或者请求授权被拒绝，用shouldShowRequestPermissionRationale判断用户是否拒绝过，如果返回true，表示用户拒绝过，
                // 再次请求权限，将会出现“不再询问”，勾上“不再询问”，只能选择拒绝，再次进入，shouldShowRequestPermissionRationale始终false
                // 3、或者曾经授权过，但用户在设置中禁用权限
                Log.i("wxl", "是否拒绝过=" + shouldShowRequestPermissionRationale(READ_CONTACTS));
                requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                break;
            default:
                break;
        }

    }

    /**
     * 同时请求通讯录定位权限
     */
    @TargetApi(M)
    public void requestContactsLocation() {
        List<String> permissionsList = new ArrayList<>();
        permissionsList.add(READ_CONTACTS);
        permissionsList.add(ACCESS_FINE_LOCATION);
        requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_READ_CONTACTS_LOCATION);

    }

    @TargetApi(M)
    public void requestRuntimePermission(String permission, OnPermissionCallbackListener onPermissionCallbackListener) {
        this.onPermissionCallbackListener = onPermissionCallbackListener;
        switch (checkSelfPermission(permission)) {
            case PackageManager.PERMISSION_GRANTED:
                // 已有授权
                Log.i("wxl", "已有授权");
                if (this.onPermissionCallbackListener != null)
                    onPermissionCallbackListener.onGranted();
                break;
            case PackageManager.PERMISSION_DENIED:
                // 1、没有权限：尚未请求过权限；
                // 2、或者请求授权被拒绝，用shouldShowRequestPermissionRationale判断用户是否拒绝过，如果返回true，表示用户拒绝过，
                // 再次请求权限，将会出现“不再询问”，勾上“不再询问”，只能选择拒绝，再次进入，shouldShowRequestPermissionRationale始终false
                // 3、或者曾经授权过，但用户在设置中禁用权限
                Log.i("wxl", "是否拒绝过=" + shouldShowRequestPermissionRationale(permission));
                requestPermissions(new String[]{permission}, REQUEST_PERMISSION);
                break;
            default:
                break;
        }
    }


    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS_LOCATION) {
            Map<String, Integer> perms = new HashMap<>();
            // Initial
            perms.put(ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
            perms.put(READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);
            if (perms.get(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && perms.get(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                Log.i("wxl", "授权请求被通过");
            } else {
                // Permission Denied
                Log.i("wxl", "授权请求不被通过");
            }
        }
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 授权请求被通过，读取通讯录
                Log.i("wxl", "onRequestPermissionsResult=授权请求被通过，读取通讯录");
            } else {
                Log.i("wxl", "onRequestPermissionsResult=授权请求不被通过");
            }
        }
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 授权请求被通过，读取通讯录
                Log.i("wxl", "onRequestPermissionsResult=授权请求被通过");
                if (onPermissionCallbackListener != null)
                    onPermissionCallbackListener.onGranted();
            } else {
                Log.i("wxl", "onRequestPermissionsResult=授权请求不被通过");
                if (onPermissionCallbackListener != null)
                    onPermissionCallbackListener.onDenied();
            }
        }
    }


}
