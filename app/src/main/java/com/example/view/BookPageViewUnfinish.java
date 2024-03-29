package com.example.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.utils.LogUtil;

/**
 * Created by      android studio
 *
 * @author :       ly
 * Date            :       2019-05-22
 * Time            :       上午10:09
 * Version         :       1.0
 * location        :       武汉研发中心
 * 功能描述         :       本篇来自简书 https://www.jianshu.com/p/d02362fbd9f2
 *
 *                     PorterDuffXfermode用法 参考一：https://blog.csdn.net/iispring/article/details/50472485
 *                     参考二： https://www.jianshu.com/p/78c36742d50f
 *                     三：https://www.jianshu.com/p/d11892bbe055
 **/
public class BookPageViewUnfinish extends View {
    public static final String STYLE_TOP_RIGHT = "STYLE_TOP_RIGHT";//f点在右上角
    public static final String STYLE_LOWER_RIGHT = "STYLE_LOWER_RIGHT";//f点在右下角

    private Paint pointPaint;//绘制各标识点的画笔
    private Paint bgPaint;//背景画笔

    private MyPoint a,f,g,e,h,c,j,b,k,d,i;

    private int defaultWidth;//默认宽度
    private int defaultHeight;//默认高度
    private int viewWidth;
    private int viewHeight;


    private Paint pathAPaint;//绘制A区域画笔
    private Path pathA;
    private Bitmap bitmap;//缓存bitmap
    private Canvas bitmapCanvas;


    private Paint pathCPaint;//绘制C区域画笔
    private Path pathC;

    private Paint pathBPaint;//绘制B区域画笔
    private Path pathB;

    public BookPageViewUnfinish(Context context) {
        this(context,null);
    }

    public BookPageViewUnfinish(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BookPageViewUnfinish(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);

    }

    private void init(Context context, @Nullable AttributeSet attrs){
        defaultWidth = 600;
        defaultHeight = 1000;

//        defaultWidth = DisplayTool.getInstance().getDisplayMetrics().widthPixels;
        LogUtil.e("defaultWidth:"+defaultWidth);
//        defaultHeight = DisplayTool.getInstance().getDisplayMetrics().heightPixels;
        LogUtil.e("defaultHeight:"+defaultHeight);

//        viewWidth = defaultWidth;
//        viewHeight = defaultHeight;

//        a = new MyPoint(defaultWidth/3f,defaultHeight/3f);
        a = new MyPoint(200,800);
        f = new MyPoint();
        g = new MyPoint();
        e = new MyPoint();
        h = new MyPoint();
        c = new MyPoint();
        j = new MyPoint();
        b = new MyPoint();
        k = new MyPoint();
        d = new MyPoint();
        i = new MyPoint();
//        calcPointsXY(a,f);

        pointPaint = new Paint();
        pointPaint.setColor(Color.RED);
        pointPaint.setTextSize(25);

        bgPaint = new Paint();
        bgPaint.setColor(Color.WHITE);


        //area A
        pathAPaint = new Paint();
        pathAPaint.setColor(Color.GREEN);
        pathAPaint.setAntiAlias(true);//设置抗锯齿
        pathA = new Path();

        //area C
        pathCPaint = new Paint();
        pathCPaint.setColor(Color.YELLOW);
        pathCPaint.setAntiAlias(true);//设置抗锯齿
        //观察分析后可以得出结论，区域C是 由直线ab,bd,dj,ik,ak连接而成的区域 减去 与区域A交集部分 后剩余的区域。于是我们设置区域C画笔Xfermode模式为DST_ATOP
        pathCPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        pathC = new Path();

        pathBPaint = new Paint();
        pathBPaint.setColor(Color.BLUE);
        pathBPaint.setAntiAlias(true);//设置抗锯齿
        pathBPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        pathB = new Path();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureSize(defaultHeight, heightMeasureSpec);
        int width = measureSize(defaultWidth, widthMeasureSpec);
        setMeasuredDimension(width, height);

        viewWidth = width;
        viewHeight = height;
        f.x = width;
        f.y = height;
        calcPointsXY(a,f);//将初始化计算放在这
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //为了看清楚点与View的位置关系绘制一个背景
        canvas.drawRect(0,0,viewWidth,viewHeight,bgPaint);


        bitmap = Bitmap.createBitmap((int) viewWidth, (int) viewHeight, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);
        //------------------------------A area
        bitmapCanvas.drawPath(getPathAFromLowerRight(),pathAPaint);
//        canvas.drawBitmap(bitmap,0,0,null);

        //-----------------------------C area
        bitmapCanvas.drawPath(getPathC(),pathCPaint);
        //-----------------------------B area
        bitmapCanvas.drawPath(getPathB(),pathBPaint);

        canvas.drawBitmap(bitmap,0,0,null);





        //-----------------------------绘制各标识点
        canvas.drawText("a",a.x,a.y,pointPaint);
        canvas.drawText("f",f.x,f.y,pointPaint);
        canvas.drawText("g",g.x,g.y,pointPaint);

        canvas.drawText("e",e.x,e.y,pointPaint);
        canvas.drawText("h",h.x,h.y,pointPaint);

        canvas.drawText("c",c.x,c.y,pointPaint);
        canvas.drawText("j",j.x,j.y,pointPaint);

        canvas.drawText("b",b.x,b.y,pointPaint);
        canvas.drawText("k",k.x,k.y,pointPaint);

        canvas.drawText("d",d.x,d.y,pointPaint);
        canvas.drawText("i",i.x,i.y,pointPaint);



    }

