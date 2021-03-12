package com.example.view;

import android.content.Context;
import android.view.View;

/**
 * Created by      android studio
 *
 * @author :       ly
 * Date            :       2020-01-03
 * Time            :       00:41
 * Version         :       1.0
 * location        :       武汉研发中心
 * 功能描述         :       测试内存溢出的极好例子
 **/
public class MyView extends View {
    /*public MyView(Context context) {
        super(context);
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            Thread.sleep(1000);
        } finally {
            super.finalize();
        }
    }*/
    int mIndex = 0;
    public MyView(Context context, int index) {
        super(context);
        mIndex = index;
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if(mIndex == 10000) {
                Thread.sleep(20000);
            }
        } finally {
            super.finalize();
        }
    }


}
