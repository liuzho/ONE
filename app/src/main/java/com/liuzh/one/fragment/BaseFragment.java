package com.liuzh.one.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuzh.one.utils.DensityUtil;

/**
 * base fragment
 * Created by 刘晓彬 on 2017/4/6.
 */

public abstract class BaseFragment extends Fragment {

    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getRootViewId(), null);
            initView(mRootView);
            initData();
        }
        return mRootView;
    }

    protected abstract void initData();

    protected abstract void initView(View rootView);

    protected abstract int getRootViewId();

}
