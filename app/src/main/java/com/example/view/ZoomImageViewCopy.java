package com.example.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;

import java.util.List;

import static com.example.utils.ZoomImageView.log;

/**
 * Description:
 * Created by liying
 * on 2017-10-31.
 */

public class ZoomImageViewCopy extends AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener,
        ScaleGestureDetector.OnScaleGestureListener,View.OnTouchListener{
    private static final String TAG = "ZoomImageView";
    private boolean isInit;


    /**
     * 缩放工具
     */
    private Matrix mMatrix;

    /**
     * 缩放的最小值
     */
    private float mMinScale;
    /**
     * 缩放的中间值
     */
    private float mMidScale;
    /**
     * 缩放的最大值
     */
    private float mMaxScale;

    /**
     * 多点手势触 控缩放比率分析器
     */
    private ScaleGestureDetector mScaleGestureDetector;
    /**
     * 记录上一次多点触控的数量
     */
    private int mLastPointereCount;

    private float mLastX;
    private float mLastY;
    private int mTouchSlop;
    private boolean isCanDrag;
    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;

    //实现双击放大与缩小
    private GestureDetector mGestureDetector;
    private boolean isScaleing;
    private List<MotionEvent> events;
    private OnClickListener onClickListener;
    private int arae_img_id = -1;
    public ZoomImageViewCopy(Context context) {
        this(context,null);
    }

    public ZoomImageViewCopy(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }

    public ZoomImageViewCopy(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {

    }

    /**
     * 获取缩放值
     * @return
     *  缩放值
     */
    public float getScale(){
        float[] values = new float[9];
        mMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    /**
     * 获取放大缩小后的图片宽高
     * @return
     */
    private RectF getMatrixRectF(){
        RectF rectF = new RectF();
        Matrix newMatrix = mMatrix;
        Drawable d = getDrawable();

        if (d != null) {
            rectF.set(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
            newMatrix.mapRect(rectF);
        }
        return rectF;
    }

    /**
     * 在缩放过程中控制我们的位置
     */
    private void checkBoardAndCenterWhenScale() {
        RectF rect = getMatrixRectF();
        int width = getWidth();
        int height = getHeight();
        float deltaX = 0;
        float deltaY = 0;
        //缩放时进行边界检测，防止出现白边
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }

        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = 0;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }
        //如果图片宽或高 小于控件的宽或高
        if(rect.width() < width){
            deltaX = width/2f - rect.right + rect.width()/2;
        }
        if(rect.height() < height){
            deltaY = height/2f - rect.bottom + rect.height()/2;
        }
        mMatrix.postTranslate(deltaX,deltaY);
        setImageMatrix(mMatrix);
    }

    /**
     * 在移动图片的时候进行边界检查
     */
    private void checkBorderWhenTranslate() {
        RectF rectF = getMatrixRectF();

        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        if (rectF.top > 0 && isCheckTopAndBottom) {
            deltaY = -rectF.top;
        }

        if (rectF.bottom < height && isCheckTopAndBottom) {
            deltaY = height - rectF.bottom;
        }


        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltaX = -rectF.left;
        }

        if (rectF.right < width && isCheckLeftAndRight) {
            deltaX = width - rectF.right;
        }

        mMatrix.postTranslate(deltaX, deltaY);
        setImageMatrix(mMatrix);
    }

    private class AutoScaleRunnable implements Runnable {
        /**
         * 要缩放的目标值
         */
        private float mTargetScale;
        private float x; //缩放的中心点x
        private float y; //缩放的中心点y
        private float tmpScale;

        private final float BIGGER = 1.07f;
        private final float SMALL = 0.93f;

        public AutoScaleRunnable(float mTargetScale, float x, float y) {
            this.mTargetScale = mTargetScale;
            this.x = x;
            this.y = y;

            if (getScale() < mTargetScale) {
                tmpScale = BIGGER;
            } else {
                tmpScale = SMALL;
            }
        }

        @Override
        public void run() {
            mMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBoardAndCenterWhenScale();
            setImageMatrix(mMatrix);


            float currentScale = getScale();
            if ((tmpScale > 1.0f && currentScale < mTargetScale)
                    || (tmpScale < 1.0f && currentScale > mTargetScale)) {
                postDelayed(this, 16);
            } else {
                float scale = mTargetScale / currentScale;
                mMatrix.postScale(scale, scale, x, y);
                checkBoardAndCenterWhenScale();
                setImageMatrix(mMatrix);
                isScaleing = false;
            }
        }
    }

    //缩放区间:minScale,maxScale
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        //获取当前触摸事件的放大或缩小的状态值
        float scaleFactor = detector.getScaleFactor();
        if(getDrawable() == null)
            return true;
        //缩放范围的控制
        if((scale < mMaxScale && scaleFactor > 1.0f) ||(scale > mMinScale && scaleFactor < 1.0f) ){
            if(scale*scaleFactor < mMinScale){
                scaleFactor = mMinScale / scale;
            }
            if(scale*scaleFactor > mMaxScale){
                scaleFactor = mMaxScale / scale;
            }
            //缩放
            mMatrix.postScale(scaleFactor,scaleFactor,detector.getFocusX(),detector.getFocusY());
            checkBoardAndCenterWhenScale();
            setImageMatrix(mMatrix);
        }



        return true;//保证事件往下传递
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //如果正在双击，就不要滑动
        if(mGestureDetector.onTouchEvent(event))
            return true;
//        mGestureDetector.onTouchEvent(event);
        mScaleGestureDetector.onTouchEvent(event);
        float y = 0;
        float x = 0;
        int pointCount = event.getPointerCount();
        for (int i = 0; i < pointCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x /= pointCount;
        y /= pointCount;

        if(mLastPointereCount != pointCount){
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }
        mLastPointereCount = pointCount;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                RectF rectF = getMatrixRectF();
                if ((rectF.width() > getWidth() + 0.01f || (rectF.height() > getHeight() + 0.01f))) {
                    if ((rectF.right != getWidth()) && (rectF.left != 0)) {
                        try {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        } catch (Exception e) {
                            log(e.toString());
                        }
                    }
                }
                break;
            }
            case MotionEvent.ACTION_MOVE:
                float dx = 0;
                float dy = 0;
                dx = x - mLastX;
                dy = y - mLastY;
                if(!isCanDrag){
                    isCanDrag = isMoveAction(dx,dy);
                }
                if(isCanDrag){
                    RectF rectf = getMatrixRectF();
                    float width = rectf.width();
                    float height = rectf.height();
                    if(getDrawable() != null) {
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        if (width < getWidth()) {
                            isCheckLeftAndRight = false;
                            dx = 0;
                        }
                        if (height < getHeight()) {
                            isCheckTopAndBottom = false;
                            dy = 0;
                        }
                        mMatrix.postTranslate(dx,dy);
                        checkBorderWhenTranslate();
                        setImageMatrix(mMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                RectF rect = getMatrixRectF();
                if ((rect.width() > getWidth() + 0.01f || (rect.height() > getHeight() + 0.01f))) {
                    if ((rect.right != getWidth()) && (rect.left != 0)) {
                        try {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        } catch (Exception e) {
                            log(e.toString());
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPointereCount = 0;
                break;
            default:
                break;
        }


        return true;
    }

    private boolean isMoveAction(float dx, float dy) {

        return Math.sqrt(dx*dx+dy*dy) > mTouchSlop;

    }
}
