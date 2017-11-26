package com.wuxiaolong.androidsamples.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestListener;
import com.wuxiaolong.androidsamples.GlideApp;
import com.wuxiaolong.androidsamples.R;


/**
 * Created by wuxiaolong on 2017/6/21.
 * 个人博客：http：//wuxiaolong.me
 */

public class ImageLoaderUtil {
    public static void load(Context context, Object object, ImageView imageView) {
        load(context, object, R.drawable.place_holder, imageView);
    }


    public static void load(Context context, Object object, int defaultImage, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(object)
                .placeholder(defaultImage)
                .error(defaultImage)
                .fallback(defaultImage)
                .into(imageView);
    }


    public static void load(Context context, String url, int defaultImage,
                            ImageView imageView, RequestListener requestListener) {
        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .placeholder(defaultImage)
                .error(defaultImage)
                .fallback(defaultImage)
                .listener(requestListener)
                .into(imageView);
    }
}
