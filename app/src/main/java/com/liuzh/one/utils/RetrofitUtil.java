package com.liuzh.one.utils;

import com.liuzh.one.bean.DataList;
import com.liuzh.one.bean.list.OneDay;
import com.liuzh.one.bean.list.OneListId;
import com.liuzh.one.bean.movie.Movie;
import com.liuzh.one.bean.music.Music;
import com.liuzh.one.bean.question.Question;
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

    public static final String URL_BASE = "http://v3.wufazhuce.com:8000/api/";

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


//----------------------------------oneListId--------------------------------------------

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


//---------------------------------------oneList------------------------------------------

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


//------------------------------------ReadDetail-----------------------------------

    public interface ReadDetailService {
        @GET("essay/{item_id}?channel=wdj&source=channel_reading" +
                "&source_id=9264&version=4.0.2" +
                "&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Call<Read> getCall(@Path("item_id") int id);
    }

    public static Call<Read> getReadCall(int id) {
        return getRetrofit(URL_BASE).create(ReadDetailService.class).getCall(id);
    }


//-------------------------------------MovieDetail------------------------------------

    public interface MovieDetailService {
        @GET("movie/detail/{item_id}?channel=wdj" +
                "&source=channel_movie&source_id=9240&version=4.0.2" +
                "&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Call<Movie> getCall(@Path("item_id") int id);
    }

    public static Call<Movie> getMovieCall(int id) {
        return getRetrofit(URL_BASE).create(MovieDetailService.class).getCall(id);
    }


//--------------------------------QuestionDetail----------------------------------------

    public interface QuestionDetailService {
        @GET("question/{item_id}?channel=wdj" +
                "&source=channel_reading&source_id=9254&version=4.0.2" +
                "&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Call<Question> getCall(@Path("item_id") int id);
    }

    public static Call<Question> getQuestionCall(int id) {
        return getRetrofit(URL_BASE).create(QuestionDetailService.class).getCall(id);
    }


//------------------------------MusicDetail---------------------------------------------

    public interface MusicDetailService {
        @GET("music/detail/{item_id}?channel=wdj&version=4.0.2" +
                "&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Call<Music> getCall(@Path("item_id") int id);
    }

    public static Call<Music> getMusicCall(int id) {
        return getRetrofit(URL_BASE).create(MusicDetailService.class).getCall(id);
    }


//------------------------------------ReadList-----------------------------------------

    public interface ReadList {
        @GET("channel/reading/more/0?channel=wdj&version=4.0.2" +
                "&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Call<DataList> getCall();
    }

    public static Call<DataList> getReadListCall() {
        return getRetrofit(URL_BASE).create(ReadList.class).getCall();
    }


//----------------------------------------MusicList-------------------------------------

    public interface MusicList {
        @GET("channel/music/more/0?channel=wdj&version=4.0.2" +
                "&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Call<DataList> getCall();
    }

    public static Call<DataList> getMusicListCall() {
        return getRetrofit(URL_BASE).create(MusicList.class).getCall();
    }

//--------------------------------------MovieList----------------------------------------

    public interface MovieList {
        @GET("channel/movie/more/0?channel=wdj&version=4.0.2" +
                "&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
        Call<DataList> getCall();
    }

    public static Call<DataList> getMovieListCall() {
        return getRetrofit(URL_BASE).create(MovieList.class).getCall();
    }

}
