package com.liuzh.one.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.liuzh.one.R;
import com.liuzh.one.activity.MainActivity;
<<<<<<< HEAD
import com.liuzh.one.adapter.ListRvAdapter;
=======
import com.liuzh.one.adapter.ListRVAdapter;
>>>>>>> b8fcdddcc226415dcb35966f3f46f9b302a5ae2e
import com.liuzh.one.application.App;
import com.liuzh.one.bean.list.OneDay;
import com.liuzh.one.utils.RetrofitUtil;
import com.liuzh.one.view.ScrollRecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * HomeFragment中viewpager控制滑动的fragment
 * Created by 刘晓彬 on 2017/3/20.
 */

public class OneContentFragment extends BaseFragment {
    private int mID;//fragment对应的one list的id
    private ScrollRecyclerView mRecyclerView;//recycler view
    private ImageView mIvLoading;
    private Call<OneDay> mCall;
    private MainActivity mMainActivity;

    public OneContentFragment() {
    }

    @SuppressLint("ValidFragment")
    public OneContentFragment(int id) {
        this.mID = id;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_content;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivity = (MainActivity) getActivity();
        fetchOneList();
    }

    @Override
    protected void initView(View rootView) {
        mRecyclerView = (ScrollRecyclerView) rootView.findViewById(R.id.recyclerView);
        mIvLoading = (ImageView) rootView.findViewById(R.id.iv_loading);
    }

    @Override
    protected void initData() {
        mIvLoading.setVisibility(View.VISIBLE);
        ((AnimationDrawable) mIvLoading.getDrawable()).start();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                controlToolbarVisibility();
            }
        });

        DividerItemDecoration decoration = new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

    }


    /**
     * 控制toolbar的显示隐藏
     * 如果recycleView滑动在顶部，则隐藏
     * 其余情况显示
     */
    public void controlToolbarVisibility() {
        boolean inTop = mRecyclerView.isInTop();
        if (inTop) {
            mMainActivity.hideToolbar();
        } else {
            mMainActivity.showToolbar();
        }
    }

    /**
     * 获取oneList数据
     */
    private void fetchOneList() {
        mCall = RetrofitUtil.getOneListCall(mID);
        mCall.enqueue(new Callback<OneDay>() {
            @Override
            public void onResponse(Call<OneDay> call, Response<OneDay> response) {
<<<<<<< HEAD
                ListRvAdapter adapter =
                        new ListRvAdapter(getActivity(), response.body().data);
=======
                ListRVAdapter adapter =
                        new ListRVAdapter(getActivity(), response.body().data);
>>>>>>> b8fcdddcc226415dcb35966f3f46f9b302a5ae2e
                mRecyclerView.setAdapter(adapter);
                mIvLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<OneDay> call, Throwable t) {
                App.showToast("失败，再次尝试");
                mCall.enqueue(this);
            }
        });
    }

    /**
     * 停止recycleView的滚动
     */
    public void stopRvScroll() {
        mRecyclerView.stopScroll();
    }

}
