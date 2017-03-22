package com.liuzh.one.utils;

import java.util.Date;

/**
 * Created by 刘晓彬 on 2017/3/19.
 */

public class DateUtil {
    /**
     * 获取格式化后的年月日
     *
     * @return 格式化后的年月日
     */
    public static String getFormatYMD() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yy / MM / dd");
        return sdf.format(new Date());
    }

    public static String getFormatYMDHMS() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yy / MM / dd  hh:mm:ss");
        return sdf.format(new Date());
    }

    public static String getFormatH() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh");
        return sdf.format(new Date());
    }
}
