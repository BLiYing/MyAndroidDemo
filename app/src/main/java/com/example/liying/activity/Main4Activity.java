package com.example.liying.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myinterface.ScrollerChangeListener;
import com.example.view.ScollerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 测试scollerview控件监听上下滑动
 */
public class Main4Activity extends AppCompatActivity {

    @BindView(R.id.line2)
    LinearLayout line2;
    @BindView(R.id.scroller)
    ScollerView scroller;
    @BindView(R.id.line1)
    LinearLayout line1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test_scroller);
        ButterKnife.bind(this);
        for (int i = 0; i < 100; i++) {
            TextView textview = new TextView(this);
            textview.setText(String.valueOf(i));
            line2.addView(textview);

        }
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        int heigh_scroller = scroller.getHeight();
        Log.e("@@@@@@@",String.valueOf(heigh_scroller));
        scroller.setScrollerChangeListener(new ScrollerChangeListener() {
            @Override
            public void onScrollChanged(ScollerView scollerView, int x, int y, int oldx, int oldy) {
//                Toast.makeText(Main4Activity.this, String.valueOf(y-oldy), Toast.LENGTH_SHORT).show();
                Log.e("------------",String.valueOf(y));
                int heigh_scroller = scroller.getHeight();
                Log.e("??????????????",String.valueOf(heigh_scroller));
                if(y-oldy < 0){
                    Log.e("↓↓↓↓↓↓","下滑");


                }else{
                    Log.e("↑↑↑↑↑↑","上滑");
                }

            }
        });

    }
}
