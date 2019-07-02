package com.example.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.example.utils.DisplayTool;
import com.example.utils.LogUtil;

/**
 * Description:
 * Created by administrator
 * on 2017-5-25.
 * 来自：https://blog.csdn.net/iispring/article/details/50472485
 */

public class ViewPorterDuffXfermode extends AppCompatTextView {
    private Paint mPaint;

    private int defaultWidth;//默认宽度
    private int defaultHeight;//默认高度
    public ViewPorterDuffXfermode(Context context) {
        super(context);
    }

    public ViewPorterDuffXfermode(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPorterDuffXfermode(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        defaultWidth = DisplayTool.getInstance().getDisplayMetrics().widthPixels;
        LogUtil.e("defaultWidth:"+defaultWidth);
        defaultHeight = DisplayTool.getInstance().getDisplayMetrics().heightPixels;
        LogUtil.e("defaultHeight:"+defaultHeight);

        // 矩形区域
        /*RectF rect = new RectF(-400,-400,400,400);
        canvas.translate(defaultWidth / 2, defaultHeight/2);

        for (int i = 0; i <= 20; i++)
        {
            canvas.scale(0.9f,0.9f);
            canvas.drawRect(rect, mPaint);
        }*/




        // 将坐标系原点移动到画布正中心
        canvas.translate(defaultWidth / 2, defaultHeight / 2);

        //设置背景色
        canvas.drawARGB(255, 139, 197, 186);

        int canvasWidth = canvas.getWidth();
        int r = canvasWidth / 3;
        //正常绘制黄色的圆形
        mPaint.setColor(Color.YELLOW);
        canvas.drawCircle(0, 0, r, mPaint);

        int rMin = r*9/10;
        LogUtil.e("小圆半径："+rMin);
        mPaint.setColor(Color.BLUE);
//        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0,0,rMin, mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(2);

        for(int i = 0;i < 360;i+=30){
            canvas.drawLine(0,rMin,0,r, mPaint);
            canvas.rotate(30);

        }





        //使用CLEAR作为PorterDuffXfermode绘制蓝色的矩形
       /* mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(r, r, r * 2.7f, r * 2.7f, mPaint);
        //最后将画笔去除Xfermode
        mPaint.setXfermode(null);*/

        //设置背景色
       /* canvas.drawARGB(255, 139, 197, 186);

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);
        int r = canvasWidth / 3;
        //正常绘制黄色的圆形
        mPaint.setColor(0xFFFFCC44);
        canvas.drawCircle(r, r, r, mPaint);
        //使用CLEAR作为PorterDuffXfermode绘制蓝色的矩形
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mPaint.setColor(0xFF66AAFF);
        canvas.drawRect(r, r, r * 2.7f, r * 2.7f, mPaint);
        //最后将画笔去除Xfermode
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);*/
    }
}
