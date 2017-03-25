package com.liuzh.one.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuzh.one.R;
import com.liuzh.one.application.App;
import com.squareup.picasso.Picasso;

/**
 * Created by 刘晓彬 on 2017/3/24.
 */

public class MovieProfileActivity extends AppCompatActivity {

    private static final String KEY_NAME = "name";
    private static final String KEY_URL = "url";
    private static final String KEY_DESC = "desc";
    private static final String KEY_INFO = "info";
    private static final String KEY_OFFICIAL_STORY = "official_story";

    public static void start(Context context, String name, String url,
                             String desc, String info, String official_story) {
        Intent intent = new Intent(context, MovieProfileActivity.class);
        intent.putExtra(KEY_NAME, name);
        intent.putExtra(KEY_URL, url);
        intent.putExtra(KEY_DESC, desc);
        intent.putExtra(KEY_INFO, info);
        intent.putExtra(KEY_OFFICIAL_STORY, official_story);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_profile);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        Picasso.with(this)
                .load(intent.getStringExtra(KEY_URL))
                .into((ImageView) findViewById(R.id.iv_poster));
        ((TextView) findViewById(R.id.tv_movie_name))
                .setText(intent.getStringExtra(KEY_NAME));
        ((TextView) findViewById(R.id.tv_desc))
                .setText(intent.getStringExtra(KEY_DESC));
        ((TextView) findViewById(R.id.tv_info))
                .setText(intent.getStringExtra(KEY_INFO));
        ((TextView) findViewById(R.id.tv_official_story))
                .setText(intent.getStringExtra(KEY_OFFICIAL_STORY));
        findViewById(R.id.ll_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
