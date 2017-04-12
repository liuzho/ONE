package com.liuzh.one.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuzh.one.R;
import com.liuzh.one.bean.Author;
import com.liuzh.one.utils.Constant;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者信息activity
 * Created by 刘晓彬 on 2017/4/6.
 */

public class AuthorActivity extends BaseActivity {

    private ImageView mIvBack;
    private CircleImageView mCivHead;
    private TextView mTvName;
    private TextView mTvSummary;
    private TextView mTvDesc;
    private TextView mTvFansNum;
    private TextView mTvTitle;


    public static void start(Context context, Author author) {
        Intent starter = new Intent(context, AuthorActivity.class);
        starter.putExtra(Constant.INTENT_KEY_AUTHOR, author);
        context.startActivity(starter);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_author;
    }

    @Override
    protected void findViews() {
        mCivHead = (CircleImageView) findViewById(R.id.iv_head);
        mTvName = (TextView) findViewById(R.id.tv_author_name);
        mTvSummary = (TextView) findViewById(R.id.tv_summary);
        mTvDesc = (TextView) findViewById(R.id.tv_desc);
        mTvFansNum = (TextView) findViewById(R.id.tv_fans_num);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
    }

    @Override
    protected void initViewData() {
        Author author = (Author) getIntent().getSerializableExtra(Constant.INTENT_KEY_AUTHOR);
        Picasso.with(mContext)
                .load(author.web_url)
                .placeholder(R.mipmap.ic_launcher)
                .into(mCivHead);
        mTvName.setText(author.user_name);
        mTvSummary.setText(author.summary);
        mTvDesc.setText(author.desc);
        mTvFansNum.setText(author.fans_total + "关注");
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTvTitle.setText(author.user_name);
        mTvTitle.setAlpha(0);
    }
}
