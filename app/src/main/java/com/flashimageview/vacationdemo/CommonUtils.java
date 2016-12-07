package com.flashimageview.vacationdemo;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 公共工具类
 * author True Lies
 * Created by Administrator on 2016/6/11.
 */
public class CommonUtils {


    /**
     * 获取屏幕的大小
     * @param mContext
     */
    public static void getScreenSize(Context mContext){

        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        App.SCREEN_WIDTH = displayMetrics.widthPixels;
        App.SCREEN_HEIGHT = displayMetrics.heightPixels;
    }


}
