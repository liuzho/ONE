package com.liuzh.one.fragment;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuzh.one.R;
import com.liuzh.one.activity.MainActivity;
import com.liuzh.one.adapter.ListRVAdapter;
import com.liuzh.one.application.App;
import com.liuzh.one.bean.list.OneDay;
import com.liuzh.one.utils.DensityUtil;
import com.liuzh.one.utils.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * pager fragment
 * Created by 刘晓彬 on 2017/3/20.
 */

public class HomeContentFragment extends Fragment {

    private static final String TAG = "HomeContentFragment";
    private int mID;//fragment对应的one list的id
    private View mRootView;//布局根view
    private RecyclerView mRecyclerView;//recycler view
    private Toolbar mActivityToolbar;
    private boolean mToolbarNeedShow = false;

    public HomeContentFragment() {
    }

    @SuppressLint("ValidFragment")
    public HomeContentFragment(int id) {
        this.mID = id;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchOneList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_home_content, null);
            initView();
            initData();
        }
        return mRootView;
    }


    public boolean toolbarNeedShow() {
        return mToolbarNeedShow;
    }

    /**
     * find view
     */
    private void initView() {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
    }

    /**
     * init view data
     */
    private void initData() {
        mActivityToolbar = ((MainActivity) getActivity()).getToolbar();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                toolbarHandler(recyclerView);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                toolbarHandler(recyclerView);
            }

        });
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL));
    }


    //控制toolbar的显示隐藏
    private void toolbarHandler(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager =
                (LinearLayoutManager) recyclerView.getLayoutManager();
        int pos = layoutManager.findFirstVisibleItemPosition();
        if (pos == 0 && recyclerView.getChildAt(0).getY() == 0) {
            mToolbarNeedShow = false;
            if (!((MainActivity) getActivity()).canOperateToolbar()) {
                return;
            }
            ObjectAnimator.ofFloat(mActivityToolbar, "translationY",
                    -DensityUtil.dip2px(50)).setDuration(300).start();
            ObjectAnimator.ofFloat(mActivityToolbar, "alpha", 0).setDuration(300).start();
        } else {
            mToolbarNeedShow = true;
            if (!((MainActivity) getActivity()).canOperateToolbar()) {
                return;
            }
            ObjectAnimator.ofFloat(mActivityToolbar, "translationY", 0)
                    .setDuration(300).start();
            ObjectAnimator.ofFloat(mActivityToolbar, "alpha", 1).setDuration(300).start();
        }

    }

    /**
     * 返回RecyclerView
     * HomeFragment中用于设置view pager左右滑动时的监听，设置toolbar的显示隐藏
     *
     * @return RecyclerView
     */
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    /**
     * 获取oneList数据
     */
    private void fetchOneList() {
        RetrofitUtil.getOneListCall(mID)
                .enqueue(new Callback<OneDay>() {
                    @Override
                    public void onResponse(Call<OneDay> call, Response<OneDay> response) {
                        mRootView.findViewById(R.id.tv_loading).setVisibility(View.GONE);
                        mRecyclerView.setAdapter(new ListRVAdapter(
                                getActivity(), response.body().data));
                    }

                    @Override
                    public void onFailure(Call<OneDay> call, Throwable t) {
                        App.showToast("失败，再次尝试");
                        fetchOneList();
                    }
                });
    }

}
