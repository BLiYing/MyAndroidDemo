package com.example.liying.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utils.NotificationBuildUtil;
import com.example.view.MyView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainHostActivity extends AppCompatActivity {

    @BindView(R.id.main1)
    Button main1;
    @BindView(R.id.main2)
    Button main2;
    @BindView(R.id.main3)
    Button main3;
    @BindView(R.id.main4)
    Button main4;
    @BindView(R.id.main5)
    Button main5;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.main)
    Button main;
    @BindView(R.id.main6)
    Button main6;
    @BindView(R.id.main7)
    Button main7;
    @BindView(R.id.main9)
    Button main9;

    private boolean isConnect;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_host);

        ButterKnife.bind(this);
        String getUpStr = "上车";
        String startNameStr = "是肯定解释了快捷方式李开复解释了开发阶段索拉卡大姐夫";
        SpannableStringBuilder snSB = new SpannableStringBuilder();
        snSB.append(startNameStr + getUpStr);
        if (startNameStr != null && getUpStr != null) {
            AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(convertDP2PX(14));
            snSB.setSpan(absoluteSizeSpan, startNameStr.length(), (startNameStr + getUpStr).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
            snSB.setSpan(colorSpan, startNameStr.length(), (startNameStr + getUpStr).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

            tv.setText(snSB);
        }
//        String token = "RwFTnY/cPSugzpnl1/lef8oagh4UemBImB8VJN3wgG3ptgQzE4BlGoOAo4opEl4/nHZnq49KH5cMU9OEov43QseJ0/5XJxaZ";
//        connect(token);

    }

    public int convertDP2PX(float dpValue) {
        float scale = getResources().getDisplayMetrics().density;
        int px = (int) (dpValue * scale + 0.5f);
        return px;
    }

    @OnClick({R.id.main1, R.id.main2, R.id.main3, R.id.main4, R.id.main, R.id.main5, R.id.main6,R.id.main7,R.id.main8,R.id.main9
            ,R.id.main10,R.id.main11})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.main:
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.main1:
//                onButtonClick();
                NotificationBuildUtil.showNotification(this, "1", "1");
            case R.id.main2:
                intent.setClass(this, Main2Activity.class);
                startActivity(intent);
                break;
            case R.id.main3:
                intent.setClass(this, Main3Activity.class);
                startActivity(intent);
                break;
            case R.id.main4:
                intent.setClass(this, Main4Activity.class);
                startActivity(intent);
                break;
            case R.id.main5:

                intent.setClass(this, Main5Activity.class);
                startActivity(intent);
                break;

            case R.id.main6:
                intent.setClass(this, Main6Activity.class);
                startActivity(intent);
                break;
            case R.id.main7:
                intent.setClass(this, BookPageViewActivity.class);
                startActivity(intent);
                break;
            case R.id.main8:
                intent.setClass(this, Main8Activity.class);
                startActivity(intent);
                break;
            case R.id.main9:
                intent.setClass(this, Main9Activity.class);
                startActivity(intent);
                break;
            case R.id.main10:
                sendShareMessage();
                break;
            case R.id.main11:
                invokeCetc15Map(this,null);
                /*if (isConnect) {
                    intent.setClass(this, SubConversationListActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtil.show("等待连接成功");
                }*/
                break;
            default:
                break;
        }
    }

    private int count = 5000;
    void onButtonClick(){
        for (int i = 0; i < 100000; i++) {
            View view = new MyView(this,count);
            count++;
        }
    }

    //发送分享的信息
    private void sendShareMessage() {
        String packageName = "com.yuntongxun.rongxin.lite";
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);//主要用于判断用户是否安装了要分享过去的APP
        if (intent != null) {
            Intent share = new Intent();
            share.setClassName(packageName, "com.yuntongxun.rongxin.lite.ui.PreStartActivity");
            share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            share.setAction(Intent.ACTION_SEND);
            share.setType("text/plain"); //分享的是文本类型
            share.putExtra(Intent.EXTRA_TEXT, "https://blog.csdn.net/oudetu/article/details/78443826");//分享出去的内容
//            share.putExtra(Intent.EXTRA_TEXT, "https://123.45");//分享出去的内容
//            share.putExtra(Intent.EXTRA_TEXT, "http://192.168.1.217/page/news_detail.html?id=4731");//分享出去的内容
//            startActivity(Intent.createChooser(share, ""));
            startActivity(share);
            getSupportFragmentManager().popBackStack();
        } else {
            Toast.makeText(getApplicationContext(), "你还没安装该应用，请先安装", Toast.LENGTH_LONG).show();
        }
    }

    private void invokeCetc15Map(Context context, Map arg){
        final List<String> packages = checkInstalledPackage(MAP_PACKAGES);
        if (packages.size() == 0) {
            Toast.makeText(this,"安装地图app", Toast.LENGTH_SHORT).show();
            return;
        }else{
            String appName = getProgramNameByPackageName(context,packages.get(0));
            Toast.makeText(this,appName, Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent();
        intent.setAction("eterramap.app");
        intent.putExtra("lon",(Double) 116.368);
        intent.putExtra("lat",(Double) 39.9871);
        intent.putExtra("name","太极大厦");
        context.startActivity(intent);
    }
    //1.百度地图包名
    public static final String BAIDUMAP_PACKAGENAME = "com.baidu.BaiduMap";
    //2.高德地图包名
    public static final String AUTONAVI_PACKAGENAME = "com.autonavi.minimap";
    //3.腾讯地图包名
    public static final String QQMAP_PACKAGENAME = "com.tencent.map";
    //3.Google地图包名
    public static final String GOOGLEMAP_PACKAGENAME = "com.google.android.apps.maps";
    public static final String CETC15_PACKAGENAME = "com.cetc15.eterramap";
    private static final String[] MAP_PACKAGES = {BAIDUMAP_PACKAGENAME, AUTONAVI_PACKAGENAME, QQMAP_PACKAGENAME, GOOGLEMAP_PACKAGENAME,CETC15_PACKAGENAME};


    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param packageNames 可变参数 String[]
     * @return 目标软件中已安装的列表
     */
    public List<String> checkInstalledPackage(String... packageNames) {

        //获取packageManager
        final PackageManager packageManager = getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储
        List<String> newPackageNames = new ArrayList<>();
        int count = packageNames.length;

        if (packageInfos != null && packageInfos.size() > 0) {

            outermost:
            for (String packageName : packageNames) {
                for (int i = 0; i < packageInfos.size(); i++) {
                    String packageInfo = packageInfos.get(i).packageName;
                    if (packageInfo.contains(packageName)) {
                        newPackageNames.add(packageName);
                        if (newPackageNames.size() == count) {
                            break outermost;//这里使用了循环标记，跳出外层循环
                        }
                    }
                }
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return newPackageNames;
    }

    public String getProgramNameByPackageName(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        String name = null;
        try {
            name = pm.getApplicationLabel(
                    pm.getApplicationInfo(packageName,
                            PackageManager.GET_META_DATA)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }
}
