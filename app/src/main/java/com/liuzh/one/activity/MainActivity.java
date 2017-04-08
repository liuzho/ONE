package com.liuzh.one.activity;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;

import com.liuzh.one.R;
import com.liuzh.one.application.App;
import com.liuzh.one.fragment.HomeFragment;
import com.liuzh.one.fragment.ListFragment;
import com.liuzh.one.utils.Constant;
import com.liuzh.one.utils.DensityUtil;
import com.liuzh.one.view.AppToolbar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private AppToolbar mToolbar;
    private HomeFragment mHomeFragment;
    private ListFragment mReadFragment;
    private ListFragment mMusicFragment;
    private ListFragment mMovieFragment;
    private FragmentManager mFragmentManager;//fragment manager

    private ImageView mIvHome;
    private ImageView mIvRead;
    private ImageView mIvMusic;
    private ImageView mIMovie;

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;


    public static void start(Context context, ArrayList<String> value) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putStringArrayListExtra(Constant.INTENT_KEY_LIST_ID, value);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }


    public void setToolbarTitle(String title) {
        mToolbar.setToolbarTitle(title);
    }

    @Override
    protected void findViews() {
        mToolbar = (AppToolbar) findViewById(R.id.toolbar);
        mIvHome = (ImageView) findViewById(R.id.iv_home);
        mIvRead = (ImageView) findViewById(R.id.iv_read);
        mIvMusic = (ImageView) findViewById(R.id.iv_music);
        mIMovie = (ImageView) findViewById(R.id.iv_movie);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

    }

    @Override
    protected void initViewData() {
        mHomeFragment = new HomeFragment();
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.fl_fragment, mHomeFragment);
        transaction.show(mHomeFragment);
        transaction.commit();
        //onclick
        mIvHome.setOnClickListener(this);
        mIvRead.setOnClickListener(this);
        mIvMusic.setOnClickListener(this);
        mIMovie.setOnClickListener(this);
        //init toolbar
        mToolbar.setToolbarTitle(getString(R.string.app_name));
        mToolbar.setLBtnDrawable(R.drawable.user);
        mToolbar.setLBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(mNavigationView);
            }
        });
        mToolbar.setRRDrawable(R.drawable.search);
        mToolbar.setRRClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.showToast("搜索");
            }
        });
        mToolbar.setTranslationY(-DensityUtil.dip2px(50));
        mToolbar.setAlpha(0);
        setSupportActionBar(mToolbar);
    }

    @Override
    public void onClick(View view) {
        resetTab();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (view.getId()) {
            case R.id.iv_home:
                mIvHome.setImageResource(R.drawable.tab_home_checked);
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.fl_fragment, mHomeFragment);
                }
                transaction.show(mHomeFragment);
                break;
            case R.id.iv_read:
                mToolbar.setToolbarTitle(getString(R.string.one_read));
                mIvRead.setImageResource(R.drawable.tab_read_checked);
                if (mReadFragment == null) {
                    mReadFragment = new ListFragment(ListFragment.TYPE_READ);
                    transaction.add(R.id.fl_fragment, mReadFragment);
                }
                transaction.show(mReadFragment);
                showToolbar();
                break;
            case R.id.iv_music:
                mToolbar.setToolbarTitle(getString(R.string.one_music));
                mIvMusic.setImageResource(R.drawable.tab_music_checked);
                if (mMusicFragment == null) {
                    mMusicFragment = new ListFragment(ListFragment.TYPE_MUSIC);
                    transaction.add(R.id.fl_fragment, mMusicFragment);
                }
                transaction.show(mMusicFragment);
                showToolbar();
                break;
            case R.id.iv_movie:
                mToolbar.setToolbarTitle(getString(R.string.one_movie));
                mIMovie.setImageResource(R.drawable.tab_movie_checked);
                if (mMovieFragment == null) {
                    mMovieFragment = new ListFragment(ListFragment.TYPE_MOVIE);
                    transaction.add(R.id.fl_fragment, mMovieFragment);
                }
                transaction.show(mMovieFragment);
                showToolbar();
                break;
        }
        transaction.commit();
    }

    public void hideToolbar() {
        ObjectAnimator.ofFloat(mToolbar, "translationY",
                -DensityUtil.dip2px(50)).setDuration(300).start();
        ObjectAnimator.ofFloat(mToolbar, "alpha", 0).setDuration(300).start();
    }

    public void showToolbar() {
        ObjectAnimator.ofFloat(mToolbar, "translationY", 0)
                .setDuration(300).start();
        ObjectAnimator.ofFloat(mToolbar, "alpha", 1).setDuration(300).start();
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

    /**
     * 重置所有tab状态为未选中
     */
    private void resetTab() {
        mIvHome.setImageResource(R.drawable.tab_home_unchecked);
        mIvRead.setImageResource(R.drawable.tab_read_unchecked);
        mIvMusic.setImageResource(R.drawable.tab_music_unchecked);
        mIMovie.setImageResource(R.drawable.tab_movie_unchecked);
    }

    public List<String> getListId() {
        return getIntent().getStringArrayListExtra(Constant.INTENT_KEY_LIST_ID);
    }

    @Override
    public void onBackPressed() {
        if (mHomeFragment.currentPagePopIsShowing()) {
            mHomeFragment.dismissPop();
        } else {
            finish();
        }
    }
}
