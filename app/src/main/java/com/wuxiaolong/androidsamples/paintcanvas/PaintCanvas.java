package com.wuxiaolong.androidsamples.paintcanvas;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2015/12/6 0006.
 */
public class PaintCanvas extends View {
    private Bitmap mSrcBitmap, mRefBitmap;
    private Paint mPaint;
    private PorterDuffXfermode mXfermode;

    public PaintCanvas(Context context) {
        super(context);
        initXfermodeShader();
    }

    public PaintCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        initXfermodeShader();
    }

    public PaintCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initXfermodeShader();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PaintCanvas(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initXfermodeShader();
    }

    Bitmap mBitmap;

    private void initXfermodeShader() {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);
        canvas.drawCircle(200, 200, 100, paint);
        canvas.drawRect(10, 150, 70, 190, paint);
    }

}
