package com.wuxiaolong.androidsamples.viewdraghelper;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by WuXiaolong
 * on 2015/12/4.
 */
public class ViewDragLayout extends LinearLayout {
    /**
     * 内容界面
     */
    private ViewGroup mContentLayout;

    /**
     * 遮盖界面
     */
    private ViewGroup mBehindLayout;

    private ViewDragHelper mViewDragHelper;

    /**
     * 手势事件类
     */
    private GestureDetectorCompat mGestureDetectorCompat;

    /**
     * 滑动距离
     */
    private int mViewDragRange;

    /**
     * 左滑最大距离
     */
    private int mBehindLayoutWidth;
    /**
     * 宽度
     */
    private int mContentLayoutWidth;

    private ViewDragListener mViewDragListener;

    private boolean isOpen = false;

    public ViewDragLayout(Context context) {
        super(context);
        init();
    }

    public ViewDragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ViewDragLayout(Context context, AttributeSet attrs,
                          int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * View 中所有的子控件均被映射成xml后触发
     */
    @Override
    protected void onFinishInflate() {
        mContentLayout = (ViewGroup) getChildAt(0);
        mBehindLayout = (ViewGroup) getChildAt(1);
        mBehindLayout.setClickable(true);
        mContentLayout.setClickable(true);
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mContentLayoutWidth = mContentLayout.getMeasuredWidth();
        mBehindLayoutWidth = mBehindLayout.getMeasuredWidth();

    }

    /**
     * 设置监听
     */
    public void setOnViewDragListener(ViewDragListener viewDragListener) {
        this.mViewDragListener = viewDragListener;
    }


    public interface ViewDragListener {
        void onOpen();

        void onClose();

        void onDrag(float percent);

    }


    /**
     * 滑动时松手后以一定速率继续自动滑动下去并逐渐停止，
     * 类似于扔东西或者松手后自动滑动到指定位置
     */
    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * 初始化
     */
    private void init() {
        //创建ViewDragHelper的实例，第一个参数是ViewGroup，传自己，
        // 第二个参数就是滑动灵敏度的意思,可以随意设置，第三个是回调
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
        //手势操作，第二参数什么意思看下面
        mGestureDetectorCompat = new GestureDetectorCompat(getContext(), new XScrollDetector());
    }

    /**
     * 手势监听回调,
     * SimpleOnGestureListener为了不用重写那么多监听的帮助类
     */
    private class XScrollDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            //判断是否是滑动的x距离>y距离
            return Math.abs(distanceY) <= Math.abs(distanceX);
        }

    }

    class DragHelperCallback extends ViewDragHelper.Callback {

        /**
         * 根据返回结果决定当前child是否可以拖拽
         *
         * @param child     当前被拖拽的view
         * @param pointerId 区分多点触摸的id
         * @return
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mContentLayout == child;
        }

        /**
         * 返回拖拽的范围，不对拖拽进行真正的限制，仅仅决定了动画执行速度
         *
         * @param child
         * @return
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return mContentLayoutWidth;
        }

        /**
         * @param child
         * @param left  代表你将要移动到的位置的坐标,返回值就是最终确定的移动的位置,
         *              判断如果这个坐标在layout之内,那我们就返回这个坐标值，
         *              不能让他超出这个范围, 除此之外就是如果你的layout设置了padding的话，
         *              让子view的活动范围在padding之内的
         * @param dx
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //Log.d("wxl", "clampViewPositionHorizontal left=" + left);
            if (child == mContentLayout) {
                int newLeft = Math.min(
                        Math.max((-getPaddingLeft() - mBehindLayoutWidth), left), 0);
                return newLeft;
            } else {
                int newLeft = Math.min(
                        Math.max(left, getPaddingLeft() + mContentLayoutWidth - mBehindLayoutWidth),
                        (getPaddingLeft() + mContentLayoutWidth + getPaddingRight()));
                return newLeft;
            }
        }


        /**
         * 位置改变时回调，常用于滑动是更改scale进行缩放等效果
         *
         * @param changedView
         * @param left
         * @param top
         * @param dx          横向滑动的加速度
         * @param dy
         */
        @Override
        public void onViewPositionChanged(
                View changedView, int left, int top, int dx, int dy) {
            //Log.d("wxl", "onViewPositionChanged left=" + left);
            mViewDragRange = left;
            float percent = Math.abs((float) left / (float) mContentLayoutWidth);
            if (null != mViewDragListener)
                mViewDragListener.onDrag(percent);
            if (changedView == mContentLayout) {
                mBehindLayout.offsetLeftAndRight(dx);
            } else {
                mContentLayout.offsetLeftAndRight(dx);
            }
            invalidate();
        }

        /**
         * 拖动结束后调用
         *
         * @param releasedChild
         * @param xvel          水平方向的速度  向右为正
         * @param yvel          竖直方向的速度  向下为正
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (releasedChild == mContentLayout) {
                if (xvel <= 0) {//向左滑动
                    if (-mViewDragRange >= mBehindLayoutWidth / 2
                            && -mViewDragRange <= mBehindLayoutWidth) {
                        open();
                    } else {
                        close();
                    }
                } else {//向右滑动
                    if (-mViewDragRange >= 0
                            && -mViewDragRange <= mBehindLayoutWidth) {
                        close();
                    } else {
                        open();
                    }
                }
            }
        }
    }

    /**
     * 打开
     */
    public void open() {
        if (mViewDragHelper.smoothSlideViewTo(
                mContentLayout, -mBehindLayoutWidth, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        if (null != mViewDragListener)
            mViewDragListener.onOpen();
        isOpen = true;
    }

    /**
     * 关闭
     */
    public void close() {
        if (mViewDragHelper.smoothSlideViewTo(mContentLayout, 0, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        if (null != mViewDragListener)
            mViewDragListener.onClose();
        isOpen = false;
    }

    public boolean isOpen() {
        return isOpen;
    }


    /**
     * 事件拦截下来,相当于把自定义控件的事件交给ViewDragHelper去处理
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev)
                && mGestureDetectorCompat.onTouchEvent(ev);
    }

    /**
     * 事件监听，交给ViewDragHelper处理
     *
     * @param e
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        try {
            mViewDragHelper.processTouchEvent(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
