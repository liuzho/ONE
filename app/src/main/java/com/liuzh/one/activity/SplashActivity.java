package com.liuzh.one.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.liuzh.one.R;
import com.liuzh.one.application.App;
import com.liuzh.one.bean.list.OneListId;
import com.liuzh.one.utils.DateUtil;
import com.liuzh.one.utils.RetrofitUtil;
import com.liuzh.one.utils.SPUtil;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 首屏页
 * Created by 刘晓彬 on 2017/3/19.
 */

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        fetchOneListId();
    }

    /**
     * 获取oneListId
     */
    public void fetchOneListId() {
        String nowDate = DateUtil.getFormatYMD();
        String lastOpenDate = SPUtil.getString(SPUtil.SP_KEY_OPEN_DATE, "");
        //根据SP存储的年月日信息，如果当天打开过应用即当天获取过oneListId，则不再获取直接进入MainActivity
        if (lastOpenDate.equals(nowDate)) {
            String listIdStr = SPUtil.getString(SPUtil.SP_KEY_ONE_LIST_ID, "");
            ArrayList<String> listId = new ArrayList<>();
            Collections.addAll(listId, listIdStr.split(","));
            startMainActivity(listId);
            return;
        }
        //如果当天第一次打开应用，则获取oneListId数据
        RetrofitUtil.getOneListIdCall()
                .enqueue(new Callback<OneListId>() {
                    @Override
                    public void onResponse(Call<OneListId> call, Response<OneListId> response) {
                        //向SP写入打开应用的日期
                        SPUtil.putString(SPUtil.SP_KEY_OPEN_DATE, DateUtil.getFormatYMD());
                        OneListId listId = response.body();
                        //将oneListId全部存入SP
                        String content = listId.data.get(0);
                        for (int i = 1; i < listId.data.size(); i++) {
                            content = content + "," + listId.data.get(i);
                        }
                        SPUtil.putString(SPUtil.SP_KEY_ONE_LIST_ID, content);

                        startMainActivity(listId.data);
                    }

                    @Override
                    public void onFailure(Call<OneListId> call, Throwable t) {
                        App.showToast("获取oneListId失败\n" + t.getMessage());
                        Log.i(TAG, "onFailure: 获取oneListId失败" + t.getMessage());
                    }
                });
    }

    /**
     * 进入MainActivity
     */
    private void startMainActivity(ArrayList<String> listId) {
        MainActivity.start(this, listId);
        finish();
    }

}
