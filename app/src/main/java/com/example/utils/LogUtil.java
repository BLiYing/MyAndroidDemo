package com.example.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Joeshould on 2017/8/25.
 */

public class LogUtil {
    private static final String TAG = LogUtil.class.getSimpleName();
    private LogUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    // 是否需要打印log，可以在application的onCreate函数里面初始化
    public static boolean isDebug = true;

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug && !TextUtils.isEmpty(msg))
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug && !TextUtils.isEmpty(msg))
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug && !TextUtils.isEmpty(msg))
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug && !TextUtils.isEmpty(msg))
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(msg))
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(msg))
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(msg))
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(msg))
            Log.v(tag, msg);
    }

}
