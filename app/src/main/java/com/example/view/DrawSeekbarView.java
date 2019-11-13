package com.example.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.liying.activity.R;
import com.example.utils.ScreenUtils;

/**
 * Description:自定义view
 * Created by liying
 * on 2017-10-11.
 */

public class DrawSeekbarView extends View{


    // 画笔
    private Paint mDeafultPaint;
    //已售画笔
    private Paint mHaveSalePaint;
    private Paint circlyPaint;
    // 文字
    private Paint mTextPaint;
    private Paint mTextPaintOfred;

    // View 宽高
    private int mViewWidth;
    private int mViewHeight;

    // 图片
    private Bitmap mBitmap;
    private int BWScreenWidth;
    private int BWScreenHeight;

    private float widthOfline = 20;
    private Context mContext;
    private int totalheight = 0;
    private String textstr = "     已售出8件";
    private float raduis = 0;



    private void init(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;       // 缩放图片
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.product_gold, options);
    }
    public DrawSeekbarView(@NonNull Context context) {
        this(context,null);

    }

    public DrawSeekbarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }

    public DrawSeekbarView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        this.mContext = context;



        mDeafultPaint = new Paint();
        // 设置画布模式为填充
        mDeafultPaint.setStyle(Paint.Style.FILL);
        mDeafultPaint.setStrokeWidth(widthOfline);
        //设置线条等图形的抗锯齿
        mDeafultPaint.setAntiAlias(true);
        mDeafultPaint.setColor(Color.parseColor("#D8D8D8"));

        mHaveSalePaint = new Paint();
        // 设置画布模式为填充
        mHaveSalePaint.setStyle(Paint.Style.FILL);
        mHaveSalePaint.setStrokeWidth(widthOfline);
        //设置线条等图形的抗锯齿
        mHaveSalePaint.setAntiAlias(true);
        mHaveSalePaint.setColor(Color.parseColor("#E00E0E"));

        circlyPaint = new Paint();
        // 设置画布模式为填充
        circlyPaint.setStyle(Paint.Style.FILL);
        //设置线条等图形的抗锯齿
        circlyPaint.setAntiAlias(true);
        circlyPaint.setColor(Color.parseColor("#E00E0E"));

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.parseColor("#272727"));
        mTextPaint.setTextSize(46);

        mTextPaintOfred = new Paint();
        mTextPaintOfred.setStyle(Paint.Style.FILL);
        mTextPaintOfred.setColor(Color.parseColor("#E00E0E"));
        mTextPaintOfred.setTextSize(56);

        mViewWidth = ScreenUtils.getScreenPix(mContext).widthPixels;
        mViewHeight = ScreenUtils.getScreenPix(mContext).heightPixels;
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.totalheight = BWScreenHeight*4/5;
        canvas.translate(mViewWidth/4, 0);

        PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        canvas.setDrawFilter(pfd);

        raduis = widthOfline / 2;
        RectF rect = new RectF(-widthOfline,0,widthOfline,totalheight);
        canvas.drawRoundRect(rect,raduis,raduis,mDeafultPaint);
//        canvas.drawLine(0,0,0,totalheight,mDeafultPaint);
//        canvas.drawLine(0,0,0,totalheight/3,mHaveSalePaint);
        float widthOfbitmap = mBitmap.getWidth();
        int topOfbitmap = totalheight/2;
        canvas.drawBitmap(mBitmap,- widthOfbitmap/2,topOfbitmap,mDeafultPaint);
        canvas.drawText(textstr,-mViewWidth/4,topOfbitmap+mBitmap.getHeight()/2,mTextPaint);
        RectF rectOfsale = new RectF(-widthOfline,0,widthOfline,topOfbitmap);
        canvas.drawRoundRect(rectOfsale,raduis,raduis,mHaveSalePaint);


        for (int i = 0; i < 4; i++) {

            String text10 = String.format("................累计售出%d件 预计省 ", i+1);
            int yOftext = totalheight * (i+1) / 4;
            canvas.drawText(text10, widthOfline * 3, yOftext, mTextPaint);
            Rect rect0fText1 = new Rect();
            mTextPaint.getTextBounds(text10, 0, text10.length(), rect0fText1);
            String textMoney = String.format("  %s元",i+1+"");
            canvas.drawText(textMoney, widthOfline * 3 + rect0fText1.width(), yOftext, mTextPaintOfred);
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(BWScreenWidth, BWScreenHeight);
    }

    public void setWidthHeight(int width, int height) {
        this.BWScreenHeight = height;
        this.BWScreenWidth = width;
    }

    public String getTextstr() {
        return textstr;
    }

    public void setTextstr(String textstr) {
        this.textstr = textstr;
    }
}
