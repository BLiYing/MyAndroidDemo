package com.example.liying.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;

import com.example.utils.ScreenUtils;
import com.example.view.DrawSeekbarView;
import com.example.view.TestDrawLineView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main6Activity extends AppCompatActivity {

    @BindView(R.id.testPv)
    DrawSeekbarView testPv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        ButterKnife.bind(this);
        testPv.setWidthHeight(ScreenUtils.getScreenWidthPix(this),ScreenUtils.getScreenHeightPix(this)/3);


    }
}
