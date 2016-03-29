package com.wuxiaolong.androidsamples.motionevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by WuXiaolong on 2015/12/19.
 */
public class MotionEventViewGroupB extends LinearLayout {

    public MotionEventViewGroupB(Context context) {
        super(context);
    }

    public MotionEventViewGroupB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MotionEventViewGroupB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("wxl", "MotionEventViewGroupB dispatchTouchEventB");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("wxl", "MotionEventViewGroupB onInterceptTouchEventB");
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("wxl", "MotionEventViewGroupB onTouchEventB");
//        return super.onTouchEvent(event);
        return true;
    }
}
