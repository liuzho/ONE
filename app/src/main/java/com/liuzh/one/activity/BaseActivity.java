package com.liuzh.one.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * base activity
 * Created by 刘晓彬 on 2017/3/26.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    protected View mLoadingView;

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

    protected void findLoadingView(int resId) {
        mLoadingView = findViewById(resId);
    }

    protected void hiddenLoadingView() {
        if (mLoadingView == null) {
            return;
        }
        mLoadingView.setVisibility(View.GONE);
    }

    protected void showLoadingView() {
        if (mLoadingView == null) {
            return;
        }
        mLoadingView.setVisibility(View.VISIBLE);
    }


}
