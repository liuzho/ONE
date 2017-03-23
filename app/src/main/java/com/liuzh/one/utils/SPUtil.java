package com.liuzh.one.utils;

import android.content.SharedPreferences;

import com.liuzh.one.application.App;

/**
 * Created by 刘晓彬 on 2017/3/19.
 */

public class SPUtil {

    public static final String SP_KEY_ONE_LIST_ID = "one_list_id";//one list id的key
    public static final String SP_KEY_OPEN_DATE = "open_date";//打开时间的key

    /**
     * 获取SharedPreferences
     *
     * @return SharedPreferences
     */
    private static SharedPreferences getSP() {
        return App.getContext().getSharedPreferences(
                App.getContext().getPackageName(), App.MODE_PRIVATE);
    }

    /**
     * 向SP写入字符串
     *
     * @param key   键
     * @param value 值
     */
    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = getSP().edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 向SP写入布尔值
     *
     * @param key   键
     * @param value 值
     */
    public static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSP().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 向SP写入int值
     *
     * @param key   键
     * @param value 值
     */
    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = getSP().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 从Sp获取字符串
     *
     * @param key      键
     * @param defValue 默认值
     * @return 获取的字符串
     */
    public static String getString(String key, String defValue) {
        return getSP().getString(key, defValue);
    }

    /**
     * 从Sp获取布尔值
     *
     * @param key      键
     * @param defValue 默认值
     * @return 获取的布尔值
     */
    public static boolean getBoolean(String key, boolean defValue) {
        return getSP().getBoolean(key, defValue);
    }

    /**
     * 从SP获取int数据
     *
     * @param key      键
     * @param defValue 默认值
     * @return 获取的int数据
     */
    public static int getInt(String key, int defValue) {
        return getSP().getInt(key, defValue);
    }
}
