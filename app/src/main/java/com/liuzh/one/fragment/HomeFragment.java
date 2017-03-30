package com.liuzh.one.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuzh.one.R;
import com.liuzh.one.activity.MainActivity;
import com.liuzh.one.adapter.HomePagerAdapter;
import com.liuzh.one.utils.DensityUtil;
import com.liuzh.one.view.AppToolbar;

import java.util.ArrayList;

/**
 * homeFragment
 * Created by 刘晓彬 on 2017/3/16.
 */

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private View mRootView;//root view
    private ViewPager mViewPager;//viewpager
    private ArrayList<Fragment> mFragments;//all fragment
    private HomePagerAdapter mPagerAdapter;//adapter
    private ArrayList<String> mIDs;//ids
    private AppToolbar mActivityToolbar;//activity 的 toolbar，用于出现隐藏的动画

    private int mPagerPos = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragments();
        mActivityToolbar = ((MainActivity) getActivity()).getToolbar();
    }

    /**
     * init fragments
     */
    private void initFragments() {
        //从依附的activity的intent中取一天的list id
        mIDs = getActivity().getIntent()
                .getStringArrayListExtra(MainActivity.INTENT_KEY_LIST_ID);
        //创建2个fragment，其余fragment通过view pager滑动监听来动态创建
        mFragments = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            mFragments.add(new HomeContentFragment(Integer.valueOf(mIDs.get(i))));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_home, null);
            initView();
            initData();
        }
        return mRootView;
    }

    public boolean toolNeedShow() {
        return ((HomeContentFragment) mFragments
                .get(mViewPager.getCurrentItem())).toolbarNeedShow();
    }

    /**
     * find view
     */
    private void initView() {
        mViewPager = (ViewPager) mRootView.findViewById(R.id.viewPager);
    }

    /**
     * init view data
     */
    private void initData() {
        mPagerAdapter = new HomePagerAdapter(getChildFragmentManager(), mFragments);
        mViewPager.setAdapter(mPagerAdapter);
        /*设置pager切换动画后，在4.1.2有奇怪的问题
        * 滑动当前页，实际滑动的是下一页*/
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "onPageSelected: " + mActivityToolbar);
                mPagerPos = position;
                mActivityToolbar.setToolbarTitle(getResources()
                        .getStringArray(R.array.day)[position]);
                //如果当前页是最后一页则新建下一页的fragment
                if (position == mFragments.size() - 1
                        && position != mIDs.size() - 1) {
                    mFragments.add(new HomeContentFragment(
                            Integer.valueOf(mIDs.get(position + 1))));
                    mPagerAdapter.notifyDataSetChanged();
                }
                RecyclerView rv = ((HomeContentFragment) mFragments.get(position)).getRecyclerView();
                //控制toolbar的显示隐藏
                // view pager左右滑动的时候根据不同pager的位置也要控制toolbar的显示与隐藏
                LinearLayoutManager layoutManager =
                        (LinearLayoutManager) rv.getLayoutManager();
                int pos = layoutManager.findFirstVisibleItemPosition();
                if (pos == 0 && rv.getChildAt(0).getY() == 0) {
                    Log.i(TAG, "onPageSelected: toolbar2top ");
                    ObjectAnimator.ofFloat(mActivityToolbar, "translationY",
                            -DensityUtil.dip2px(50)).setDuration(300).start();
                    ObjectAnimator.ofFloat(mActivityToolbar, "alpha", 0).setDuration(300).start();
                } else {
                    Log.i(TAG, "onPageSelected: toolbar2bottom ");
                    ObjectAnimator.ofFloat(mActivityToolbar, "translationY", 0)
                            .setDuration(300).start();
                    ObjectAnimator.ofFloat(mActivityToolbar, "alpha", 1).setDuration(300).start();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public int getPagerPos() {
        return mPagerPos;
    }

    /**
     * viewpager切换动画
     * 4.1上有神奇的bug
     */
    private class DepthPageTransformer implements ViewPager.PageTransformer {

        public void transformPage(View view, float position) {
            if (position <= -1) { // [-Infinity,-1)
                view.setAlpha(0);
            } else if (position <= 0) { // [-1,0]
                view.setAlpha(1);
                view.setTranslationX(0);
            } else if (position <= 1) { // (0,1]
                view.setAlpha(1 - position);
                view.setTranslationX(view.getMeasuredWidth() * -position);
            } else { // (1,+Infinity]
                view.setAlpha(0);
            }
        }
    }
}