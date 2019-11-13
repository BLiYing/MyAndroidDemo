package com.example.liying.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.view.WPopupWindow;
import com.example.view.WheelView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 1.测试第三方权限库AndPermission
 * 2.测试单选弹出框
 * 3.测试系统弹出框
 * 4.测试FloatingActionButton
 */
public class MainActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener {
    WPopupWindow popupWindow;
    AlertDialog d;
    private String[] permassion = {Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        //测试第三方权限库
        AndPermission.with(this)
                .requestCode(200)
                .permission(permassion)
                .callback(listener)
                .start();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                popupWindow.showAtLocation(fab, Gravity.BOTTOM, 0, 0);


            }
        });

        View wh = LayoutInflater.from(this).inflate(R.layout.common_window_wheel, null);
        final WheelView picker = (WheelView) wh.findViewById(R.id.wheel);
        TextView textView = (TextView) wh.findViewById(R.id.left);
        TextView textViewright = (TextView) wh.findViewById(R.id.right);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


        textViewright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Toast.makeText(MainActivity.this, (String) picker.getCenterItem(), Toast.LENGTH_LONG).show();
                Snackbar.make(fab, (String) picker.getCenterItem(), Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });
        picker.addData("轰趴馆");
        picker.addData("台球");
        picker.addData("密室逃脱");
        picker.addData("卡丁车");
        picker.addData("桌游");
        picker.addData("真人CS");
        picker.addData("DIY");
        picker.addData("轰趴馆");
        picker.addData("台球");
        picker.addData("密室逃脱");
        picker.addData("卡丁车");
        picker.addData("桌游");
        picker.addData("真人CS");
        picker.setCenterItem(4);
        popupWindow = new WPopupWindow(wh);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/

       /* NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Toast.makeText(this, NativeUtils.stringFromJNITest(), Toast.LENGTH_SHORT).show();*/
        Messenger messenger = new Messenger(new MessengerHandle());
    }

    private void goPay(){
        final Handler handler = new Handler();
        final String orderInfo = "alipay_sdk=alipay-sdk-java-4.6.0.ALL&app_id=2019101568388745&biz_content=%7B%22body%22%3A%22%E7%94%A8%E6%88%B7%E8%B4%AD%E4%B9%B0%E6%B5%8B%E8%AF%95%E5%95%86%E5%93%81%2C%E8%AE%A2%E5%8D%95%E7%BC%96%E5%8F%B7%EF%BC%9A201910230101000018%22%2C%22out_trade_no%22%3A%22201910230101000018%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22yosuns%E5%B9%B3%E5%8F%B0APP%E5%95%86%E5%93%81%EF%BC%9A201910230101000018%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=JSON&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F121.40.92.246%2Fapi%2FappOrder%2FnotifyUrl&sign=SA%2FOf1hc9CnfL%2FNNIgBKlwWPax%2Bdd2CdX215ylYSIuxNpOZC2E83T3w58hkrM8cpCYBQ7F9mSHZvw5DsRJAW0HZ7ddPzgk2a7MCXlPUdMxwMqlY4Fibv1twK65%2FtoX7uBp7bCQ6k%2BGOPPCKb1qus%2B5gQ7kus%2Bxy1RmA%2BZIJ5uW2d%2BWwPp%2FAtiyZ%2Fa%2B%2ByDSy7LR20oZsTOabP%2BB69TkHPSPYtjA7OwOPrBCYrYXnVo6oUbc8UL8YHq%2FAiTKe9mVuuRqsvrElQdQUyqu1TrSZHCfF6syKXEPZgXXr%2BJ9PYf8njxa4PhO%2FE%2B5BQ4uKOzUimos%2FmXbK9VqvIQ8b5XlPnlg%3D%3D&sign_type=RSA2&timestamp=2019-10-23+09%3A17%3A30&version=1.0";
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(MainActivity.this);//调用支付接口
                Map<String, String> result = alipay.payV2(orderInfo, true);//支付结果
                Log.i("msp", result.toString());

                /*Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);*/
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private static class MessengerHandle extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    private ServiceConnection mService = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // Successfully.
            if(requestCode == 200) {

            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // Failure.
            if(requestCode == 200) {
                // TODO ...
            }
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private final OkHttpClient client = new OkHttpClient();

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("http://httpbin.org/delay/2") // This URL is served with a 2 second delay.
                .build();

        final long startNanos = System.nanoTime();
        final Call call = client.newCall(request);

        // Schedule a job to cancel the call in 1 second.
        executor.schedule(new Runnable() {
            @Override public void run() {
                System.out.printf("%.2f Canceling call.%n", (System.nanoTime() - startNanos) / 1e9f);
                call.cancel();
                System.out.printf("%.2f Canceled call.%n", (System.nanoTime() - startNanos) / 1e9f);
            }
        }, 1, TimeUnit.SECONDS);

        try {
            System.out.printf("%.2f Executing call.%n", (System.nanoTime() - startNanos) / 1e9f);
            Response response = call.execute();
            System.out.printf("%.2f Call was expected to fail, but completed: %s%n",
                    (System.nanoTime() - startNanos) / 1e9f, response);
        } catch (IOException e) {
            System.out.printf("%.2f Call failed as expected: %s%n",
                    (System.nanoTime() - startNanos) / 1e9f, e);
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            try {
                run();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
