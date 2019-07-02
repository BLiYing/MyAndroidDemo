package com.example.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.liying.activity.R;
import com.example.utils.DisplayTool;
import com.example.utils.LogUtil;

/**
 * Created by      android studio
 *
 * @author :       ly
 * Date            :       2019-05-23
 * Time            :       下午8:25
 * Version         :       1.0
 * location        :       武汉研发中心
 * 功能描述         :       指示牌
 **/
public class SignView extends View {

    private int defaultHeight;
    private int defaultWidth;
    private Canvas mCanvas;
    private Paint paint;
    private Paint midinePaint;
    Paint paintOfSign;
    Paint textPaint;
    private int midLineWidth = 150;
    MyPoint a;

    float dxOfMove;
    float scale = 1;
    float centerX;
    float centerY;

    float transX = 0;

    public SignView(Context context) {
        this(context, null);
    }

    public SignView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * srceen density屏幕像素值
         */
        scale = context.getResources().getDisplayMetrics().density;
        init();
    }

    private void init() {

        defaultWidth = DisplayTool.getInstance().getDisplayMetrics().widthPixels;
        LogUtil.e("defaultWidth:" + defaultWidth);
        defaultHeight = DisplayTool.getInstance().getDisplayMetrics().heightPixels;
        LogUtil.e("defaultHeight:" + defaultHeight);

        centerX = defaultWidth/2.0f;
        centerY = defaultHeight/2.0f;

        paint = new Paint();
        paint.setColor(Color.BLUE);

        midinePaint = new Paint();
        midinePaint.setStrokeWidth(5);
        midinePaint.setStyle(Paint.Style.STROKE);
        //设置抗锯齿
        midinePaint.setAntiAlias(true);
        midinePaint.setColor(Color.RED);

        a = new MyPoint();
        a.x = defaultWidth / 2;
        a.y = 0;

        transX = defaultWidth/2;
        transX = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(new Rect(0, 0, defaultWidth, defaultHeight), paint);
        canvas.save();
        canvas.translate(defaultWidth/2,0);


        mCanvas = canvas;

        drawLineBitmap(canvas,0,dxOfMove);

        aBitmap = drawABitmap(mCanvas, 40, dxOfMove);
        drawABitmap(mCanvas, aBitmap.getHeight(),180.0f + dxOfMove);
        drawABitmap(mCanvas, aBitmap.getHeight() * 2,dxOfMove);
        drawABitmap(mCanvas, aBitmap.getHeight() * 3,180.0f + dxOfMove);
        LogUtil.e("重新绘制-----------------------");
        mCanvas.restore();
        //图像扭曲+动感  https://blog.csdn.net/danfengw/article/details/48598489
//        canvas.drawBitmapMesh();


    }

    float startx = 0;
    float starty = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                startx = event.getX();
                starty = event.getY();
//                LogUtil.e("-----ACTION_DOWN startx："+startx);

                break;
            case MotionEvent.ACTION_MOVE:
                float movex = event.getX();


                dxOfMove = movex - startx;
