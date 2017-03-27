package com.liuzh.one.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.liuzh.one.R;
import com.liuzh.one.application.App;
import com.liuzh.one.bean.music.Data;
import com.liuzh.one.bean.music.Music;
import com.liuzh.one.utils.CircleTransform;
import com.liuzh.one.utils.HtmlUtil;
import com.liuzh.one.utils.RetrofitUtil;
import com.liuzh.one.view.AppToolbar;
import com.liuzh.one.view.AuthorsView;
import com.liuzh.one.view.CDView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * music activity
 * Created by 刘晓彬 on 2017/3/23.
 */

public class MusicActivity extends BaseActivity {

    private static final String KEY_ID = "key_id";

    private AppToolbar mToolbar;
    private TextView tv_loading;
    private CDView cdv_music;
    private TextView tv_music_author;
    private TextView tv_title;
    private TextView tv_author_name;
    private WebView mWebView;
    private TextView tv_info;//作者名+email
    private TextView tv_copyright;
    private TextView tv_like_comment;
    private AuthorsView av_authors;

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, MusicActivity.class);
        intent.putExtra(KEY_ID, id);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        fetchMusic();
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_music;
    }

    private void initView() {
        tv_loading = (TextView) findViewById(R.id.tv_loading);
        tv_loading.setVisibility(View.VISIBLE);
        mToolbar = (AppToolbar) findViewById(R.id.toolbar);
        mToolbar.setLeftDrawable(getResources().getDrawable(R.drawable.back));
        mToolbar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        cdv_music = (CDView) findViewById(R.id.cdv_music);
        tv_music_author = (TextView) findViewById(R.id.tv_music_author);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_author_name = (TextView) findViewById(R.id.tv_author_name);
        mWebView = (WebView) findViewById(R.id.webView);
        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_copyright = (TextView) findViewById(R.id.tv_copyright);
        tv_like_comment = (TextView) findViewById(R.id.tv_like_comment);
        av_authors = (AuthorsView) findViewById(R.id.av_authors);
    }

    private void initData(Data data) {
        if (data.tag_list.size() != 0) {
            mToolbar.setToolbarTitle(data.tag_list.get(0).title);
        } else {
            mToolbar.setToolbarTitle("一个音乐");
        }
        Picasso.with(this)
                .load(data.cover)
                .transform(new CircleTransform())
                .into(cdv_music);
        tv_music_author.setText(" · " + data.title + " · \n" +
                data.author.user_name + " | " + data.album);
        tv_title.setText(data.story_title);
        tv_author_name.setText("文 / " + data.story_author.user_name);
        mWebView.loadDataWithBaseURL("about:blank",
                HtmlUtil.fmt(data.story), "text/html", "utf-8", null);
        tv_info.setText(data.charge_edt + "  " + data.editor_email);
        tv_copyright.setText(data.copyright);
        tv_like_comment.setText(data.praisenum + " 喜欢 · " + data.commentnum + " 评论");
        av_authors.setAuthor(data.author_list);
    }

    private void fetchMusic() {
        int id = getIntent().getIntExtra(KEY_ID, -1);
        if (id == -1) {
            App.showToast("id=-1");
            return;
        }
        RetrofitUtil.getMusicCall(id)
                .enqueue(new Callback<Music>() {
                    @Override
                    public void onResponse(Call<Music> call, Response<Music> response) {
                        initData(response.body().data);
                        tv_loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<Music> call, Throwable t) {
                        fetchMusic();
                        App.showToast("失败-再次请求");
                    }
                });

    }
}
