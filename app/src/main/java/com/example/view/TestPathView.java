package com.example.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.liying.activity.R;
import com.example.utils.DisplayTool;

/**
 * Description:自定义view,了解path的基本用法
 * Created by administrator
 * on 2017-10-11.
 */

public class TestPathView extends View{


    // 画笔
    private Paint mDeafultPaint;

    // View 宽高
    private int mViewWidth;
    private int mViewHeight;

    private float currentValue = 0;     // 用于纪录当前的位置,取值范围[0,1]映射Path的整个长度

    private float[] pos;                // 当前点的实际位置
    private float[] tan;                // 当前点的tangent值,用于计算图片所需旋转的角度
    private Bitmap mBitmap;             // 箭头图片
    private Matrix mMatrix;             // 矩阵,用于对图片进行一些操作
    private int BWScreenWidth;
    private int BWScreenHeight;

    private void init(Context context) {
        pos = new float[2];
        tan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;       // 缩放图片
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.arrow, options);
        mMatrix = new Matrix();
    }
    public TestPathView(@NonNull Context context) {
        this(context,null);

    }

    public TestPathView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }

    public TestPathView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        mDeafultPaint = new Paint();
        mDeafultPaint.setStyle(Paint.Style.STROKE);                   // 设置画布模式为填充
        mDeafultPaint.setStrokeWidth(5);
        mDeafultPaint.setColor(Color.BLACK);
        mViewWidth = DisplayTool.getInstance().getDisplayMetrics().widthPixels;
        mViewHeight = DisplayTool.getInstance().getDisplayMetrics().heightPixels;
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
//        canvas.translate(mViewWidth / 2, mViewHeight / 2);          // 平移坐标系
//
//        Path path = new Path();                                     // 创建Path并添加了一个矩形
//        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
//
//        Path dst = new Path();                                      // 创建用于存储截取后内容的 Path
//        dst.lineTo(-300, -300);                                     // 在 dst 中添加一条线段
//
//        PathMeasure measure = new PathMeasure(path, false);         // 将 Path 与 PathMeasure 关联
//
//        measure.getSegment(200, 600, dst, true);                   // <--- 截取一部分 不使用 startMoveTo, 保持 dst 的连续性
//
//        canvas.drawPath(dst, mDeafultPaint);                        // 绘制 Path

        canvas.translate(0, mViewHeight / 2);      // 平移坐标系

//        Path path = new Path();                                 // 创建 Path
//
//        path.addCircle(0, 0, 200, Path.Direction.CW);           // 添加一个圆形
//
//        PathMeasure measure = new PathMeasure(path, false);     // 创建 PathMeasure
//
//        currentValue += 0.005;                                  // 计算当前的位置在总长度上的比例[0,1]
//        if (currentValue >= 1) {
//            currentValue = 0;
//        }
//
//        measure.getPosTan(measure.getLength() * currentValue, pos, tan);        // 获取当前位置的坐标以及趋势
//
//        mMatrix.reset();                                                        // 重置Matrix
//        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // 计算图片旋转角度
//
//        mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);   // 旋转图片
//        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合
//
//        canvas.drawPath(path, mDeafultPaint);                                   // 绘制 Path
//        canvas.drawBitmap(mBitmap, mMatrix, mDeafultPaint);                     // 绘制箭头
//
//        invalidate();                                                           // 重绘页面


        Path path = new Path();                                 // 创建 Path

        path.addCircle(0, 0, 200, Path.Direction.CW);           // 添加一个圆形

        PathMeasure measure = new PathMeasure(path, false);     // 创建 PathMeasure

        currentValue += 0.005;                                  // 计算当前的位置在总长度上的比例[0,1]
        if (currentValue >= 1) {
            currentValue = 0;
        }

// 获取当前位置的坐标以及趋势的矩阵
        measure.getMatrix(measure.getLength() * currentValue, mMatrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);

        mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);   // <-- 将图片绘制中心调整到与当前点重合(注意:此处是前乘pre)

        canvas.drawPath(path, mDeafultPaint);                                   // 绘制 Path
        canvas.drawBitmap(mBitmap, mMatrix, mDeafultPaint);                     // 绘制箭头

        invalidate();                                                           // 重绘页面

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(BWScreenWidth, BWScreenHeight);
    }

    public void setWidthHeight(int width, int height) {
        this.BWScreenHeight = height;
        this.BWScreenWidth = width;
    }
}
