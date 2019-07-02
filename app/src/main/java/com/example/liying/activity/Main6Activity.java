package com.example.liying.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;

import com.example.view.TestDrawLineView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main6Activity extends AppCompatActivity {

    @BindView(R.id.testPv)
    TestDrawLineView testPv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        ButterKnife.bind(this);
//        TestPathView testPv = new TestPathView(this);
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        testPv.setWidthHeight(15000, height * 2 / 3);// 重新绘制宽高，不然自定义控件放在ScrollView里面没有高度不显示
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(400, ViewGroup.LayoutParams.MATCH_PARENT);
//        testPv.setLayoutParams(params);

    }
}
