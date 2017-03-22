package com.liuzh.one.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuzh.one.R;
import com.liuzh.one.adapter.ListRVAdapter;
import com.liuzh.one.application.App;
import com.liuzh.one.bean.Author;
import com.liuzh.one.bean.read.Data;
import com.liuzh.one.bean.read.Read;
import com.liuzh.one.utils.CircleTransform;
import com.liuzh.one.utils.RetrofitUtil;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 刘晓彬 on 2017/3/22.
 */

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    private static final String KEY_ID = "key_id";
    private static final String KEY_TYPE = "key_type";

    private WebView mWebView;//web view
    private TextView tv_title;//title
    private TextView tv_author;//author
    private LinearLayout ll_authors;//all author

    private int mID;
    private int mType;

    public static void start(Context context, int id, int type) {
        Intent intent = new Intent(context, DetailActivity.class);
        Log.i(TAG, "start: " + id);
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mID = getIntent().getIntExtra(KEY_ID, -1);
        mType = getIntent().getIntExtra(KEY_TYPE, -1);
        if (mType!= ListRVAdapter.ITEM_TYPE_READ_CARTOON){
            App.showToast("不是阅读类型，暂时没实现");
            return;
        }
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
        ll_authors = (LinearLayout) findViewById(R.id.ll_authors);
    }

    /**
     * 初始数据
     */
    private void initData(Data data) {
        String content = fmt(data.hp_content);
        mWebView.loadDataWithBaseURL("about:blank", content, "text/html", "utf-8", null);
        tv_title.setText(data.hp_title);
        tv_author.setText("文／" + data.hp_author);
        List<Author> authors = data.author_list;
        LayoutInflater inflater = LayoutInflater.from(this);
        //遍历添加作者信息
        for (int i = 0; i < authors.size(); i++) {
            Author author = authors.get(i);
            View authorView = inflater.inflate(R.layout.layout_author, ll_authors);
            ((TextView) authorView.findViewById(R.id.tv_author_name)).setText(author.user_name);
            Log.i(TAG, "initData: " + author.web_url);
            Picasso.with(this)
                    .load(author.web_url)
                    .transform(new CircleTransform())
                    .into((ImageView) authorView.findViewById(R.id.iv_head));
            ((TextView) authorView.findViewById(R.id.tv_profile)).setText(author.desc);
        }
    }

    /**
     * 获取阅读的详细信息
     */
    private void fetchRead() {
        RetrofitUtil.getReadCall(mID).enqueue(new Callback<Read>() {
            @Override
            public void onResponse(Call<Read> call, Response<Read> response) {
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
        //用正则将img的style置为空
        Pattern pattern = Pattern.compile("style=\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(str);
        str = matcher.replaceAll("");

        String result = "<html>" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                "<title>Sign in | Score System</title>" +
                "<style type=\"text/css\">\n" +
                "img{margin-top:15px;margin-bottom:15px;}" +
                "body{display:flex;flex-direction:column;justify-content:center;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                str +
                "</body>" +
                "</html>";
        return result;
    }

}
