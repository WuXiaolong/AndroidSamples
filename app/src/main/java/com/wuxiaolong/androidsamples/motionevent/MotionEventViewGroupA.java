package com.wuxiaolong.androidsamples.motionevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by WuXiaolong on 2015/12/19.
 */
public class MotionEventViewGroupA extends LinearLayout {
    public MotionEventViewGroupA(Context context) {
        super(context);
    }

    public MotionEventViewGroupA(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MotionEventViewGroupA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("wxl", "MotionEventViewGroupA dispatchTouchEventA");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("wxl", "MotionEventViewGroupA onInterceptTouchEventA");
        return super.onInterceptTouchEvent(ev);
//        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("wxl", "MotionEventViewGroupA onTouchEventA");
        return super.onTouchEvent(event);
    }

}
