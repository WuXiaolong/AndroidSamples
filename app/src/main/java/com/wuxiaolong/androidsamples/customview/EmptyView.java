package com.wuxiaolong.androidsamples.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.wuxiaolong.androidsamples.R;
import com.wuxiaolong.androidutils.library.LogUtil;


/**
 * Created by WuXiaolong
 * on 2017/2/10.
 */
public class EmptyView extends View {
    private int mWidth = 500, mHeight = 450;
    private Paint mPaint;
    private String emptyText;
    private Drawable emptyImage;
    private Bitmap bitmap;
    private Rect textRect;
    private Context context;

    public EmptyView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmptyView);
        emptyImage = typedArray.getDrawable(R.styleable.EmptyView_empty_image);
        emptyText = typedArray.getString(R.styleable.EmptyView_empty_text);
        typedArray.recycle();

        initView();
    }

    private void initView() {

        BitmapDrawable bitmapDrawable = (BitmapDrawable) emptyImage;
        bitmap = bitmapDrawable.getBitmap();

        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setTextSize(40f);
        mPaint.setAntiAlias(true);
        textRect = new Rect();
        mPaint.getTextBounds(emptyText, 0, emptyText.length(), textRect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int finalWidth = mWidth > widthSpecSize ? mWidth : widthSpecSize;
        int finalheight = mHeight > heightSpecSize ? mHeight : heightSpecSize;
        //view支持wrap_content
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(finalWidth, finalheight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(finalWidth, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, finalheight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //支持padding，不然padding属性无效
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        LogUtil.d("paddingLeft=" + paddingLeft + ",paddingTop=" + paddingTop + ",paddingRight=" + paddingRight + ",paddingBottom=" + paddingBottom);
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        canvas.drawText(emptyText, (float) ((width - textRect.width()) * 0.5), (float) (height * 0.5), mPaint);
        canvas.drawBitmap(bitmap, (float) ((width - bitmap.getWidth()) * 0.5), (float) (height * 0.5 - bitmap.getHeight() - 100), mPaint);
    }

    public void setEmptyImage(int id) {
        emptyImage = ContextCompat.getDrawable(context, id);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) emptyImage;
        bitmap = bitmapDrawable.getBitmap();
        //requestLayout();
        invalidate();
    }

    public void setEmptyText(String text) {
        emptyText = text;
        mPaint.getTextBounds(emptyText, 0, emptyText.length(), textRect);
        invalidate();
    }
}
