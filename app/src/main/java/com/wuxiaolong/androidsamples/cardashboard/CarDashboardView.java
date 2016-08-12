package com.wuxiaolong.androidsamples.cardashboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator
 * on 2016/8/12.
 */
public class CarDashboardView extends View {
    Paint mPaint, mPaintText;

    public CarDashboardView(Context context) {
        super(context);
        init();
    }

    public CarDashboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CarDashboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化对象
     */
    private void init() {
        // 实例化画笔并开启其抗锯齿和抗抖动
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        // 设置画笔颜色为浅灰色
        mPaint.setColor(Color.WHITE);

        //设置画笔样式为描边,画笔样式分三种：
        //1.Paint.Style.STROKE：描边
        //2.Paint.Style.FILL_AND_STROKE：描边并填充
        //3.Paint.Style.FILL：填充
        mPaint.setStyle(Paint.Style.STROKE);
        //设置描边的粗细，单位：像素px
        //注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
        mPaint.setStrokeWidth(10);

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaintText.setColor(Color.WHITE);
        mPaintText.setStrokeWidth(10);
        mPaintText.setTextSize(40);
    }

    float circleX = 500, circleY = 500, circleRadius = 300;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制圆环
        canvas.drawCircle(circleX, circleY, circleRadius, mPaint);
        //drawScale(canvas);
    }

    /**
     * 绘制刻度
     */
    private void drawScale(Canvas canvas) {
        for (int i = 0; i < 13; i++) {
            canvas.drawLine(circleX, circleY + circleRadius, circleX, circleY + circleRadius - 20, mPaint);
            canvas.drawText(String.valueOf(i), circleX, circleY + circleRadius - 50, mPaintText);
            //第一个参数是旋转角度，后两个参数依旧是控制旋转中心点
            canvas.rotate(360 / 13, circleX, circleY);
        }
    }
}
