package com.liuzh.one.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 刘晓彬 on 2017/3/23.
 */

public class MusicActivity extends AppCompatActivity {

    private static final String KEY_ID = "key_id";

    private int mID;
    private Data mData;
    private AppToolbar mToolbar;

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, MusicActivity.class);
        intent.putExtra(KEY_ID, id);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        mID = getIntent().getIntExtra(KEY_ID, -1);
        fetchMusic();
        initView();
    }

    private void initView() {
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


    }

    private void initData() {
        if (mData.tag_list.size() != 0) {
            mToolbar.setToolbarTitle(mData.tag_list.get(0).title);
        } else {
            mToolbar.setToolbarTitle("一个音乐");
        }
        Picasso.with(this)
                .load(mData.cover)
                .transform(new CircleTransform())
                .into((ImageView) findViewById(R.id.cdv_music));
        ((TextView) findViewById(R.id.tv_music_author)).setText(" · " +
                mData.title + " · \n" + mData.author.user_name + " | " + mData.album);
        ((TextView) findViewById(R.id.tv_title)).setText(mData.story_title);
        ((TextView) findViewById(R.id.tv_author_name))
                .setText("文 / " + mData.story_author.user_name);
        ((WebView) findViewById(R.id.webView)).loadDataWithBaseURL("about:blank",
                HtmlUtil.fmt(mData.story), "text/html", "utf-8", null);
        ((TextView) findViewById(R.id.tv_info))
                .setText(mData.charge_edt + "  " + mData.editor_email);
        ((TextView) findViewById(R.id.tv_copyright)).setText(mData.copyright);
        ((TextView) findViewById(R.id.tv_like_comment))
                .setText(mData.praisenum + " 喜欢 · " + mData.commentnum + " 评论");
        ((AuthorsView) findViewById(R.id.av_authors)).setAuthor(mData.author_list);
    }

    private void fetchMusic() {
        RetrofitUtil.getMusicCall(mID)
                .enqueue(new Callback<Music>() {
                    @Override
                    public void onResponse(Call<Music> call, Response<Music> response) {
                        mData = response.body().data;
                        initData();
                    }

                    @Override
                    public void onFailure(Call<Music> call, Throwable t) {
                        fetchMusic();
                        App.showToast("失败-再次请求");
                    }
                });
    }


}
