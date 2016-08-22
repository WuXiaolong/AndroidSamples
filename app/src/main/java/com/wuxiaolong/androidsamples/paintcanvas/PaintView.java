package com.wuxiaolong.androidsamples.paintcanvas;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SumPathEffect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.wuxiaolong.androidsamples.R;

/**
 * Created by Administrator
 * on 2015/12/6 0006.
 */
public class PaintView extends View {
    private Paint mPaint;
    // 图形混合模式
    private PorterDuffXfermode porterDuffXfermode;
    private Context mContext;
    private Bitmap mBitmap;
    private RectF mRectF;
    private Shader mShader, bitmapShader, linearGradient, composeShader;
    // 路径对象
    private Path mPath;
    private PathEffect[] pathEffects = new PathEffect[7];
    private float mPhase = 5;

    public PaintView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PaintView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        init();
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.logo);

        //mShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        //mShader = new LinearGradient(0, 0, 500, 500, Color.BLUE, Color.GREEN, Shader.TileMode.CLAMP);

        //mShader = new RadialGradient(500, 500, 400, Color.BLUE, Color.GREEN, Shader.TileMode.CLAMP);

        //mShader = new SweepGradient(500, 500, Color.BLUE, Color.GREEN);

        //bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        //linearGradient = new LinearGradient(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), Color.BLUE, Color.GREEN, Shader.TileMode.CLAMP);
        //bitmapShader对应下层图形，linearGradient对应上层图形，像素颜色混合采用MULTIPLY模式
        //composeShader = new ComposeShader(bitmapShader, linearGradient, PorterDuff.Mode.MULTIPLY);
        //mRectF = new RectF(0, 0, 600, 400);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(60);
        //mPaint.setColor(Color.WHITE);
        //mPaint.setShadowLayer(10, 100, 100, Color.BLUE);
        //mPaint.setStrikeThruText(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeMiter(20f);
        mPaint.setSubpixelText(true);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextScaleX(1.1f);
        mPaint.setTextSkewX(-1f);
        mPaint.setFilterBitmap(true);
        mPaint.setHinting(Paint.HINTING_ON);
        //mPaint.setLinearText(false);
        // 设置画笔模糊阴影效果,参数1：模糊延伸半径，必须>0；
        // 参数2：有四种枚举
        // NORMAL，同时绘制图形本身内容+内阴影+外阴影,正常阴影效果
        // INNER，绘制图形内容本身+内阴影，不绘制外阴影
        // OUTER，不绘制图形内容以及内阴影，只绘制外阴影
        // SOLID，只绘制外阴影和图形内容本身，不绘制内阴影
        // BlurMaskFilter绘制的Bitmap基本完全不受影响
        //mPaint.setMaskFilter(new BlurMaskFilter(20f, BlurMaskFilter.Blur.SOLID));

        //Paint的setMaskFilter不被GPU支持,为了确保画笔的setMaskFilter能供起效，我们需要禁用掉GPU硬件加速
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        //    //View从API Level 11才加入setLayerType方法
        //    //设置myView以软件渲染模式绘图
        //    this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //}
        //设置浮雕滤镜效果，参数1：光源指定方向；参数2:环境光亮度，取值0-1,值越小越暗；参数3：镜面高光反射系数，值越小反射越强；参数4：模糊延伸半径
        //mPaint.setMaskFilter(new EmbossMaskFilter(new float[]{1, 1, 1}, 0.4f, 8f, 3f));
        //initPath();
        // 生成色彩矩阵
        //ColorMatrix colorMatrix = new ColorMatrix(new float[]{
        //        0.5F, 0, 0, 0, 0,
        //        0, 0.5F, 0, 0, 0,
        //        0, 0, 0.5F, 0, 0,
        //        0, 0, 0, 1, 0,
        //});
        //mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        // 设置颜色过滤,去掉绿色
        //mPaint.setColorFilter(new LightingColorFilter(0xFFFF00FF, 0x00000000));
        // 设置颜色过滤,Color的值设为红色，模式PorterDuff.Mode.DARKEN变暗
        //mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DARKEN));
        // 实例化混合模式
        //porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC);

    }

    private void initPath() {
        // 实例化路径
        mPath = new Path();
        // 定义路径的起点
        mPath.moveTo(10, 50);

        // 定义路径的各个点
        for (int i = 0; i <= 30; i++) {
            mPath.lineTo(i * 35, (float) (Math.random() * 100));
        }
        //什么都不处理
        pathEffects[0] = null;
        //参数1：线段之间的圆滑程度
        pathEffects[1] = new CornerPathEffect(10);
        //参数1：间隔线条长度(length>=2)，如float[] {20, 10}的偶数参数20定义了我们第一条实线的长度，
        //而奇数参数10则表示第一条虚线的长度，后面不再有数据则重复第一个数以此往复循环；参数2：虚实线间距
        pathEffects[2] = new DashPathEffect(new float[]{20, 10}, mPhase);
        //参数1:值越小杂点越密集；参数2:杂点突出的大小，值越大突出的距离越大
        pathEffects[3] = new DiscretePathEffect(5.0f, 10.0f);
        Path path = new Path();
        path.addRect(0, 0, 8, 8, Path.Direction.CCW);
        //参数1：path；参数2：实线的长度；参数3：虚实线间距
        pathEffects[4] = new PathDashPathEffect(path, 20, mPhase, PathDashPathEffect.Style.ROTATE);
        pathEffects[5] = new ComposePathEffect(pathEffects[2], pathEffects[4]);
        //ComposePathEffect和SumPathEffect都可以用来组合两种路径效果,具体区别（不知道如何描述）小伙伴们自己试试
        pathEffects[6] = new SumPathEffect(pathEffects[4], pathEffects[3]);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //int canvasWidth = canvas.getWidth();
        //int canvasHeight = canvas.getHeight();
        //新建一个layer,放置在canvas默认layer的上部，产生的layer初始时是完全透明的
        //int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);
        //dst是先画的图形
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        //设置混合模式
        //mPaint.setXfermode(porterDuffXfermode);
        //src是后画的图形
        //mPaint.setColor(0xFFFFCC44);
        //canvas.drawCircle(600, 600, 200, mPaint);
        //还原混合模式
        //mPaint.setXfermode(null);
        //或canvas.restore()把这个layer绘制到canvas默认的layer上去
        //canvas.restoreToCount(layerId);

        //mPaint.setShader(composeShader);
        //canvas.drawCircle(500, 550, 400, mPaint);

         /*
         * 绘制路径
         */
        //for (int i = 0; i < pathEffects.length; i++) {
        //    mPaint.setPathEffect(pathEffects[i]);
        //    canvas.drawPath(mPath, mPaint);
        //
        //    // 每绘制一条将画布向下平移250个像素
        //    canvas.translate(0, 250);
        //}

        canvas.drawText("微信公众号：吴小龙同学", 20, 500, mPaint);

        //canvas.drawRect(100, 100, 500, 500, mPaint);
    }

}
