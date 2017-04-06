package com.liuzh.one.utils;

/**
 * 常量
 * Created by 刘晓彬 on 2017/3/19.
 */

public class Constant {
    //intent key : id
    public static final String INTENT_KEY_ID = "intent_key_id";
    //intent key : like count
    public static final String INTENT_KEY_LIKE_COUNT = "intent_key_like_count";
    //intent key : title
    public static final String INTENT_KEY_TITLE = "intent_key_title";
    //intent key : url
    public static final String INTENT_KEY_URL = "intent_key_url";
    //intent key : list id
    public static final String INTENT_KEY_LIST_ID = "intent_key_list_id";
    //intent key : author
    public static final String INTENT_KEY_AUTHOR = "intent_key_author";
    /**
     * -1 下拉刷新head
     * -2 上拉加载bottom
     * 0 one day
     * 1 read 软糖漫画
     * 2 连载
     * 3 问答
     * 4 音乐
     * 5 影视
     */
    public static final int ITEM_TYPE_HEAD = -1;
    public static final int ITEM_TYPE_BOTTOM = -2;
    public static final int ITEM_TYPE_DAY_ONE = 0;
    public static final int ITEM_TYPE_READ_CARTOON = 1;
    public static final int ITEM_TYPE_SERIAL = 2;
    public static final int ITEM_TYPE_QUESTION = 3;
    public static final int ITEM_TYPE_MUSIC = 4;
    public static final int ITEM_TYPE_MOVIE = 5;
}
