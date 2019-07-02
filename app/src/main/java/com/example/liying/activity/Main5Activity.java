package com.example.liying.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 测试在浏览器中调起指定app
 */
public class Main5Activity extends AppCompatActivity {
    private final static String TAG = "Main5Activity";
    @BindView(R.id.main1)
    Button main1;
    @BindView(R.id.webview)
    WebView wv;

    public String fileFullName;//照相后的照片的全整路径
    private boolean fromTakePhoto; //是否是从摄像界面返回的webview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        ButterKnife.bind(this);
        initViews();
        /*WebSettings wSet = wv.getSettings();
        wSet.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wSet.setJavaScriptEnabled(true);
        wSet.setAppCacheEnabled(false); // 关闭缓存
        //http://www.shishigongjiao.com.cn/service/service.html
        //http://ic.tcps.com.cn:28081/help/test.html
        wv.loadUrl("http://ic.tcps.com.cn:28081/help/test.html");*/

    }

    /*@OnClick(R.id.main1)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        //file:///android_asset/test.html
        Uri content_url = Uri.parse("https://www.baidu.com/");
        intent.setData(content_url);
        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        startActivity(intent);
    }*/

    @OnClick({R.id.webview,R.id.main1})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.main1:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                //file:///android_asset/test.html
                Uri content_url = Uri.parse("http://ic.tcps.com.cn:28081/help/test.html");
                intent.setData(content_url);
//                intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                startActivity(intent);
                break;

        }


    }

    @SuppressLint("JavascriptInterface")
    private void initViews() {

        main1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto( Math.random()*1000+1 + ".jpg");
            }
        });

        WebSettings setting = wv.getSettings();
        setting.setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });

        wv.setWebChromeClient(new WebChromeClient(){
            //实现js中的alert弹窗在Activity中显示
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.d(TAG, message);
                result.confirm();
                return true;
            }
        });

        wv.loadUrl("file:///android_asset/index.html");
        final Handler mHandler = new Handler();
        //webview增加javascript接口，监听html页面中的js点击事件
        wv.addJavascriptInterface(new Object(){
            public String clickOnAndroid() {//将被js调用
                mHandler.post(new Runnable() {
                    public void run() {
                        fromTakePhoto  = true;
                        //调用 启用摄像头的自定义方法
                        takePhoto("testimg" + Math.random()*1000+1 + ".jpg");
                        Log.e(TAG,"========fileFullName: " + fileFullName);

                    }
                });
                return fileFullName;
            }
        }, "demo");
    }

    /*
     * 调用摄像头的方法
     */
    public void takePhoto(String filename) {
        Log.e(TAG,"----start to take photo2 ----");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_MEDIA_TITLE, "TakePhoto");

        //判断是否有SD卡
        String sdDir = null;
        boolean isSDcardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if(isSDcardExist) {
            sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            sdDir = Environment.getRootDirectory().getAbsolutePath();
        }
        //确定相片保存路径
        String targetDir = sdDir + "/" + "webview_camera";
        File file = new File(targetDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        fileFullName = targetDir + "/" + filename;
        Log.e(TAG,"----taking photo fileFullName: " + fileFullName);
        //初始化并调用摄像头
        intent.putExtra(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(fileFullName)));
        startActivityForResult(intent, 1);
    }

    /*
     * 重写些方法，判断是否从摄像Activity返回的webviewactivity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG,"----requestCode: " + requestCode + "; resultCode " + resultCode + "; fileFullName: " + fileFullName);
        if (fromTakePhoto && requestCode ==1 && resultCode ==-1) {
            wv.loadUrl("javascript:wave2('" + fileFullName + "')");
        } else {
            wv.loadUrl("javascript:wave2('Please take your photo')");
        }
        fromTakePhoto = false;
        super.onActivityResult(requestCode, resultCode, data);
    }

}
