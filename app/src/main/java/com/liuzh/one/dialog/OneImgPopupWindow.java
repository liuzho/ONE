package com.liuzh.one.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.liuzh.one.R;
import com.liuzh.one.utils.DensityUtil;
import com.liuzh.one.utils.FileUtil;
import com.squareup.picasso.Picasso;

/**
 * Created by 刘晓彬 on 2017/4/8.
 */

public class OneImgPopupWindow extends PopupWindow implements View.OnClickListener {

    private TextView mTvVolume;
    private TextView mTvInfo;
    private ImageView mIvImg;

    public OneImgPopupWindow(View contentView) {
        super(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mTvVolume = (TextView) contentView.findViewById(R.id.tv_volume);
        mTvInfo = (TextView) contentView.findViewById(R.id.tv_info);
        mIvImg = (ImageView) contentView.findViewById(R.id.iv_img);
        mIvImg.setOnClickListener(this);

        contentView.setOnClickListener(this);

        this.setAnimationStyle(R.style.PopupWindowAnimationRight);

    }

    public void setData(final Context context, String volume, String info, final String url) {
        mTvVolume.setText(volume);
        mTvInfo.setText(info);
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.one_placeholder)
                .into(mIvImg);
        int maxWidth = DensityUtil.getWinWidth((Activity) context) - DensityUtil.dip2px(30);
        mIvImg.setMaxWidth(maxWidth);
        mIvImg.setMinimumWidth(maxWidth);
        mIvImg.setMaxHeight(maxWidth * 5);
        mIvImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Snackbar.make(view.getRootView(), "要保存图片吗", 2000)
                        .setAction("保存", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FileUtil.savePic(context, url);
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == getContentView()) {
            dismiss();
        }
    }

}
