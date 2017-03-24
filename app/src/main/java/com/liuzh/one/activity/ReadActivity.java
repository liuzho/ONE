package com.liuzh.one.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.liuzh.one.R;
import com.liuzh.one.application.App;
import com.liuzh.one.bean.read.Data;
import com.liuzh.one.bean.read.Read;
import com.liuzh.one.utils.HtmlUtil;
import com.liuzh.one.utils.RetrofitUtil;
import com.liuzh.one.view.AppToolbar;
import com.liuzh.one.view.AuthorsView;

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
    private AuthorsView av_authors;
    private TextView tv_info;//
    private TextView tv_copyright;//
    private TextView tv_like_comment;
    private AppToolbar mToolbar;
    private int mID;

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, ReadActivity.class);
        Log.i(TAG, "start: " + id);
        intent.putExtra(KEY_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        mID = getIntent().getIntExtra(KEY_ID, -1);
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
        av_authors = (AuthorsView) findViewById(R.id.av_authors);
        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_copyright = (TextView) findViewById(R.id.tv_copyright);
        tv_like_comment = (TextView) findViewById(R.id.tv_like_comment);
        //init toolbar
        mToolbar = (AppToolbar) findViewById(R.id.toolbar);
        mToolbar.setLeftDrawable(getResources().getDrawable(R.drawable.back));
        mToolbar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mToolbar.setRightLDrawable(getResources().getDrawable(R.drawable.listen));
        mToolbar.setRightLClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.showToast("听阅读");
            }
        });
        mToolbar.setRightRDrawable(getResources().getDrawable(R.drawable.share));
        mToolbar.setRightRClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.showToast("分享");
            }
        });
        setSupportActionBar(mToolbar);
    }

    /**
     * 初始数据
     */
    private void initData(Data data) {
        if (data.tag_list.size() != 0) {
            mToolbar.setToolbarTitle(data.tag_list.get(0).title);
        } else {
            mToolbar.setToolbarTitle("一个阅读");
        }
        mWebView.loadDataWithBaseURL("about:blank",
                HtmlUtil.fmt(data.hp_content), "text/html", "utf-8", null);
        tv_title.setText(data.hp_title);
        tv_author.setText("文／" + data.hp_author);
        tv_info.setText(data.hp_author_introduce + "  " + data.editor_email);
        tv_like_comment.setText(data.praisenum + " 喜欢 · " + data.commentnum + " 评论");
        if (TextUtils.isEmpty(data.copyright)) {
            tv_copyright.setVisibility(View.GONE);
        } else {
            tv_copyright.setText(data.copyright + "");
        }
        av_authors.setAuthor(data.author_list);
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

}
