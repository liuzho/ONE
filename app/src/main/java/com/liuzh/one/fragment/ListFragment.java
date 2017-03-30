package com.liuzh.one.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
 * Created by 刘晓彬 on 2017/3/27.
 */

public class ListFragment extends Fragment {
    private static final String TAG = "ListFragment";
    public static final int TYPE_READ = 0;
    public static final int TYPE_MUSIC = 1;
    public static final int TYPE_MOVIE = 2;

    private int mType;

    private View mRootView;
    private RecyclerView mRecyclerView;
    private TextView tv_loading;

    public ListFragment() {

    }

    @SuppressLint("ValidFragment")
    public ListFragment(int type) {
        mType = type;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchData();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_home_content, null, false);
            mRootView.setPadding(0, DensityUtil.dip2px(50), 0, 0);
            initView();
            initData();
        }
        return mRootView;
    }

    private void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL));
        tv_loading.setVisibility(View.VISIBLE);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        tv_loading = (TextView) mRootView.findViewById(R.id.tv_loading);
    }

    private void fetchData() {

        Callback callback = new Callback<DataList>() {
            @Override
            public void onResponse(Call<DataList> call, Response<DataList> response) {
                Data data = new Data();
                data.content_list = response.body().data;
                mRecyclerView.setAdapter(new ListRVAdapter(
                        getActivity(), data));
                tv_loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<DataList> call, Throwable t) {
                App.showToast("失败，再次链接");
            }
        };

        switch (mType) {
            case TYPE_READ:
                RetrofitUtil.getReadListCall()
                        .enqueue(callback);
                break;
            case TYPE_MUSIC:
                RetrofitUtil.getMusicListCall()
                        .enqueue(callback);
                break;
            case TYPE_MOVIE:
                RetrofitUtil.getMovieListCall()
                        .enqueue(callback);
                break;
        }
    }


}
