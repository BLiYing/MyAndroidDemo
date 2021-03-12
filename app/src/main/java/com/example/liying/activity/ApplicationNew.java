package com.example.liying.activity;

import android.app.Application;

import com.example.utils.Utils;

/**
 * Description:
 * Created by administrator
 * on 2017-10-11.
 */

public class ApplicationNew extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //解决部分机型上关闭通知栏权限后吐司无法显示问题
        com.hjq.toast.ToastUtils.init(this);
        Utils.init(this);
//        RongIM.init(this);


    }


}
