package com.liuzh.one.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.liuzh.one.R;
import com.liuzh.one.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * homeFragment
 * Created by 刘晓彬 on 2017/3/16.
 */

public class HomeFragment extends BaseFragment {
    private ViewPager mViewPager;//viewpager
    private ArrayList<OneContentFragment> mFragments;//all fragment
    private FragmentPagerAdapter mPagerAdapter;//adapter
    private List<String> mIdList;//one ids
    private MainActivity mMainActivity;
    private String[] mDayArr;

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivity = (MainActivity) getActivity();
        mDayArr = getResources().getStringArray(R.array.day);
        initFragments();
    }

    /**
     * init fragments
     */
    private void initFragments() {
        //从依附的activity的intent中取一天的list id
        mIdList = mMainActivity.getListId();
        //创建2个fragment，其余fragment通过view pager滑动监听来动态创建
        mFragments = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int id = Integer.valueOf(mIdList.get(i));
            OneContentFragment fragment = new OneContentFragment(id);
            mFragments.add(fragment);
        }
    }

    /**
     * 当前fragment:
     * 被隐藏的时候：停止当前ContentFragment中的RecycleView的滚动（防止toolbar的显示异常）
     * 被显示的时候：设置toolbar显示状态、设置toolbar的title
     *
     * @param hidden 是否隐藏
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        int pos = mViewPager.getCurrentItem();
        if (hidden) {
            mFragments.get(pos).stopRvScroll();
        } else {
            mFragments.get(pos).controlToolbarVisibility();
            mMainActivity.setToolbarTitle(mDayArr[pos]);
        }
    }

    @Override
    protected void initView(View rootView) {
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
    }

    @Override
    protected void initData() {
        mPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }
        };
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
                mMainActivity.setToolbarTitle(mDayArr[position]);
                //如果当前页是最后一页则新建下一页的fragment
                if (position == mFragments.size() - 1
                        && position != mIdList.size() - 1) {
                    int id = Integer.valueOf(mIdList.get(position + 1));
                    OneContentFragment fragment = new OneContentFragment(id);
                    mFragments.add(fragment);
                    mPagerAdapter.notifyDataSetChanged();
                }
                mFragments.get(position).controlToolbarVisibility();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
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