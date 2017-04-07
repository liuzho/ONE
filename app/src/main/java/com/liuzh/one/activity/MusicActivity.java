package com.liuzh.one.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.liuzh.one.R;
import com.liuzh.one.adapter.CommentRvAdapter;
import com.liuzh.one.application.App;
import com.liuzh.one.bean.Tag;
import com.liuzh.one.bean.comment.Comment;
import com.liuzh.one.bean.comment.CommentData;
import com.liuzh.one.bean.music.Music;
import com.liuzh.one.bean.music.MusicData;
import com.liuzh.one.utils.CircleTransform;
import com.liuzh.one.utils.Constant;
import com.liuzh.one.utils.HtmlFmtUtil;
import com.liuzh.one.utils.RetrofitUtil;
import com.liuzh.one.view.AppToolbar;
import com.liuzh.one.view.AuthorsView;
import com.liuzh.one.view.CDView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * music activity
 * Created by 刘晓彬 on 2017/3/23.
 */

public class MusicActivity extends BaseActivity {


    private AppToolbar mToolbar;
    private CDView mCdvMusic;
    private TextView mTvMusicAuthor;
    private TextView mTvTitle;
    private TextView mTvAuthorName;
    private WebView mWvContent;
    private TextView mTvEditorInfo;
    private TextView mTvCopyright;
    private TextView mTvLikeComment;
    private AuthorsView mAvAuthors;
    private Call<Music> mMusicCall;
    private Call<Comment> mCommentCall;
    private RecyclerView mRvComments;

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, MusicActivity.class);
        intent.putExtra(Constant.INTENT_KEY_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_music;
    }

    @Override
    protected void findViews() {
        mToolbar = (AppToolbar) findViewById(R.id.toolbar);
        mCdvMusic = (CDView) findViewById(R.id.cdv_music);
        mTvMusicAuthor = (TextView) findViewById(R.id.tv_music_author);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvAuthorName = (TextView) findViewById(R.id.tv_author_name);
        mWvContent = (WebView) findViewById(R.id.webView);
        mTvEditorInfo = (TextView) findViewById(R.id.tv_info);
        mTvCopyright = (TextView) findViewById(R.id.tv_copyright);
        mTvLikeComment = (TextView) findViewById(R.id.tv_like_comment);
        mAvAuthors = (AuthorsView) findViewById(R.id.av_authors);
        mRvComments = (RecyclerView) findViewById(R.id.rv_comments);
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
        mToolbar.setRRDrawable(R.drawable.share);
        mToolbar.setRRClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.showToast("分享");
            }
        });
        setSupportActionBar(mToolbar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRvComments.setLayoutManager(layoutManager);
        mRvComments.addItemDecoration(new DividerItemDecoration(
                mContext, DividerItemDecoration.VERTICAL));
    }

    private void setData(MusicData data) {
        String toolbarTitle = getString(R.string.one_music);
        List<Tag> tags = data.tag_list;
        if (tags.size() != 0) {
            toolbarTitle = tags.get(0).title;
        }
        mToolbar.setToolbarTitle(toolbarTitle);
        Picasso.with(mContext)
                .load(data.cover)
                .transform(new CircleTransform())
                .into(mCdvMusic);
        mTvMusicAuthor.setText(" · " + data.title + " · \n" +
                data.author.user_name + " | " + data.album);
        mTvTitle.setText(data.story_title);
        mTvAuthorName.setText("文 / " + data.story_author.user_name);
        mWvContent.loadDataWithBaseURL("about:blank",
                HtmlFmtUtil.fmt(data.story), "text/html", "utf-8", null);
        mTvEditorInfo.setText(data.charge_edt + "  " + data.editor_email);
        mTvCopyright.setText(data.copyright);
        mTvLikeComment.setText(data.praisenum + " 喜欢 · " + data.commentnum + " 评论");
        mAvAuthors.setAuthors(data.author_list);
    }

    @Override
    protected void fetchData() {
        int id = getIntent().getIntExtra(Constant.INTENT_KEY_ID, -1);
        if (id == -1) {
            App.showToast("id=-1");
            return;
        }
        fetchMusic(id);
        fetchComment(id);
    }

    private void fetchComment(final int id) {
        mCommentCall = RetrofitUtil.getMusicCommentCall(id);
        mCommentCall.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                setComment(response.body().data);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                fetchComment(id);
            }
        });
    }

    public void setComment(CommentData comment) {
        mRvComments.setAdapter(new CommentRvAdapter(mContext, comment));
    }

    private void fetchMusic(final int id) {
        mMusicCall = RetrofitUtil.getMusicCall(id);
        mMusicCall.enqueue(new Callback<Music>() {
            @Override
            public void onResponse(Call<Music> call, Response<Music> response) {
                setData(response.body().data);
                hiddenLoadingView();
            }

            @Override
            public void onFailure(Call<Music> call, Throwable t) {
                App.showToast("失败-再次请求");
                fetchMusic(id);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMusicCall.cancel();
        mCommentCall.cancel();
    }


}
