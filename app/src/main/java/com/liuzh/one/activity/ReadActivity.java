package com.liuzh.one.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.liuzh.one.R;
import com.liuzh.one.application.App;
import com.liuzh.one.bean.read.Data;
import com.liuzh.one.bean.read.Read;
import com.liuzh.one.utils.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 刘晓彬 on 2017/3/22.
 */

public class ReadActivity extends AppCompatActivity {

    private static final String TAG = "ReadActivity";
    private static final String KEY_ID = "key_id";

    private WebView mWebView;//web view
    private TextView tv_title;//title
    private TextView tv_author;//author

    private int read_id;

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, ReadActivity.class);
        Log.i(TAG, "start: " + id);
        intent.putExtra(KEY_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_detail);
        read_id = getIntent().getIntExtra(KEY_ID, -1);
        initView();
        fetchRead();
    }

    /**
     * find view
     */
    private void initView() {
        mWebView = (WebView) findViewById(R.id.webView);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_author = (TextView) findViewById(R.id.tv_author);
    }

    /**
     * 初始数据
     */
    private void initData(Data data) {
        String content = fmt(data.hp_content);
        mWebView.loadDataWithBaseURL("about:blank", content, "text/html", "utf-8", null);
        tv_title.setText(data.hp_title);
        tv_author.setText("文／" + data.hp_author);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        mWebView.getSettings().setLoadWithOverviewMode(true);
//        mWebView.setInitialScale(400);
    }

    /**
     * 获取阅读的详细信息
     */
    private void fetchRead() {
        RetrofitUtil.getReadCall(read_id).enqueue(new Callback<Read>() {
            @Override
            public void onResponse(Call<Read> call, Response<Read> response) {
                Log.i(TAG, "onResponse: " + response.body().data.hp_content);
                initData(response.body().data);
            }

            @Override
            public void onFailure(Call<Read> call, Throwable t) {
                App.showToast("获取read失败");
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private String fmt(String str) {
        String result = "<html>" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                "<title>Sign in | Score System</title>" +
                "<style type=\"text/css\">\n" +
                "img{margin:0 auto;}" +
//                "body{display:flex;flex-direction:column;justify-content:center;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<span>" + str + "</span>" +
                "</body>" +
                "</html>";
        return result;
    }

}
