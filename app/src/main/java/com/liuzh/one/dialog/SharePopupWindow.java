package com.liuzh.one.dialog;

import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.liuzh.one.R;
import com.liuzh.one.application.App;

/**
 * Created by 刘晓彬 on 2017/4/8.
 */

public class SharePopupWindow extends PopupWindow implements View.OnClickListener {

    private View mContentView;
    private View mShareWeichatFriends;
    private View mShareWeichat;
    private View mShareQQ;
    private View mShareWeibo;
    private View mShareUrl;

    public SharePopupWindow(View contentView) {
        super(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView = contentView;
        mShareWeichatFriends = mContentView.findViewById(R.id.iv_share_friends);
        mShareWeichat = mContentView.findViewById(R.id.iv_share_weichat);
        mShareQQ = mContentView.findViewById(R.id.iv_share_qq);
        mShareWeibo = mContentView.findViewById(R.id.iv_share_weibo);
        mShareUrl = mContentView.findViewById(R.id.iv_share_url);

        mShareWeichatFriends.setOnClickListener(this);
        mShareWeichat.setOnClickListener(this);
        mShareQQ.setOnClickListener(this);
        mShareWeibo.setOnClickListener(this);
        mShareUrl.setOnClickListener(this);
        mContentView.setOnClickListener(this);


        this.setAnimationStyle(R.style.PopupWindowAnimationBottom);
    }


    @Override
    public void onClick(View view) {
        if (view != mContentView) {
            App.showToast("分享");
        }
        dismiss();
    }
}
