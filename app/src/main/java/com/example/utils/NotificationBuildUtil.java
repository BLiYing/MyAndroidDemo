package com.example.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import com.example.liying.activity.MainHostActivity;
import com.example.liying.activity.R;

public class NotificationBuildUtil {

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static void showNotification(Context context, String noticeTile, String content) {
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent inten = new Intent(context, MainHostActivity.class);
		PendingIntent pd = PendingIntent.getActivity(context, 0, inten, 0);

		/*
		 * SKD中API Level低于11
		 
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			Notification n = new Notification();
			n.flags |= Notification.FLAG_SHOW_LIGHTS;
			n.flags |= Notification.FLAG_AUTO_CANCEL;
			n.defaults = Notification.DEFAULT_ALL;
			n.icon = R.drawable.logo2;
			n.when = System.currentTimeMillis();
			//n.setLatestEventInfo(context, noticeTile, content, null);
			nm.notify(R.string.app_name, n);
		}*/
		/*
		 * SKD中API Level高于11低于16
		 */
		int sdk_int = Build.VERSION.SDK_INT;
		int resIdIcon = 0;
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
			resIdIcon = R.mipmap.ic_launcher;
		}else{
			resIdIcon = R.mipmap.ic_launcher;
		}
		 if ( sdk_int >= Build.VERSION_CODES.HONEYCOMB
				&& sdk_int < Build.VERSION_CODES.JELLY_BEAN) {
			Notification.Builder builder1 = new Notification.Builder(context);
			builder1.setSmallIcon(resIdIcon); // 设置图标
//			builder1.setTicker(BusApplication.getInstance().getApplicationContext().getResources().getString(R.string.tcps_remind_message));
			builder1.setContentTitle(noticeTile); // 设置标题
			builder1.setContentText(content); // 消息内容
			builder1.setWhen(System.currentTimeMillis()); // 发送时间
			builder1.setDefaults(Notification.DEFAULT_ALL); // 设置默认的提示音，振动方式，灯光
			builder1.setAutoCancel(true);// 打开程序后图标消失
			builder1.setContentIntent(pd);
			Notification notification = builder1.getNotification();
			nm.notify(R.string.app_name, notification); //
		}
		/*
		 * SKD中API Level高于16
		 */
		else if (sdk_int >= Build.VERSION_CODES.JELLY_BEAN && sdk_int < Build.VERSION_CODES.O ) {
			// 新建状态栏通知
			Notification.Builder baseNF1 = new Notification.Builder(context);
			baseNF1.setSmallIcon(resIdIcon);
//			baseNF1.setTicker(BusApplication.getInstance().getApplicationContext().getResources().getString(R.string.tcps_remind_message));
			baseNF1.setAutoCancel(true);
			baseNF1.setContentTitle(noticeTile);
			baseNF1.setContentText(content);
			baseNF1.setAutoCancel(true);
			baseNF1.setWhen(System.currentTimeMillis());
			baseNF1.setContentIntent(pd);
			Notification baseNF = baseNF1.build();// 获取一个Notification
			baseNF.defaults = Notification.DEFAULT_ALL;// 设置为默认的声音
			nm.notify(R.string.app_name, baseNF);// 显示通知 break;
		}else if(sdk_int >= Build.VERSION_CODES.O){
			 NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

			 NotificationChannel channel = new NotificationChannel("2",
					 "Channel2", NotificationManager.IMPORTANCE_DEFAULT);
			 channel.enableLights(true);
			 channel.setLightColor(Color.GREEN);
			 channel.setShowBadge(true);
			 manager.createNotificationChannel(channel);
			 Notification.Builder builderAndroidO = new Notification.Builder(context,"2");
			 builderAndroidO
					 .setSmallIcon(resIdIcon)
					 .setAutoCancel(true)
					 .setWhen(System.currentTimeMillis())
					 .setContentText(content)
					 .setContentTitle(noticeTile)
					 .setWhen(System.currentTimeMillis())
					 .setContentIntent(pd);
			 Notification baseNF1 = builderAndroidO.build();// 获取一个Notification
			 baseNF1.defaults = Notification.DEFAULT_ALL;// 设置为默认的声音

			 manager.notify(R.string.app_name, baseNF1);

		 }
	}

}
