package com.liuzh.one.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liuzh.one.R;
import com.liuzh.one.activity.MainActivity;
import com.liuzh.one.adapter.ListRVAdapter;
import com.liuzh.one.application.App;
import com.liuzh.one.bean.list.OneDay;
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
    private ImageView mIvLoading;
    private LinearLayoutManager mRvLayoutManager;

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
            mRootView = inflater.inflate(R.layout.fragment_content, null);
            initView();
            initData();
        }
        return mRootView;
    }


    /**
     * find view
     */
    private void initView() {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        mIvLoading = (ImageView) mRootView.findViewById(R.id.iv_loading);
    }

    /**
     * init view data
     */
    private void initData() {
        mIvLoading.setVisibility(View.VISIBLE);
        ((AnimationDrawable) mIvLoading.getDrawable()).start();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                changeToolbarVisibility();
            }
        });
        DividerItemDecoration decoration = new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
    }


    //控制toolbar的显示隐藏
    public void changeToolbarVisibility() {
        int pos = mRvLayoutManager.findFirstVisibleItemPosition();
        float firstViewY = mRecyclerView.getChildAt(0).getY();
        if (pos == 0 && firstViewY == 0f) {
            ((MainActivity) getActivity()).hideToolbar();
        } else {
            ((MainActivity) getActivity()).showToolbar();
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
                        mRecyclerView.setAdapter(new ListRVAdapter(
                                getActivity(), response.body().data));
                        mIvLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<OneDay> call, Throwable t) {
                        App.showToast("失败，再次尝试");
                        fetchOneList();
                    }
                });
    }

}
