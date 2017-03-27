package com.liuzh.one.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.liuzh.one.R;
import com.liuzh.one.application.App;
import com.liuzh.one.fragment.HomeFragment;
import com.liuzh.one.fragment.ListFragment;
import com.liuzh.one.utils.DensityUtil;
import com.liuzh.one.view.AppToolbar;

import java.util.ArrayList;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    public static final String INTENT_KEY_LIST_ID = "list_id";
    private AppToolbar mToolbar;
    private HomeFragment mHomeFragment;
    private ListFragment mReadFragment;
    private ListFragment mMusicFragment;
    private ListFragment mMovieFragment;
    private FragmentManager mFragmentManager;//fragment manager

    private ImageView iv_home;
    private ImageView iv_read;
    private ImageView iv_music;
    private ImageView iv_movie;

    private boolean operateToolbar = true;


    public static void start(Context context, ArrayList<String> value) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putStringArrayListExtra(INTENT_KEY_LIST_ID, value);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAppToolbar();
        initView();
    }

    protected void initAppToolbar() {
        //init toolbar
        mToolbar = (AppToolbar) findViewById(R.id.toolbar);
        mToolbar.setToolbarTitle(getString(R.string.app_name));
        mToolbar.setLeftDrawable(getResources().getDrawable(R.drawable.user));
        mToolbar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.showToast("用户");
            }
        });
        mToolbar.setRightRDrawable(getResources().getDrawable(R.drawable.search));
        mToolbar.setRightRClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.showToast("搜索");
            }
        });
        mToolbar.setTranslationY(-DensityUtil.dip2px(50));
        mToolbar.setAlpha(0);
        setSupportActionBar(mToolbar);
    }


    public AppToolbar getToolbar() {
        return mToolbar;
    }

    /**
     * 初始化view
     */
    private void initView() {
        mHomeFragment = new HomeFragment();
        //view data
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.fl_fragment, mHomeFragment);
        transaction.show(mHomeFragment);
        transaction.commit();
        iv_home = (ImageView) findViewById(R.id.iv_home);
        iv_read = (ImageView) findViewById(R.id.iv_read);
        iv_music = (ImageView) findViewById(R.id.iv_music);
        iv_movie = (ImageView) findViewById(R.id.iv_movie);

        iv_home.setOnClickListener(this);
        iv_read.setOnClickListener(this);
        iv_music.setOnClickListener(this);
        iv_movie.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        resetTab();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (view.getId()) {
            case R.id.iv_home:
                iv_home.setImageResource(R.drawable.tab_home_checked);
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.fl_fragment, mHomeFragment);
                }
                if (mHomeFragment.toolNeedShow()) {
                    showToolbar();
                } else {
                    hideToolbar();
                }
                transaction.show(mHomeFragment);
                operateToolbar = true;
                break;
            case R.id.iv_read:
                iv_read.setImageResource(R.drawable.tab_read_checked);
                if (mReadFragment == null) {
                    mReadFragment = new ListFragment(ListFragment.TYPE_READ);
                    transaction.add(R.id.fl_fragment, mReadFragment);
                }
                transaction.show(mReadFragment);
                showToolbar();
                operateToolbar = false;
                break;
            case R.id.iv_music:
                iv_music.setImageResource(R.drawable.tab_music_checked);
                if (mMusicFragment == null) {
                    mMusicFragment = new ListFragment(ListFragment.TYPE_MUSIC);
                    transaction.add(R.id.fl_fragment, mMusicFragment);
                }
                transaction.show(mMusicFragment);
                showToolbar();
                operateToolbar = false;
                break;
            case R.id.iv_movie:
                iv_movie.setImageResource(R.drawable.tab_movie_checked);
                if (mMovieFragment == null) {
                    mMovieFragment = new ListFragment(ListFragment.TYPE_MOVIE);
                    transaction.add(R.id.fl_fragment, mMovieFragment);
                }
                transaction.show(mMovieFragment);
                showToolbar();
                operateToolbar = false;
                break;
        }
        transaction.commit();
    }

    private void hideToolbar() {
        mToolbar.setTranslationY(-DensityUtil.dip2px(50));
        mToolbar.setAlpha(0);
    }

    private void showToolbar() {
        mToolbar.setTranslationY(0);
        mToolbar.setAlpha(1);
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mReadFragment != null) {
            transaction.hide(mReadFragment);
        }
        if (mMusicFragment != null) {
            transaction.hide(mMusicFragment);
        }
        if (mMovieFragment != null) {
            transaction.hide(mMovieFragment);
        }
    }

    private void resetTab() {
        iv_home.setImageResource(R.drawable.tab_home_unchecked);
        iv_read.setImageResource(R.drawable.tab_read_unchecked);
        iv_music.setImageResource(R.drawable.tab_music_unchecked);
        iv_movie.setImageResource(R.drawable.tab_movie_unchecked);
    }

    public boolean getOperateToolbar() {
        return operateToolbar;
    }

}
