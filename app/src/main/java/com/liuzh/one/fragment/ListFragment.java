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
import com.liuzh.one.adapter.ListRVAdapter;
import com.liuzh.one.application.App;
import com.liuzh.one.bean.DataList;
import com.liuzh.one.bean.list.Data;
import com.liuzh.one.utils.DensityUtil;
import com.liuzh.one.utils.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 展现内容list的fragment，可创建类型：Read、Music、movie
 * Created by 刘晓彬 on 2017/3/27.
 */

public class ListFragment extends BaseFragment {
    public static final int TYPE_READ = 0;
    public static final int TYPE_MUSIC = 1;
    public static final int TYPE_MOVIE = 2;

    private int mType;

    private RecyclerView mRecyclerView;
    private ImageView mIvLoading;

    public ListFragment() {
    }

    @SuppressLint("ValidFragment")
    public ListFragment(int type) {
        mType = type;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_content;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchData();
    }

    @Override
    protected void initData() {
        mIvLoading.setVisibility(View.VISIBLE);
        ((AnimationDrawable) mIvLoading.getDrawable()).start();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initView(View rootView) {
        rootView.setPadding(0, DensityUtil.dip2px(50), 0, 0);
        mIvLoading = (ImageView) rootView.findViewById(R.id.iv_loading);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
    }

    private void fetchData() {
        Callback<DataList> callback = new Callback<DataList>() {
            @Override
            public void onResponse(Call<DataList> call, Response<DataList> response) {
                Data data = new Data();
                data.content_list = response.body().data;
                mRecyclerView.setAdapter(new ListRVAdapter(getActivity(), data));
                mIvLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<DataList> call, Throwable t) {
                App.showToast("失败，再次链接");
                call.enqueue(this);
            }
        };

        Call<DataList> call;
        switch (mType) {
            case TYPE_READ:
                call = RetrofitUtil.getReadListCall();
                break;
            case TYPE_MUSIC:
                call = RetrofitUtil.getMusicListCall();
                break;
            case TYPE_MOVIE:
                call = RetrofitUtil.getMovieListCall();
                break;
            default:
                App.showToast("Error:ListFragment type error");
                return;
        }
        call.enqueue(callback);
    }
}