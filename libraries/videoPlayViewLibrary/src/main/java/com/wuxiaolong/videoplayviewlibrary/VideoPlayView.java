package com.wuxiaolong.videoplayviewlibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.VideoView;

/**
 * 自动全屏的VideoView
 */
public class VideoPlayView extends VideoView {

    private int videoWidth;
    private int videoHeight;
    // 屏幕宽高
    private float screenWidth;

    public VideoPlayView(Context context) {
        super(context);
        init(context);
    }

    public VideoPlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VideoPlayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context) {
        screenWidth = DensityUtil.getWidthInPx(context);
        threshold = DensityUtil.dip2px(context, 18);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(videoWidth, widthMeasureSpec);
        int height = getDefaultSize(videoHeight, heightMeasureSpec);
        if (videoWidth > 0 && videoHeight > 0) {
            if (videoWidth * height > width * videoHeight) {
                height = width * videoHeight / videoWidth;
            } else if (videoWidth * height < width * videoHeight) {
                width = height * videoWidth / videoHeight;
            }
        }
        setMeasuredDimension(width, height);
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    private float mLastMotionX;
    private float mLastMotionY;
    private int startX;
    private int startY;
    private int threshold;
    private boolean isClick = true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("wxl", "onTouchEvent");
        final float x = event.getX();
        final float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                mLastMotionY = y;
                startX = (int) x;
                startY = (int) y;
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = x - mLastMotionX;
                float deltaY = y - mLastMotionY;
                float absDeltaX = Math.abs(deltaX);
                float absDeltaY = Math.abs(deltaY);
                /**
                 * 竖向滑动,声音或亮度调节标识
                 */
                boolean isAdjustAudio;
                if (absDeltaX > threshold && absDeltaY > threshold) {
                    if (absDeltaX < absDeltaY) {
                        //竖向滑动，属于声音或亮度调节范畴
                        isAdjustAudio = true;
                    } else {
                        //横向滑动，属于视频进度调节范畴
                        isAdjustAudio = false;
                    }
                } else if (absDeltaX < threshold && absDeltaY > threshold) {
                    //竖向滑动，属于声音或亮度调节范畴
                    isAdjustAudio = true;
                } else if (absDeltaX > threshold && absDeltaY < threshold) {
                    //只能横向滑动，属于视频进度调节范畴
                    isAdjustAudio = false;
                } else {
                    return true;
                }
                if (isAdjustAudio) {//竖向滑动
                    if (x < screenWidth / 2) {//屏幕一半，左边区域
                        if (deltaY > 0) {
                            mOnVideoViewStateChangeListener.lightDown(absDeltaY);
                        } else if (deltaY < 0) {
                            mOnVideoViewStateChangeListener.lightUp(absDeltaY);
                        }
                    } else {//屏幕一半，右边区域
                        if (deltaY > 0) {
                            mOnVideoViewStateChangeListener.volumeDown(absDeltaY);
                        } else if (deltaY < 0) {
                            mOnVideoViewStateChangeListener.volumeUp(absDeltaY);
                        }
                    }

                } else {//横向滑动
                    if (deltaX > 0) {
                        mOnVideoViewStateChangeListener.forward(absDeltaX);
                    } else if (deltaX < 0) {
                        mOnVideoViewStateChangeListener.backward(absDeltaX);
                    }
                }
                mLastMotionX = x;
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(x - startX) > threshold
                        || Math.abs(y - startY) > threshold) {
                    isClick = false;
                }
                mLastMotionX = 0;
                mLastMotionY = 0;
                startX = 0;
                if (isClick) {
                    mOnVideoViewStateChangeListener.showOrHide();
                }
                isClick = true;
                break;

            default:
                break;
        }
        return true;
    }

    public onVideoViewStateChangeListener mOnVideoViewStateChangeListener;

    public void setOnVideoViewStateChangeListener(onVideoViewStateChangeListener onVideoViewStateChangeListener) {
        this.mOnVideoViewStateChangeListener = onVideoViewStateChangeListener;
    }

    public interface onVideoViewStateChangeListener {
        void lightDown(float absDeltaY);

        void lightUp(float absDeltaY);

        void volumeDown(float absDeltaY);

        void volumeUp(float absDeltaY);

        void forward(float absDeltaX);

        void backward(float absDeltaX);

        void showOrHide();
    }
}
