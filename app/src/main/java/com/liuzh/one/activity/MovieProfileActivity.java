package com.liuzh.one.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuzh.one.R;
import com.squareup.picasso.Picasso;

/**
 * movie profile
 * Created by 刘晓彬 on 2017/3/24.
 */

public class MovieProfileActivity extends BaseActivity {

    private static final String KEY_NAME = "name";
    private static final String KEY_URL = "url";
    private static final String KEY_DESC = "desc";
    private static final String KEY_INFO = "info";
    private static final String KEY_OFFICIAL_STORY = "official_story";

    private ImageView mIvPoster;
    private TextView mTvMovieName;
    private TextView mTvDesc;
    private TextView mTvInfo;
    private TextView mTvOfficialStory;
    private LinearLayout mLlContent;


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
    protected int getContentId() {
        return R.layout.activity_movie_profile;
    }

    @Override
    protected void fetchData() {
    }

    @Override
    protected void findViews() {
        mTvMovieName = ((TextView) findViewById(R.id.tv_movie_name));
        mIvPoster = (ImageView) findViewById(R.id.iv_poster);
        mTvDesc = ((TextView) findViewById(R.id.tv_desc));
        mTvInfo = ((TextView) findViewById(R.id.tv_info));
        mTvOfficialStory = ((TextView) findViewById(R.id.tv_official_story));
        mLlContent = (LinearLayout) findViewById(R.id.ll_content);
    }

    @Override
    protected void initViewData() {
        Intent intent = getIntent();
        mTvMovieName.setText(intent.getStringExtra(KEY_NAME));
        mTvDesc.setText(intent.getStringExtra(KEY_DESC));
        mTvInfo.setText(intent.getStringExtra(KEY_INFO));
        mTvOfficialStory.setText(intent.getStringExtra(KEY_OFFICIAL_STORY));
        mLlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Picasso.with(this)
                .load(intent.getStringExtra(KEY_URL))
                .into(mIvPoster);
    }

}
