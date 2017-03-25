package com.liuzh.one.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuzh.one.R;
import com.liuzh.one.bean.Author;
import com.liuzh.one.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘晓彬 on 2017/3/23.
 */

public class AuthorsView extends FrameLayout {
    private LinearLayout ll_authors;
    private List<Author> mAuthors;
    private Context mContext;

    public AuthorsView(Context context) {
        this(context, null, 0);
    }

    public AuthorsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AuthorsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.layout_detail_authors, this);
        ll_authors = (LinearLayout) findViewById(R.id.ll_authors);
        mAuthors = new ArrayList<>();
        mContext = context;
    }

    public AuthorsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    /**
     * 设置作者列表
     *
     * @param authors 作者列表
     */
    public void setAuthor(List<Author> authors) {
        this.mAuthors = authors;
        if (authors.size() == 0) {
            this.setVisibility(GONE);
            return;
        }
        for (int i = 0; i < authors.size(); i++) {
            Author author = authors.get(i);
            View authorView = View.inflate(mContext, R.layout.layout_author, ll_authors);
            Picasso.with(mContext)
                    .load(author.web_url)
                    .transform(new CircleTransform())
                    .placeholder(R.mipmap.ic_launcher)
                    .into((ImageView) authorView.findViewById(R.id.iv_head));
            ((TextView) authorView.findViewById(R.id.tv_author_name)).setText(author.user_name);
            ((TextView) authorView.findViewById(R.id.tv_profile)).setText(author.desc);
            ((TextView) authorView.findViewById(R.id.tv_wb_name)).setText(author.wb_name);
        }
    }
}
