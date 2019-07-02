package com.example.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 适屏工具
 * 
 * @author Zhq
 * 
 */
public class DisplayTool {
	public static Context mContext;
	private static DisplayTool instance = null;

	public static DisplayTool getInstance() {
		if (instance == null) {
			mContext = Utils.getContext();
			instance = new DisplayTool();
		}
		return instance;
	}

	/**
	 * 根据手机的分辨率?dp 的单�?转成�?px(像素)
	 */
	public int convertDP2PX(float dpValue) {
		float scale = mContext.getResources().getDisplayMetrics().density;
		int px = (int) (dpValue * scale + 0.5f);
		return px;
	}

	/**
	 * 根据手机的分辨率�?px(像素) 的单�?转成�?dp
	 */
	public int convertPX2DP(float pxValue) {
		float scale = mContext.getResources().getDisplayMetrics().density;
		int dp = (int) (pxValue / scale + 0.5f);
		return dp;
	}

	/**
	 * 获取屏幕宽度 dp
	 */
	public int getDisplayWidthDPSize() {
		WindowManager mWindowManager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metric = new DisplayMetrics();
		mWindowManager.getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; // 屏幕宽度（像素）
		return convertPX2DP(width);
	}

	/**
	 * 获取屏幕高度 dp
	 */
	public int getDisplayHeightDPSize() {
		WindowManager mWindowManager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metric = new DisplayMetrics();
		mWindowManager.getDefaultDisplay().getMetrics(metric);
		int height = metric.heightPixels; // 屏幕高度（像素）
		return convertPX2DP(height);
	}

	/**
	 * 获取屏幕密度
	 * 
	 * @return
	 */
	public int getDensityDPI() {
		DisplayMetrics dm = new DisplayMetrics();
		dm = mContext.getResources().getDisplayMetrics();

		// float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
		int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
		return densityDPI;

	}

	/**
	 * 获取屏幕密度
	 * 
	 * @return
	 */
	public float getDensity() {
		DisplayMetrics dm = new DisplayMetrics();
		dm = mContext.getResources().getDisplayMetrics();

		float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
		// int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
		return density;

	}


	/**
	 * 获得屏幕尺寸
	 * @param context
	 * @return
	 */
	public static Point getScreenSize(Context context) {
		Point point = new Point();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getSize(point);

		return point;
	}

    public DisplayMetrics getDisplayMetrics() {
        WindowManager mWindowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(metric);
        return metric;
    }
}
