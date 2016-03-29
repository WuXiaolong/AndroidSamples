package com.wuxiaolong.androidsamples.fontawesome;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by WuXiaolong on 2015/10/8.
 */
public class FontManager {
    public static final String ROOT = "",
            FONTAWESOME = ROOT + "fontawesome-webfont.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    /**
     * 图标一般都是包含在一个ViewGroup，
     * 比如一个RelativeLayout或者LinearLayout中。
     * 我们可以写一个方法，爬遍指定xml parent 并且递归的覆盖每个TextView的字体。
     *
     */
    public static void markAsIconContainer(View v, Typeface typeface) {
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                markAsIconContainer(child, typeface);
            }
        } else if (v instanceof TextView) {
            ((TextView) v).setTypeface(typeface);
        }
    }


}
