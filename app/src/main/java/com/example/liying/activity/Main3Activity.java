package com.example.liying.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.utils.ZoomImageView;

/**
 * 测试自定义ImageView实现触摸控制图片放大和缩小
 */
public class Main3Activity extends Activity {
    private ViewPager viewPager;
    private int[] iMage = new int[]{R.mipmap.ic_launcher,R.mipmap.timg2,R.mipmap.timg3,R.mipmap.timg4};
    private ImageView[] imageViews = new ImageView[iMage.length];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vp);
        viewPager = (ViewPager) findViewById(R.id.id_viewpager);
//        setContentView(R.layout.activity_main3);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageViews.length;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ZoomImageView zoomImageView = new ZoomImageView(getApplicationContext());
                zoomImageView.setImageResource(iMage[position]);
                imageViews[position] = zoomImageView;
                container.addView(zoomImageView);
                return zoomImageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(imageViews[position]);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });


    }
}
