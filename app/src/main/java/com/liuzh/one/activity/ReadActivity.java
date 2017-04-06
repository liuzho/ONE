package com.liuzh.one.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.liuzh.one.R;
import com.liuzh.one.application.App;
import com.liuzh.one.bean.Tag;
import com.liuzh.one.bean.read.Read;
import com.liuzh.one.bean.read.ReadData;
import com.liuzh.one.utils.Constant;
import com.liuzh.one.utils.HtmlFmtUtil;
import com.liuzh.one.utils.RetrofitUtil;
import com.liuzh.one.view.AppToolbar;
import com.liuzh.one.view.AuthorsView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * read activity
 * Created by 刘晓彬 on 2017/3/22.
 */

public class ReadActivity extends BaseActivity {

    private WebView mWvContent;
    private TextView mTvTitle;
    private TextView mTvAuthor;
    private AuthorsView mAvAuthors;
    private TextView mTvEditorInfo;
    private TextView mTvCopyright;
    private TextView mTvLikeComment;
    private AppToolbar mToolbar;
    private Call<Read> mReadCall;

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, ReadActivity.class);
        intent.putExtra(Constant.INTENT_KEY_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_read;
    }

    @Override
    protected void findViews() {
        mWvContent = (WebView) findViewById(R.id.webView);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvAuthor = (TextView) findViewById(R.id.tv_author);
        mAvAuthors = (AuthorsView) findViewById(R.id.av_authors);
        mTvEditorInfo = (TextView) findViewById(R.id.tv_info);
        mTvCopyright = (TextView) findViewById(R.id.tv_copyright);
        mTvLikeComment = (TextView) findViewById(R.id.tv_like_comment);
        mToolbar = (AppToolbar) findViewById(R.id.toolbar);
    }

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
        mToolbar.setRLDrawable(R.drawable.listen);
        mToolbar.setRLClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.showToast("听阅读");
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
        // webView加载完毕后隐藏loading界面
        mWvContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hiddenLoadingView();
            }
        });
    }

    private void setData(ReadData data) {
        List<Tag> tags = data.tag_list;
        String toolbarTitle = getString(R.string.one_read);
        if (tags.size() != 0) {
            toolbarTitle = tags.get(0).title;
        }
        mToolbar.setToolbarTitle(toolbarTitle);
        mWvContent.loadDataWithBaseURL("about:blank",
                HtmlFmtUtil.fmt(data.hp_content), "text/html", "utf-8", null);
        mTvTitle.setText(data.hp_title);
        mTvAuthor.setText("文／" + data.hp_author);
        mTvEditorInfo.setText(data.hp_author_introduce + "  " + data.editor_email);
        mTvLikeComment.setText(data.praisenum + " 喜欢 · " + data.commentnum + " 评论");
        if (TextUtils.isEmpty(data.copyright)) {
            mTvCopyright.setVisibility(View.GONE);
        } else {
            mTvCopyright.setText(data.copyright);
        }
        mAvAuthors.setAuthors(data.author_list);
    }

    @Override
    protected void fetchData() {
        int id = getIntent().getIntExtra(Constant.INTENT_KEY_ID, -1);
        if (id == -1) {
            App.showToast("id=-1");
            return;
        }
        mReadCall = RetrofitUtil.getReadCall(id);
        mReadCall.enqueue(new Callback<Read>() {
            @Override
            public void onResponse(Call<Read> call, Response<Read> response) {
                setData(response.body().data);
            }

            @Override
            public void onFailure(Call<Read> call, Throwable t) {
                App.showToast("失败，再次尝试");
                //call.enqueue(this);
                fetchData();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mReadCall.cancel();
    }
}
