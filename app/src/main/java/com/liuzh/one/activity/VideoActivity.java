package com.liuzh.one.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.liuzh.one.R;
import com.liuzh.one.utils.Constant;

/**
 * 播放视频   activity
 * Created by 刘晓彬 on 2017/4/2.
 */

public class VideoActivity extends AppCompatActivity {


    private VideoView mVideoView;

    public static void start(Context context, String url) {
        Intent starter = new Intent(context, VideoActivity.class);
        starter.putExtra(Constant.INTENT_KEY_URL, url);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_video);
        mVideoView = (VideoView) findViewById(R.id.webView);
        String url = getIntent().getStringExtra(Constant.INTENT_KEY_URL);
        mVideoView.setVideoURI(Uri.parse(url));
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.start();
    }
}
