package com.example.liying.ndktest;

/**
 * Description:测试NDK
 * Created by liying
 * on 2017-8-25.
 */

@SuppressWarnings("JniMissingFunction")
public class NativeUtils {
     static {
        System.loadLibrary("Testcpp");
    }
    public static native String stringFromJNITest();
}