//                LogUtil.e("-----movex："+movex);
//                LogUtil.e("-----startx："+startx);
//                LogUtil.e("-----dxOfMove："+dxOfMove);


                postInvalidate();
                break;
            default:
                break;
        }
        return true;
    }


    Bitmap lineBitmap;
    private Bitmap drawLineBitmap(Canvas canvas, int lastHeight,float deg){
        if (lineBitmap == null) {
            lineBitmap = Bitmap.createBitmap(midLineWidth,defaultHeight,Bitmap.Config.RGB_565);
        }

        Matrix matrix = getMatrixs(deg,1);
        matrix.postTranslate(transX-midLineWidth/2,0);
//        canvas.drawRect(new Rect(0,0,lineBitmap.getWidth(),lineBitmap.getHeight()),midinePaint);
        canvas.drawBitmap(lineBitmap,matrix,null);
        canvas.drawLine(transX - midinePaint.getStrokeWidth()/2
                ,0,transX - midinePaint.getStrokeWidth()/2,defaultHeight,midinePaint);
        return lineBitmap;
    }


    /**
     * A area
     *
     * @param canvas
     */
    Bitmap aBitmap;
    private Bitmap drawABitmap(Canvas canvas, int lastHeight,float deg) {
        if (aBitmap == null) {
            aBitmap = scaleBitmap(getBitmap(R.mipmap.timg), 0.2f);
//            aBitmap = getBitmap();

        }
        Matrix matrix = getMatrixs(deg,2);

        matrix.preTranslate(0, -aBitmap.getHeight() / 2);
        matrix.postTranslate(transX+midLineWidth/2, 0);

        matrix.postTranslate(0, aBitmap.getHeight() / 2);

        matrix.postTranslate(0, lastHeight);




        canvas.drawBitmap(aBitmap, matrix, new Paint());
        return aBitmap;
    }



    private Matrix getMatrixs(float deg,int flag) {

        Matrix  matrix = new Matrix();
        Camera camera = new Camera();

//        Matrix   matrix = new Matrix();
        camera.save();
        LogUtil.e("getLocationX:"+camera.getLocationX()+" getLocationY:"+camera.getLocationY()+" getLocationZ:"+camera.getLocationZ());
        camera.rotateY(deg);

        camera.getMatrix(matrix);

        // 修正图片旋转过程中被拉长的失真，主要修改 MPERSP_0 和 MPERSP_1
        float[] mValues = new float[9];
        matrix.getValues(mValues);			    //获取数值
        mValues[6] = mValues[6]/scale;			//数值修正
        mValues[7] = mValues[7]/scale;			//数值修正
        matrix.setValues(mValues);			    //重新赋值
        camera.restore();

        if (flag == 1) {
            // 调节中心点
            matrix.preTranslate(-midLineWidth/2, -midLineWidth/2);
            matrix.postTranslate(midLineWidth/2, midLineWidth/2);
        }
        if (flag == 2 || flag == 3) {
            // 调节中心点
            matrix.preTranslate(midLineWidth/2, midLineWidth/2);
            matrix.postTranslate(-midLineWidth/2, -midLineWidth/2);
        }



        return matrix;
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureSize(defaultHeight, heightMeasureSpec);
        int width = measureSize(defaultWidth, widthMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == View.MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }


    private Bitmap getBitmap(int res) {

        return BitmapFactory.decodeResource(getResources(), res, null);

    }


    private Bitmap getBitmap(){
        if (paintOfSign == null) {
            paintOfSign = new Paint();
            paintOfSign.setColor(Color.WHITE);
            paintOfSign.setStyle(Paint.Style.STROKE);
            paintOfSign.setStrokeWidth(15);
            paintOfSign.setAntiAlias(true);//设置抗锯齿


            textPaint = new Paint();
            textPaint.setColor(Color.WHITE);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setSubpixelText(true);//设置自像素。如果该项为true，将有助于文本在LCD屏幕上的显示效果。
            textPaint.setTextSize(30);
        }

        Bitmap bitmap = Bitmap.createBitmap(defaultWidth/4,defaultWidth/18,Bitmap.Config.RGB_565);
        Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());

        Canvas mCanvas = new Canvas(bitmap);
        //下面开始绘制区域内的内容...
        mCanvas.drawRect(rect,paintOfSign);
        mCanvas.setDrawFilter(new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));

        mCanvas.drawText("深圳世界之窗", 150, bitmap.getHeight()/2, textPaint);

        return bitmap;
    }

    /**
     * 根据给定的宽和高进行拉伸
     *
     * @param origin    原图
     * @param newWidth  新图的宽
     * @param newHeight 新图的高
     * @return new Bitmap
     */
    private Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (!origin.isRecycled()) {
            origin.recycle();
        }
        return newBM;
    }

    /**
     * 按比例缩放图片
     *
     * @param origin 原图
     * @param ratio  比例
     * @return 新的bitmap
     */
    private Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }


}
