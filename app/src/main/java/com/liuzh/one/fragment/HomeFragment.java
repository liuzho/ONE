package com.liuzh.one.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuzh.one.R;
import com.liuzh.one.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * homeFragment
 * Created by 刘晓彬 on 2017/3/16.
 */

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private View mRootView;//root view
    private ViewPager mViewPager;//viewpager
    private ArrayList<HomeContentFragment> mFragments;//all fragment
    private FragmentPagerAdapter mPagerAdapter;//adapter
    private List<String> mIDs;//one ids
    private MainActivity mMainActivity;
    private String[] mDayArr;

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
        mIDs = mMainActivity.getListId();
        //创建2个fragment，其余fragment通过view pager滑动监听来动态创建
        mFragments = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int id = Integer.valueOf(mIDs.get(i));
            HomeContentFragment fragment = new HomeContentFragment(id);
            mFragments.add(fragment);
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


    /**
     * 被隐藏的时候：停止当前ContentFragment中的RecycleView的滚动（防止toolbar的显示异常）
     * 被显示的时候：设置toolbar显示状态、设置toolbar的title
     *
     * @param hidden 是否隐藏
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        int pos = mViewPager.getCurrentItem();
        RecyclerView rv = mFragments.get(pos).getRecyclerView();
        if (hidden) {
            rv.stopScroll();
        } else {
            mFragments.get(pos).changeToolbarVisibility();
            mMainActivity.setToolbarTitle(mDayArr[pos]);
        }
    }


    private void initView() {
        mViewPager = (ViewPager) mRootView.findViewById(R.id.viewPager);
    }

    private void initData() {
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
                        && position != mIDs.size() - 1) {
                    int id = Integer.valueOf(mIDs.get(position + 1));
                    HomeContentFragment fragment = new HomeContentFragment(id);
                    mFragments.add(fragment);
                    mPagerAdapter.notifyDataSetChanged();
                }
                mFragments.get(position).changeToolbarVisibility();
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