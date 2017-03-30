package com.liuzh.one.adapter;

import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liuzh.one.application.App;

import java.util.List;

/**
 * images view pager adapter
 * Created by 刘晓彬 on 2017/3/30.
 */

public class ViewsPagerAdapter extends PagerAdapter {

    private List<ImageView> mImageViews;

    public ViewsPagerAdapter(List<ImageView> imageViews) {
        mImageViews = imageViews;
        ImageView iv = mImageViews.get(0);
        final String videoUrl = (String) iv.getTag();
        if (!TextUtils.isEmpty(videoUrl)) {
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //// TODO: 2017/3/30 play video
                    App.showToast("播放：" + videoUrl);
                }
            });
        }
    }

    @Override
    public int getCount() {
        return mImageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//                container.removeView(mIVList.get(position % mIVList.size()));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mImageViews.get(position % mImageViews.size());
        ViewGroup parent = (ViewGroup) view.getParent();
        //如果当前要显示的view有父布局先将父布局移除（view只能有一个父布局）
        if (parent != null) {
            parent.removeView(view);
        }
        container.addView(view);
        return view;
    }
}
