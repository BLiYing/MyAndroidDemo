package com.example.utils;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by      android studio
 *
 * @author :       ly
 * Date            :       2020-02-23
 * Time            :       11:10
 * Version         :       1.0
 * location        :       武汉研发中心
 * 功能描述         :
 **/
public class App {

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
