package com.liuzh.one.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuzh.one.R;
import com.liuzh.one.adapter.ViewsPagerAdapter;
import com.liuzh.one.application.App;
import com.liuzh.one.bean.Tag;
import com.liuzh.one.bean.movie.Movie;
import com.liuzh.one.bean.movie.MovieData;
import com.liuzh.one.utils.Constant;
import com.liuzh.one.utils.DensityUtil;
import com.liuzh.one.utils.HtmlUtil;
import com.liuzh.one.utils.RetrofitUtil;
import com.liuzh.one.view.AppToolbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 电影详情的API返回的JSON有很多问题，JSON中没有内容的返回值，没有评论的ID
 * 目前我通过webView访问网页版获取到html截取内容显示
 * 其余如：作者、评论、版权信息等，无法获取到数据无法展示
 * <p>
 * ONE的外包团队真垃圾，webApp还用的是表格布局
 * <p>
 * Created by 刘晓彬 on 2017/3/23.
 */

public class MovieActivity extends BaseActivity {

    private AppToolbar mToolbar;
    private TextView mTvMovieName;
    private TextView mTvTitle;
    private TextView mTvAuthor;
    private ImageView mIvMovieInfoBtn;
    private TextView mTvEditorInfo;
    private TextView mTvCopyright;
    private TextView mTvLikeComment;
    private WebView mWvContent;
    private ViewPager mVpMoveImgs;
    private Call<Movie> mMovieCall;

    public static void start(Context context, int id, int likeCount, String title) {
        Intent intent = new Intent(context, MovieActivity.class);
        intent.putExtra(Constant.INTENT_KEY_ID, id);
        intent.putExtra(Constant.INTENT_KEY_LIKE_COUNT, likeCount);
        intent.putExtra(Constant.INTENT_KEY_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_movie;
    }

    @Override
    protected void findViews() {
        findLoadingView(R.id.tv_loading);
        mTvMovieName = (TextView) findViewById(R.id.tv_movie_name);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvAuthor = (TextView) findViewById(R.id.tv_author);
        mIvMovieInfoBtn = (ImageView) findViewById(R.id.iv_movie_info);
        mTvEditorInfo = (TextView) findViewById(R.id.tv_info);
        mTvCopyright = (TextView) findViewById(R.id.tv_copyright);
        mTvLikeComment = (TextView) findViewById(R.id.tv_like_comment);
        mWvContent = (WebView) findViewById(R.id.webView);
        mVpMoveImgs = (ViewPager) findViewById(R.id.bannerView);
        mToolbar = (AppToolbar) findViewById(R.id.toolbar);
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void initViewData() {
        showLoadingView();
        // init toolbar
        mToolbar.setLBtnDrawable(R.drawable.back);
        mToolbar.setLBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mToolbar.setRRDrawable(R.drawable.share);
        mToolbar.setRRClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.showToast("分享");
            }
        });
        setSupportActionBar(mToolbar);
        String title = getIntent().getStringExtra(Constant.INTENT_KEY_TITLE);
        mTvTitle.setText(title);
        mIvMovieInfoBtn.setVisibility(View.VISIBLE);
        mTvCopyright.setText("版权信息，json内没有...");
        mWvContent.getSettings().setJavaScriptEnabled(true);
        mWvContent.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        mWvContent.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:window.local_obj.textContent(" +
                        "document.getElementsByClassName('text-content')[0].innerHTML);");
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    protected void fetchData() {
        int id = getIntent().getIntExtra(Constant.INTENT_KEY_ID, -1);
        if (id == -1) {
            App.showToast("id=-1");
            return;
        }
        mMovieCall = RetrofitUtil.getMovieCall(id);
        mMovieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                setData(response.body().data);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                App.showToast("失败，再次尝试");
                fetchData();
            }
        });
    }

    private void setData(final MovieData data) {
        initViewPager(data);
        List<Tag> tags = data.tag_list;
        String toolbarTitle = getString(R.string.one_movie);
        if (tags.size() != 0) {
            toolbarTitle = tags.get(0).title;
        }
        mToolbar.setToolbarTitle(toolbarTitle);
        mTvMovieName.setText("·《" + data.title + "》·");
        mTvAuthor.setText(data.share_list.wx.desc.split(" ")[0]);
        mIvMovieInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieProfileActivity.start(mContext, data.title,
                        data.poster, data.share_list.qq.desc,
                        data.info, data.officialstory);
            }
        });
        mTvEditorInfo.setText(data.charge_edt + "  " + data.editor_email);
        int likeCount = getIntent().getIntExtra(Constant.INTENT_KEY_LIKE_COUNT, -1);
        mTvLikeComment.setText(likeCount + " 喜欢 · " + data.commentnum + " 评论");
        mWvContent.loadUrl(data.web_url);
    }

    /**
     * 顶部电影图片信息的可滑动pager
     *
     * @param data 数据
     */
    private void initViewPager(final MovieData data) {
        List<String> urls = new ArrayList<>();
        data.photo.add(0, data.detailcover);
        urls.addAll(data.photo);
        final List<ImageView> imageViews = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            if (i == 0 && !TextUtils.isEmpty(data.video)) {
                imageView.setTag(data.video);
            }
            Picasso.with(mContext)
                    .load(urls.get(i))
                    .resize(mVpMoveImgs.getWidth(), mVpMoveImgs.getHeight())
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);
            imageViews.add(imageView);
        }
        mVpMoveImgs.setAdapter(new ViewsPagerAdapter(imageViews));
    }


    /**
     * 用于截取获取到的html，只截取内容
     * *****************ONE这个外包团队真他妈垃圾********************
     * *****************什么JB那么长的API********************
     * *****************什么JB那么恶心人的Json,返回的信息全有问题，规范也一坨屎***********
     * *****************垃圾！一坨屎！********************
     */
    private class InJavaScriptLocalObj {
        @JavascriptInterface
        public void textContent(final String html) {
            mWvContent.post(new Runnable() {
                @Override
                public void run() {
                    mWvContent.loadDataWithBaseURL("about:blank",
                            HtmlUtil.fmt(html), "text/html", "utf-8", null);
                    hiddenLoadingView();
                }
            });
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMovieCall.cancel();
    }
}
