package com.liuzh.one.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuzh.one.R;
import com.liuzh.one.activity.MovieActivity;
import com.liuzh.one.activity.MusicActivity;
import com.liuzh.one.activity.QuestionActivity;
import com.liuzh.one.activity.ReadActivity;
import com.liuzh.one.application.App;
import com.liuzh.one.bean.ContentList;
import com.liuzh.one.bean.list.Data;
import com.liuzh.one.bean.list.Weather;
import com.liuzh.one.dialog.OneImgPopupWindow;
import com.liuzh.one.utils.Constant;
import com.liuzh.one.utils.DateUtil;
import com.liuzh.one.utils.DensityUtil;
import com.liuzh.one.view.CDView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 主页one list的RV的适配器
 * Created by 刘晓彬 on 2017/3/20.
 */

public class ListRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ListRvAdapter";
    private Context mContext;
    private int mWinWidth;
    private Weather mWeather;
    private String mDate;
    private List<ContentList> mContentList;
    private OneImgPopupWindow mPopupWindow;

    public ListRvAdapter(Context context, Data data) {
        mContext = context;
        mDate = data.date;
        mWeather = data.weather;
        mContentList = data.content_list;
        mWinWidth = DensityUtil.getWinWidth((Activity) context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(App.getContext());
        switch (viewType) {
            case Constant.ITEM_TYPE_DAY_ONE:
                holder = new OneDayHolder(inflater.inflate(
                        R.layout.item_rv_day_one, parent, false));
                break;
            case Constant.ITEM_TYPE_MUSIC:
                holder = new MusicHolder(inflater.inflate(
                        R.layout.item_rv_music, parent, false));
                break;
            case Constant.ITEM_TYPE_MOVIE:
                holder = new MovieHolder(inflater.inflate(
                        R.layout.item_rv_movie, parent, false));
                break;
            case Constant.ITEM_TYPE_READ_CARTOON:
            case Constant.ITEM_TYPE_SERIAL:
            case Constant.ITEM_TYPE_QUESTION:
                holder = new ReadHolder(inflater.inflate(
                        R.layout.item_rv_read, parent, false));
                break;
            default:
                holder = new ReadHolder(inflater.inflate(
                        R.layout.item_rv_read, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContentList content = mContentList.get(position);
        if (holder instanceof OneDayHolder) {
            initOneDayHolder(holder, content);
        } else if (holder instanceof MusicHolder) {
            initMusicHolder(holder, content);
        } else if (holder instanceof MovieHolder) {
            initMovieHolder(holder, content);
        } else {
            initReadHolder(holder, content);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return Integer.valueOf(mContentList.get(position).content_type);
    }

    @Override
    public int getItemCount() {
        return mContentList.size();
    }

    /**
     * "one每天"item的holder
     */
    private class OneDayHolder extends BaseHolder {
        TextView tv_date;
        TextView tv_climate;
        TextView tv_volume;
        TextView tv_title_picInfo;
        TextView tv_words_info;

        OneDayHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_climate = (TextView) itemView.findViewById(R.id.tv_climate_city);
            tv_volume = (TextView) itemView.findViewById(R.id.tv_volume);
            tv_title_picInfo = (TextView) itemView.findViewById(R.id.tv_title_picInfo);
            tv_forward = (TextView) itemView.findViewById(R.id.tv_forward);
            tv_words_info = (TextView) itemView.findViewById(R.id.tv_words_info);
            tv_lick_count = (TextView) itemView.findViewById(R.id.tv_lick_count);
        }
    }

    public boolean popIsShowing() {
        return mPopupWindow.isShowing();
    }

    public void dismissPop() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    private void initOneDayHolder(final RecyclerView.ViewHolder holder, final ContentList content) {
        ((OneDayHolder) holder).tv_date.setText(
                mDate.replace("-", "／").split(" ")[0]);
        ((OneDayHolder) holder).tv_climate.setText(
                mWeather.climate + "，" + mWeather.city_name);
        ((OneDayHolder) holder).tv_volume.setText(content.volume);
        ((OneDayHolder) holder).tv_title_picInfo.setText(content.title +
                "｜" + content.pic_info);
        ((OneDayHolder) holder).tv_forward.setText(content.forward);
        ((OneDayHolder) holder).tv_words_info.setText(content.words_info);
        ((OneDayHolder) holder).tv_lick_count.setText(content.like_count.toString());
        Picasso.with(App.getContext())
                .load(content.img_url)
                .placeholder(R.drawable.placeholder)
                .into(((OneDayHolder) holder).iv_img);
        ((OneDayHolder) holder).iv_img.setMaxWidth(mWinWidth);
        ((OneDayHolder) holder).iv_img.setMinimumWidth(mWinWidth);
        ((OneDayHolder) holder).iv_img.setMaxHeight(mWinWidth * 5);
        ((OneDayHolder) holder).iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPopupWindow == null) {
                    View contentView = LayoutInflater.from(mContext)
                            .inflate(R.layout.layout_popup_one_img, null);
                    mPopupWindow = new OneImgPopupWindow(contentView);
                    mPopupWindow.setData(mContext, content.volume,
                            content.title + "｜" + content.pic_info, content.img_url);

                }
                mPopupWindow.showAtLocation(((OneDayHolder) holder).itemView.getRootView(),
                        Gravity.CENTER, 0, 0);
            }
        });
    }

    /**
     * music item的holder
     */
    private class MusicHolder extends BaseHolder {

        CDView cdv_music;
        TextView tv_info;

        MusicHolder(View itemView) {
            super(itemView);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_title = (TextView) itemView.findViewById(R.id.tv_toolbar_title);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author);
            cdv_music = (CDView) itemView.findViewById(R.id.cdv_music);
            tv_info = (TextView) itemView.findViewById(R.id.tv_info);
            tv_forward = (TextView) itemView.findViewById(R.id.tv_forward);
            tv_post_time = (TextView) itemView.findViewById(R.id.tv_post_time);
            tv_lick_count = (TextView) itemView.findViewById(R.id.tv_lick_count);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = getId();
                    MusicActivity.start(mContext, id);
                }
            });
        }
    }

    private void initMusicHolder(RecyclerView.ViewHolder holder, ContentList content) {
        if (content.tag_list.size() != 0) {
            ((MusicHolder) holder).tv_type.setText("- " + content.tag_list.get(0).title + " -");
        } else {
            ((MusicHolder) holder).tv_type.setText("- 音乐 -");
        }
        ((MusicHolder) holder).tv_title.setText(content.title);
        ((MusicHolder) holder).tv_author.setText("文／" + content.author.user_name);
        ((MusicHolder) holder).tv_info.setText(content.music_name + " · " +
                content.audio_author + " | " + content.audio_album);
        ((MusicHolder) holder).tv_forward.setText(content.forward);
        int post_time = Integer.valueOf(DateUtil.getFmtH()) - 6;
        ((MusicHolder) holder).tv_post_time.setText(post_time + "小时前");
        ((MusicHolder) holder).tv_lick_count.setText(content.like_count.toString());
        Picasso.with(App.getContext())
                .load(content.img_url)
                .into(((MusicHolder) holder).cdv_music);
        ((MusicHolder) holder).cdv_music.setOnPlayListener(new CDView.OnPlayListener() {
            @Override
            public void onPlay() {
                App.showToast("播放音乐");
            }
        });
        ((MusicHolder) holder).cdv_music.setOnStopListener(new CDView.OnStopListener() {
            @Override
            public void onStop() {
                App.showToast("停止音乐");
            }
        });

    }

    private class MovieHolder extends BaseHolder {
        MovieHolder(View itemView) {
            super(itemView);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_title = (TextView) itemView.findViewById(R.id.tv_toolbar_title);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_forward = (TextView) itemView.findViewById(R.id.tv_forward);
            tv_subtitle = (TextView) itemView.findViewById(R.id.tv_subtitle);
            tv_post_time = (TextView) itemView.findViewById(R.id.tv_post_time);
            tv_lick_count = (TextView) itemView.findViewById(R.id.tv_lick_count);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = getId();
                    int like_count = getLikeCount();
                    String title = getTitle();
                    MovieActivity.start(mContext, id, like_count, title);
                }
            });
        }
    }

    private void initMovieHolder(RecyclerView.ViewHolder holder, ContentList content) {
        if (content.tag_list.size() != 0) {
            ((MovieHolder) holder).tv_type.setText("- " + content.tag_list.get(0).title + " -");
        } else {
            ((MovieHolder) holder).tv_type.setText("- 影视 -");
        }
        ((MovieHolder) holder).tv_title.setText(content.title);
        ((MovieHolder) holder).tv_author.setText("文／" + content.author.user_name);
        ((MovieHolder) holder).tv_forward.setText(content.forward);
        ((MovieHolder) holder).tv_subtitle.setText("－－《" + content.subtitle + "》");
        int post_time = Integer.valueOf(DateUtil.getFmtH()) - 6;
        ((MovieHolder) holder).tv_post_time.setText(post_time + "小时前");
        ((MovieHolder) holder).tv_lick_count.setText(content.like_count.toString());
        Picasso.with(App.getContext())
                .load(content.img_url)
                .placeholder(R.drawable.placeholder)
                .into(((MovieHolder) holder).iv_img);
        ((MovieHolder) holder).iv_img.setMaxWidth(mWinWidth - DensityUtil.dip2px(90));
        ((MovieHolder) holder).iv_img.setMinimumWidth(mWinWidth - DensityUtil.dip2px(90));
        ((MovieHolder) holder).iv_img.setMaxHeight(mWinWidth * 4);
    }

    private class ReadHolder extends BaseHolder {
        ReadHolder(View itemView) {
            super(itemView);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_title = (TextView) itemView.findViewById(R.id.tv_toolbar_title);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author);
            tv_forward = (TextView) itemView.findViewById(R.id.tv_forward);
            tv_post_time = (TextView) itemView.findViewById(R.id.tv_post_time);
            tv_lick_count = (TextView) itemView.findViewById(R.id.tv_lick_count);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = getId();
                    int type = getType();
                    switch (type) {
                        case Constant.ITEM_TYPE_READ_CARTOON:
                            ReadActivity.start(mContext, id);
                            break;
                        case Constant.ITEM_TYPE_SERIAL:
                            break;
                        case Constant.ITEM_TYPE_QUESTION:
                            QuestionActivity.start(mContext, id);
                            break;
                    }
                }
            });
        }
    }

    private void initReadHolder(RecyclerView.ViewHolder holder, ContentList content) {
        String type = "";
        switch (Integer.valueOf(content.content_type)) {
            case Constant.ITEM_TYPE_READ_CARTOON:
                type = "阅读";
                break;
            case Constant.ITEM_TYPE_SERIAL:
                type = "连载";
                break;
            case Constant.ITEM_TYPE_QUESTION:
                type = "问答";
                break;
        }
        if (content.tag_list.size() != 0) {
            type = content.tag_list.get(0).title;
        }
        ((ReadHolder) holder).tv_type.setText("- " + type + " -");
        ((ReadHolder) holder).tv_title.setText(content.title);
        ((ReadHolder) holder).tv_author.setText("文／" + content.author.user_name);
        ((ReadHolder) holder).tv_forward.setText(content.forward);
        int post_time = Integer.valueOf(DateUtil.getFmtH()) - 6;
        ((ReadHolder) holder).tv_post_time.setText(post_time + "小时前");
        ((ReadHolder) holder).tv_lick_count.setText(content.like_count.toString());
        Picasso.with(App.getContext())
                .load(content.img_url)
                .resize(300, 180)
                .placeholder(R.drawable.placeholder)
                .into(((ReadHolder) holder).iv_img);
        ((ReadHolder) holder).iv_img.setMaxWidth(mWinWidth - DensityUtil.dip2px(90));
        ((ReadHolder) holder).iv_img.setMinimumWidth(mWinWidth - DensityUtil.dip2px(90));
        ((ReadHolder) holder).iv_img.setMaxHeight(mWinWidth * 5);
    }


    private class BaseHolder extends RecyclerView.ViewHolder {
        TextView tv_type;
        TextView tv_title;
        TextView tv_author;
        ImageView iv_img;
        TextView tv_forward;
        TextView tv_post_time;
        TextView tv_lick_count;
        TextView tv_subtitle;
        View itemView;

        BaseHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        int getId() {
            return Integer.valueOf(mContentList.get(getLayoutPosition()).item_id);
        }

        int getType() {
            return Integer.valueOf(mContentList.get(getLayoutPosition()).content_type);
        }

        int getLikeCount() {
            return mContentList.get(getLayoutPosition()).like_count;
        }

        String getTitle() {
            return mContentList.get(getLayoutPosition()).title;
        }
    }
}
