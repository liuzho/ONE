package com.liuzh.one.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期的工具类
 * Created by 刘晓彬 on 2017/3/19.
 */

public class DateUtil {
    /**
     * 获取格式化后的年月日
     *
     * @return 格式化后的年月日
     */
    public static String getFmtYMD() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy / MM / dd");
        return sdf.format(new Date());
    }

    public static String getFormatYMDHMS() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy / MM / dd  hh:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 获取小时数
     *
     * @return 小时数
     */
    public static String getFmtH() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh");
        return sdf.format(new Date());
    }
}
