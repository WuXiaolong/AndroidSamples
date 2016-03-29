package com.wuxiaolong.androidsamples.motionevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by WuXiaolong on 2015/12/19.
 */
public class MotionEventViewC extends View {
    public MotionEventViewC(Context context) {
        super(context);
    }

    public MotionEventViewC(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MotionEventViewC(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("wxl", "MotionEventViewC dispatchTouchEventC");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("wxl", "MotionEventViewC onTouchEventC");
        return super.onTouchEvent(event);
    }
}
