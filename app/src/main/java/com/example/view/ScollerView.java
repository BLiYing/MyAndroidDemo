package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.example.myinterface.ScrollerChangeListener;

/**
 * Description:
 * Created by administrator
 * on 2017-11-9.
 */

public class ScollerView extends ScrollView {
    private ScrollerChangeListener scrollerChangeListener;
    public ScollerView(Context context) {
        this(context,null);
    }

    public ScollerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setScrollerChangeListener(ScrollerChangeListener mScroller){
        this.scrollerChangeListener = mScroller;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(scrollerChangeListener != null)
            scrollerChangeListener.onScrollChanged(this,l, t, oldl, oldt);
    }
}
