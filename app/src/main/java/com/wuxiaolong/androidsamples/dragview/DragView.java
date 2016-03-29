package com.wuxiaolong.androidsamples.dragview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by WuXiaolong on 2015/11/20.
 */
public class DragView extends View {
    private int x, y;
    private Scroller mScroller;

    public DragView(Context context) {
        super(context);
        initScroller(context);
    }

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initScroller(context);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initScroller(context);
    }

    private void initScroller(Context context) {
        setBackgroundColor(Color.RED);
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int getRawX = (int) event.getRawX();
        int getRawY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录触摸点坐标
                x = (int) event.getRawX();
                y = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                //计算偏移量
                int offsetX = getRawX - x;
                int offsetY = getRawY - y;
                ((View) getParent()).scrollBy(-offsetX, -offsetY);
                //重新设置初始坐标
                x = getRawX;
                y = getRawY;
                break;
            case MotionEvent.ACTION_UP:
                /**
                 * 实现拖动回弹回去，需配合方法一、二、四中任一方法
                 */
                View viewGroup = (View) getParent();
                mScroller.startScroll(viewGroup.getScrollX(),
                        viewGroup.getScrollY(),
                        -viewGroup.getScrollX(),
                        -viewGroup.getScrollY()
                );
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //判断Scroller是否执行完毕
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(
                    mScroller.getCurrX(),
                    mScroller.getCurrY());
            invalidate();
        }
    }
}
