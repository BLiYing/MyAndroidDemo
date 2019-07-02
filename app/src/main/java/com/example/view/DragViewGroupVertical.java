package com.example.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by liying on 2017-3-21.
 */

public class DragViewGroupVertical extends FrameLayout{

    private ViewDragHelper mViewDragHelper;
    private View mMenuView,mMainView;
    private int mWidth;
     public DragViewGroupVertical(Context context){
         this(context,null);

     }

    public DragViewGroupVertical(Context context, AttributeSet attrs){
        this(context,null,0);
    }

    public DragViewGroupVertical(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
        initView();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = mMenuView.getMeasuredHeight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    private void initView(){
        mViewDragHelper = ViewDragHelper.create(this,callback);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback(){

        @Override
        public boolean tryCaptureView(View child, int pointerId) {

            return mMainView == child;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return 0;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
//            if(mMainView.getTop() < 10){
//                mViewDragHelper.smoothSlideViewTo(mMainView,0,-Math.abs((int)yvel));
//                Log.e("@@@@@@@@@@@@@",String.valueOf(yvel));
//                ViewCompat.postInvalidateOnAnimation(DragViewGroupVertical.this);
//            }else{
//                mViewDragHelper.smoothSlideViewTo(mMainView,0,Math.abs((int)yvel));
//                Log.e("-------------",String.valueOf(yvel));
//                ViewCompat.postInvalidateOnAnimation(DragViewGroupVertical.this);
//            }
            if(Math.abs((int)yvel) < 30)
                mViewDragHelper.smoothSlideViewTo(mMainView,0,(int)yvel);
            Log.e("-------------",String.valueOf(yvel));
            ViewCompat.postInvalidateOnAnimation(DragViewGroupVertical.this);
        }



    };

    @Override
    public void computeScroll() {
        if(mViewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
