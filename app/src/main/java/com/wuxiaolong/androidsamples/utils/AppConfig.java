package com.wuxiaolong.androidsamples.utils;

import android.os.Environment;

import com.wuxiaolong.androidsamples.downloadnotify.DownloadActivity;
import com.wuxiaolong.androidsamples.photograph.PhotographActivity;


/**
 * Created by wxl on 2014/11/4.
 */
public class AppConfig {

    public static int PHOTOGRAPH = 1;
    public static int PHOTO_CUTTING = 2;
    public static int REQUEST_CODE_USER_ALBUM = 3;// 验证码登录
    public static int BTN_PHOTOGRAPH = 4;// 验证码登录
    public static String SAVED_IMAGE_DIR_PATH = Environment
            .getExternalStorageDirectory().getPath() + "/AndroidSamples/camera/";// 拍照路径
}
