package com.liuzh.one.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuzh.one.R;
import com.liuzh.one.activity.AuthorActivity;
import com.liuzh.one.application.App;
import com.liuzh.one.bean.Author;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者列表view
 * Created by 刘晓彬 on 2017/3/23.
 */

public class AuthorsView extends FrameLayout {
    private static final String TAG = "AuthorsView";
    private LinearLayout ll_authors;
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
        mContext = context;
    }

    /**
     * 设置作者列表
     *
     * @param authors 作者list
     */
    public void setAuthors(List<Author> authors) {
        if (authors.size() == 0) {
            this.setVisibility(GONE);
            return;
        }
        int count = 0;
        for (int i = 0; i < authors.size(); i++) {
            final Author author = authors.get(i);
            if (author.user_id.equals("0")) {
                continue;
            }
            View authorView = View.inflate(mContext, R.layout.layout_author, null);
            CircleImageView civHead = (CircleImageView) authorView.findViewById(R.id.iv_head);
            TextView tvAuthorName = (TextView) authorView.findViewById(R.id.tv_author_name);
            TextView tvAuthorSummary = (TextView) authorView.findViewById(R.id.tv_summary);
            TextView tvAuthorWbName = (TextView) authorView.findViewById(R.id.tv_wb_name);
            TextView tvFollowBtn = (TextView) authorView.findViewById(R.id.tv_follow);
            Picasso.with(mContext)
                    .load(author.web_url)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(civHead);
            tvAuthorName.setText(author.user_name);
            tvAuthorSummary.setText(author.summary);
            tvAuthorWbName.setText(author.wb_name);
            authorView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    AuthorActivity.start(mContext, author);
                }
            });
            tvFollowBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.showToast("关注");
                }
            });
            ll_authors.addView(authorView);
            count++;
        }
        if (count == 0) {
            this.setVisibility(GONE);
        }
    }
}
