package com.liuzh.one.utils;

import com.liuzh.one.bean.list.ContentList;
import com.liuzh.one.bean.list.OneDay;
import com.liuzh.one.bean.list.OneListId;
import com.liuzh.one.bean.read.Read;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * retrofit工具类
 * Created by 刘晓彬 on 2017/3/19.
 */

public class RetrofitUtil {
    //URL的开头部分
    public static final String URL_BASE = "http://v3.wufazhuce.com:8000/api/";

    //获取list：阅读
    public static final String LIST_TYPE_READ = "reading";
    //获取list：音乐
    public static final String LIST_TYPE_MUSIC = "music";
    //获取list：电影
    public static final String LIST_TYPE_MOVIE = "movie";


    /**
     * 获取Retrofit实例
     *
     * @param baseUrl 请求api头
     * @return retrofit实例
     */
    private static Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * oneListId的接口
     */
    public interface OneListIdService {
        @GET("onelist/idlist/?channel=wdj&version=4.0.2" +
                "&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Call<OneListId> getCall();
    }

    /**
     * 获取OneListId的Call实例
     *
     * @return oneListId Call实例
     */
    public static Call<OneListId> getOneListIdCall() {
        return getRetrofit(URL_BASE).create(OneListIdService.class).getCall();
    }


    /**
     * oneList的接口
     */
    public interface OneListService {
        @GET("onelist/{id}/0?cchannel=wdj&version=4.0.2" +
                "&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Call<OneDay> getCall(@Path("id") int id);
    }

    /**
     * 获取OneList的Call实例
     *
     * @param id 请求数据的id
     * @return oneListId Call实例
     */
    public static Call<OneDay> getOneListCall(int id) {
        return getRetrofit(URL_BASE).create(OneListService.class).getCall(id);
    }


    /**
     * 获取某个类型数据列表的接口
     */
    public interface ListService {
        @GET("channel/{type}/more/0?channel=wdj&version=4.0.2" +
                "&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android\"")
        Call<ContentList> getCall(@Path("type") String t);
    }

    /**
     * 获取List的Call实例
     *
     * @param type list的类型
     * @return Call实例
     */
    public static Call<ContentList> getListCall(String type) {
        return getRetrofit(URL_BASE).create(ListService.class).getCall(type);
    }

    public interface ReadDetailService {
        @GET("essay/{item_id}?channel=wdj&source=channel_reading" +
                "&source_id=9264&version=4.0.2" +
                "&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Call<Read> getCall(@Path("item_id") int id);
    }

    public static Call<Read> getReadCall(int id) {
        return getRetrofit(URL_BASE).create(ReadDetailService.class).getCall(id);
    }


    //----------------------------------------------------详情Retrofit服务
    public interface DetailService {
        @GET("{type}/{item_id}?channel=wdj&source=channel_reading" +
                "&source_id=9264&version=4.0.2" +
                "&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Call<Object> getCall(@Path("type") String type, @Path("id") int id);
    }

    public static Call<Object> getDetailCall(String type, int id) {
        return getRetrofit(URL_BASE).create(DetailService.class).getCall(type, id);
    }
}
