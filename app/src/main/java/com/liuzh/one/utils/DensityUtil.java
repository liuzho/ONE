package com.liuzh.one.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

import com.liuzh.one.application.App;

/**
 * 分辨率相关工具类
 * Created by 刘晓彬 on 2017/3/21.
 */

public class DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = App.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = App.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕的宽
     *
     * @param activity activity
     * @return 宽度 px
     */
    public static int getWinWidth(Activity activity) {
        Display d = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        d.getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高
     *
     * @param activity activity
     * @return 高度 px
     */
    public static int getWinHeight(Activity activity) {
        Display d = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        d.getMetrics(dm);
        return dm.heightPixels;
    }
}
