package com.liuzh.one.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.liuzh.one.R;

/**
 * Created by 刘晓彬 on 2017/3/23.
 */

public class CommentsView extends FrameLayout {
    public CommentsView(@NonNull Context context) {
        this(context, null, 0);
    }

    public CommentsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentsView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.layout_detail_comments, this);
    }

    public CommentsView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }
}
