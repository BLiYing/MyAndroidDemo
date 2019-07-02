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
        Utils.init(this);
    }
}
