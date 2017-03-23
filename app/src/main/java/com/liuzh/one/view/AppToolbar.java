package com.liuzh.one.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuzh.one.R;

/**
 * Created by 刘晓彬 on 2017/3/23.
 */

public class AppToolbar extends Toolbar {

    private ImageView iv_toolbar_left;
    private TextView tv_toolbar_title;
    private ImageView iv_toolbar_right_l;
    private ImageView iv_toolbar_right_r;

    public AppToolbar(Context context) {
        this(context, null, 0);
    }

    public AppToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.layout_toolbar_content, this);
        iv_toolbar_left = (ImageView) view.findViewById(R.id.iv_toolbar_left);
        tv_toolbar_title = (TextView) view.findViewById(R.id.tv_toolbar_title);
        iv_toolbar_right_l = (ImageView) view.findViewById(R.id.iv_toolbar_right_l);
        iv_toolbar_right_r = (ImageView) findViewById(R.id.iv_toolbar_right_r);
        setBackgroundColor(getResources().getColor(R.color.toolbar));
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        setPadding(0, 0, 0, 0);
    }

    public void setToolbarTitle(String title) {
        tv_toolbar_title.setText(title);
    }


    public void setLeftDrawable(Drawable drawable) {
        iv_toolbar_left.setImageDrawable(drawable);
    }

    public void setLeftClickListener(OnClickListener listener) {
        iv_toolbar_left.setOnClickListener(listener);
    }


    public void setRightLDrawable(Drawable drawable) {
        iv_toolbar_right_l.setImageDrawable(drawable);
    }

    public void setRightLClickListener(OnClickListener listener) {
        iv_toolbar_right_l.setOnClickListener(listener);
    }


    public void setRightRDrawable(Drawable drawable) {
        iv_toolbar_right_r.setImageDrawable(drawable);
    }

    public void setRightRClickListener(OnClickListener listener) {
        iv_toolbar_right_r.setOnClickListener(listener);
    }
}
