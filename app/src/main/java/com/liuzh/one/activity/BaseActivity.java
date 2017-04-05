package com.liuzh.one.activity;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.liuzh.one.R;

/**
 * base activity
 * Created by 刘晓彬 on 2017/3/26.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    private ImageView mIvLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getContentId());
        findViews();
        initViewData();
        fetchData();
    }


    protected abstract int getContentId();

    protected abstract void fetchData();

    protected abstract void findViews();

    protected abstract void initViewData();

    protected void hiddenLoadingView() {
        mIvLoading.setVisibility(View.GONE);

    }

    protected void showLoadingView() {
        if (mIvLoading == null) {
            mIvLoading = (ImageView) findViewById(R.id.iv_loading);
        }
        ((AnimationDrawable) mIvLoading.getDrawable()).start();
        mIvLoading.setVisibility(View.VISIBLE);
    }


}