    /**
     * 计算各点坐标
     * @param a
     * @param f
     */
    private void calcPointsXY(MyPoint a, MyPoint f){
        g.x = (a.x + f.x) / 2;
        g.y = (a.y + f.y) / 2;

        e.x = g.x - (f.y - g.y) * (f.y - g.y) / (f.x - g.x);
        e.y = f.y;

        h.x = f.x;
        h.y = g.y - (f.x - g.x) * (f.x - g.x) / (f.y - g.y);

        c.x = e.x - (f.x - e.x) / 2;
        c.y = f.y;

        j.x = f.x;
        j.y = h.y - (f.y - h.y) / 2;

        b = getIntersectionPoint(a,e,c,j);
        k = getIntersectionPoint(a,h,c,j);

        d.x = (c.x + 2 * e.x + b.x) / 4;
        d.y = (2 * e.y + c.y + b.y) / 4;

        i.x = (j.x + 2 * h.x + k.x) / 4;
        i.y = (2 * h.y + j.y + k.y) / 4;
    }

    /**
     * 计算两线段相交点坐标
     * @param lineOne_My_pointOne
     * @param lineOne_My_pointTwo
     * @param lineTwo_My_pointOne
     * @param lineTwo_My_pointTwo
     * @return 返回该点
     */
    private MyPoint getIntersectionPoint(MyPoint lineOne_My_pointOne, MyPoint lineOne_My_pointTwo, MyPoint lineTwo_My_pointOne, MyPoint lineTwo_My_pointTwo){
        float x1,y1,x2,y2,x3,y3,x4,y4;
        x1 = lineOne_My_pointOne.x;
        y1 = lineOne_My_pointOne.y;
        x2 = lineOne_My_pointTwo.x;
        y2 = lineOne_My_pointTwo.y;
        x3 = lineTwo_My_pointOne.x;
        y3 = lineTwo_My_pointOne.y;
        x4 = lineTwo_My_pointTwo.x;
        y4 = lineTwo_My_pointTwo.y;

        float pointX =((x1 - x2) * (x3 * y4 - x4 * y3) - (x3 - x4) * (x1 * y2 - x2 * y1))
                / ((x3 - x4) * (y1 - y2) - (x1 - x2) * (y3 - y4));
        float pointY =((y1 - y2) * (x3 * y4 - x4 * y3) - (x1 * y2 - x2 * y1) * (y3 - y4))
                / ((y1 - y2) * (x3 - x4) - (x1 - x2) * (y3 - y4));

        return  new MyPoint(pointX,pointY);
    }

    /**
     * 获取f点在右下角的pathA
     * @return
     */
    private Path getPathAFromLowerRight(){
        pathA.reset();
        pathA.lineTo(0, viewHeight);//移动到左下角
        pathA.lineTo(c.x,c.y);//移动到c点
        pathA.quadTo(e.x,e.y,b.x,b.y);//二阶贝塞尔曲线函数，从c到b画贝塞尔曲线，控制点为e
        pathA.lineTo(a.x,a.y);//移动到a点
        pathA.lineTo(k.x,k.y);//移动到k点
        pathA.quadTo(h.x,h.y,j.x,j.y);//从k到j画贝塞尔曲线，控制点为h
        pathA.lineTo(viewWidth,0);//移动到右上角
        pathA.close();//闭合区域
        return pathA;
    }

    /**
     * 绘制区域C
     * @return
     */
    private Path getPathC(){
        pathC.reset();
        pathC.moveTo(i.x,i.y);//移动到i点
        pathC.lineTo(d.x,d.y);//移动到d点
        pathC.lineTo(b.x,b.y);//移动到b点
        pathC.lineTo(a.x,a.y);//移动到a点
        pathC.lineTo(k.x,k.y);//移动到k点
        pathC.close();//闭合区域
        return pathC;
    }

    /**
     * 绘制区域B
     * @return
     */
    private Path getPathB(){
        pathB.reset();
        pathB.lineTo(0, viewHeight);//移动到左下角
        pathB.lineTo(viewWidth,viewHeight);//移动到右下角
        pathB.lineTo(viewWidth,0);//移动到右上角
        pathB.close();//闭合区域
        return pathB;
    }


}
