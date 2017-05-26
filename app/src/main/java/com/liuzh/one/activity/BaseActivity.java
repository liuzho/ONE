package com.liuzh.one.activity;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
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
        setDarkStatusIcon(true);
    }

    public void setDarkStatusIcon(boolean bDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (bDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    protected abstract int getContentId();

    protected void fetchData() {
    }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
