package com.liuzh.one.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.liuzh.one.R;
import com.squareup.picasso.Picasso;

/**
 * Created by 刘晓彬 on 2017/4/9.
 */

public class MovieProfilePopupWindow extends PopupWindow {
    private ImageView mIvPoster;
    private TextView mTvMovieName;
    private TextView mTvDesc;
    private TextView mTvInfo;
    private TextView mTvOfficialStory;
    private LinearLayout mLlContent;

    public MovieProfilePopupWindow(View contentView) {
        super(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mTvMovieName = ((TextView) contentView.findViewById(R.id.tv_movie_name));
        mIvPoster = (ImageView) contentView.findViewById(R.id.iv_poster);
        mTvDesc = ((TextView) contentView.findViewById(R.id.tv_desc));
        mTvInfo = ((TextView) contentView.findViewById(R.id.tv_info));
        mTvOfficialStory = ((TextView) contentView.findViewById(R.id.tv_official_story));
        mLlContent = (LinearLayout) contentView.findViewById(R.id.ll_content);
        this.setAnimationStyle(R.style.PopupWindowAnimationRight);
    }

    public void initData(Context context, String name, String url,
                         String desc, String info, String official_story) {
        mTvMovieName.setText(name);
        mTvDesc.setText(desc);
        mTvInfo.setText(info);
        mTvOfficialStory.setText(official_story);
        mLlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        Picasso.with(context)
                .load(url)
                .into(mIvPoster);
    }

}
