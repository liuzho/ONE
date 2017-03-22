package com.liuzh.one.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuzh.one.R;
import com.liuzh.one.activity.MainActivity;
import com.liuzh.one.adapter.HomePagerAdapter;
import com.liuzh.one.utils.DensityUtil;

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
    private Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragments();
    }

    /**
     * init fragments
     */
    private void initFragments() {
        //从依附的activity的intent中取一天的list id
        mIDs = getActivity().getIntent()
                .getStringArrayListExtra(MainActivity.INTENT_KEY_LIST_ID);
        //创建所有的fragment
        mFragments = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            mFragments.add(new ListFragment(Integer.valueOf(mIDs.get(i))));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_main_tab, null);
            initView();
            initData();
        }
        return mRootView;
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
        toolbar = ((MainActivity) getActivity()).getToolbar();
        mPagerAdapter = new HomePagerAdapter(getChildFragmentManager(), mFragments);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == mFragments.size() - 1
                        && position != mIDs.size() - 1) {
                    mFragments.add(new ListFragment(
                            Integer.valueOf(mIDs.get(position + 1))));
                    mPagerAdapter.notifyDataSetChanged();
                }
                RecyclerView recyclerView = ((ListFragment) mFragments
                        .get(position)).getRecyclerView();
                LinearLayoutManager layoutManager =
                        (LinearLayoutManager) recyclerView.getLayoutManager();
                int pos = layoutManager.findFirstVisibleItemPosition();
                if (pos == 0 && recyclerView.getChildAt(0).getY() == 0) {
                    ObjectAnimator.ofFloat(toolbar, "translationY",
                            -DensityUtil.dip2px(50)).setDuration(300).start();
                    ObjectAnimator.ofFloat(toolbar, "alpha", 0).setDuration(300).start();
                } else {
                    ObjectAnimator.ofFloat(toolbar, "translationY", 0)
                            .setDuration(300).start();
                    ObjectAnimator.ofFloat(toolbar, "alpha", 1).setDuration(300).start();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i(TAG, "onPageScrollStateChanged: " + state);
            }
        });
    }


    /**
     * viewpager切换动画
     */
    private class DepthPageTransformer implements ViewPager.PageTransformer {

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);
            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);
                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

}
