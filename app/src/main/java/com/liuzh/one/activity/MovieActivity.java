package com.liuzh.one.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuzh.one.R;
import com.liuzh.one.application.App;
import com.liuzh.one.bean.movie.Data;
import com.liuzh.one.bean.movie.Movie;
import com.liuzh.one.utils.HtmlUtil;
import com.liuzh.one.utils.RetrofitUtil;
import com.liuzh.one.view.AppToolbar;
import com.liuzh.one.view.AuthorsView;
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
    private static final String TAG = "MovieActivity";
    private static final String KEY_ID = "key_id";
    private static final String KEY_LIKE_COUNT = "key_like_count";
    private static final String KEY_TITLE = "key_title";

    private AppToolbar mToolbar;
    private TextView tv_movie_name;
    private TextView tv_title;
    private TextView tv_author;
    private ImageView iv_movie_info;
    private TextView tv_info;
    private TextView tv_copyright;
    private TextView tv_like_comment;
    private WebView mWebView;
    private ViewPager mViewPager;
    private TextView tv_loading;

    public static void start(Context context, int id, int likeCount, String title) {
        Intent intent = new Intent(context, MovieActivity.class);
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_LIKE_COUNT, likeCount);
        intent.putExtra(KEY_TITLE, title);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchMovie();
        initView();
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_movie;
    }


    private void initView() {
        tv_movie_name = (TextView) findViewById(R.id.tv_movie_name);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_author = (TextView) findViewById(R.id.tv_author);
        iv_movie_info = (ImageView) findViewById(R.id.iv_movie_info);
        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_copyright = (TextView) findViewById(R.id.tv_copyright);
        tv_like_comment = (TextView) findViewById(R.id.tv_like_comment);
        mWebView = (WebView) findViewById(R.id.webView);
        mViewPager = (ViewPager) findViewById(R.id.bannerView);
        tv_loading = (TextView) findViewById(R.id.tv_loading);

        tv_loading.setVisibility(View.VISIBLE);

        mToolbar = (AppToolbar) findViewById(R.id.toolbar);
        mToolbar.setLeftDrawable(getResources().getDrawable(R.drawable.back));
        mToolbar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mToolbar.setRightRDrawable(getResources().getDrawable(R.drawable.share));
        mToolbar.setRightRClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.showToast("分享");
            }
        });
        setSupportActionBar(mToolbar);
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initData(final Data data) {
        initViewPager(data);
        if (data.tag_list.size() != 0) {
            mToolbar.setToolbarTitle(data.tag_list.get(0).title);
        } else {
            mToolbar.setToolbarTitle("一个影视");
        }
        tv_movie_name.setText("·《" + data.title + "》·");
        tv_title.setText(getIntent().getStringExtra(KEY_TITLE));
        tv_author.setText(data.share_list.wx.desc.split(" ")[0]);
        iv_movie_info.setVisibility(View.VISIBLE);
        iv_movie_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieProfileActivity.start(MovieActivity.this, data.title,
                        data.poster, data.share_list.qq.desc,
                        data.info, data.officialstory);
            }
        });
        tv_info.setText(data.charge_edt + "  " + data.editor_email);
        tv_copyright.setText("版权信息，json里面找不到...");
        tv_like_comment.setText(getIntent().getIntExtra(KEY_LIKE_COUNT, -1) +
                " 喜欢 · " + data.commentnum + " 评论");

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        mWebView.setWebViewClient(new MovieWebViewClient());
        mWebView.loadUrl(data.web_url);
    }

    /**
     * 顶部电影图片信息的可滑动pager
     *
     * @param data 数据
     */
    private void initViewPager(final Data data) {
        List<String> urls = new ArrayList<>();
        data.photo.add(0, data.detailcover);
        urls.addAll(data.photo);
        final List<ImageView> mIVList = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            ImageView imageView = new ImageView(this);
            Picasso.with(this)
                    .load(urls.get(i))
                    .resize(mViewPager.getWidth(), mViewPager.getHeight())
                    .into(imageView);
            mIVList.add(imageView);
        }

        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return mIVList.size();
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
                Log.i(TAG, "instantiateItem: " + position);
                View view = mIVList.get(position % mIVList.size());
                ViewGroup parent = (ViewGroup) view.getParent();
                //如果当前要显示的view有父布局先将父布局移除（view只能有一个父布局）
                if (parent != null) {
                    parent.removeView(view);
                }
                container.addView(view);
                return view;
            }
        });
    }

    /**
     * 用于截取获取到的html，只截取内容
     * *****************ONE这个外包团队真他妈垃圾********************
     * *****************什么JB那么长的API********************
     * *****************什么JB那么恶心人的Json,返回的信息全有问题，规范也一坨屎***********
     * *****************垃圾！一坨屎！********************
     */
    private class MovieWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.toString());
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:window.local_obj.textContent(" +
                    "document.getElementsByClassName('text-content')[0].innerHTML);");
            super.onPageFinished(view, url);
        }
    }

    private class InJavaScriptLocalObj {
        @JavascriptInterface
        public void textContent(final String html) {
            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadDataWithBaseURL("about:blank",
                            HtmlUtil.fmt(html), "text/html", "utf-8", null);
                    tv_loading.setVisibility(View.GONE);
                }
            });
        }
    }


    private void fetchMovie() {
        RetrofitUtil.getMovieCall(getIntent().getIntExtra(KEY_ID, -1))
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        initData(response.body().data);
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        App.showToast("失败，再次尝试");
                        fetchMovie();
                    }
                });
    }
}
