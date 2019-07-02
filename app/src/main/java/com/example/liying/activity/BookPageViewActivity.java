package com.example.liying.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.example.view.BookPageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookPageViewActivity extends AppCompatActivity {

    @BindView(R.id.view_book_page)
    BookPageView bookPageView;
    private String style = null;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_page_view);
        ButterKnife.bind(this);


    }
}
