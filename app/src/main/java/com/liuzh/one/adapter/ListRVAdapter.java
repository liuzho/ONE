package com.liuzh.one.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuzh.one.R;
import com.liuzh.one.activity.ReadActivity;
import com.liuzh.one.application.App;
import com.liuzh.one.bean.list.ContentList;
import com.liuzh.one.bean.list.Data;
import com.liuzh.one.utils.CircleTransform;
import com.liuzh.one.utils.DateUtil;
import com.liuzh.one.utils.DensityUtil;
import com.liuzh.one.view.CDView;
import com.squareup.picasso.Picasso;

/**
 * 主页one list的RV的适配器
 * Created by 刘晓彬 on 2017/3/20.
 */

public class ListRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ListRVAdapter";
    private static final int ITEM_TYPE_DAY_ONE = 0;
    private static final int ITEM_TYPE_READ = 1;
    private static final int ITEM_TYPE_MUSIC = 4;
    private static final int ITEM_TYPE_MOVIE = 5;
    private Data mData;//data
    private Context mContext;
    private int mWinWidth;

    public ListRVAdapter(Context context, Data data) {
        this.mContext = context;
        this.mData = data;
        mWinWidth = DensityUtil.getWidth((Activity) context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(App.getContext());
        switch (viewType) {
            case ITEM_TYPE_DAY_ONE:
                holder = new OneDayHolder(inflater.inflate(
                        R.layout.layout_rv_item_day_one, parent, false));
                break;
            case ITEM_TYPE_MUSIC:
                holder = new MusicHolder(inflater.inflate(
                        R.layout.layout_rv_item_music, parent, false));
                break;
            case ITEM_TYPE_MOVIE:
                holder = new MovieHolder(inflater.inflate(
                        R.layout.layout_rv_item_movie, parent, false));
                break;
            default:
                holder = new ReadHolder(inflater.inflate(
                        R.layout.layout_rv_item_read, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContentList content = mData.content_list.get(position);
        if (holder instanceof OneDayHolder) {
            initOneDayHolder(holder, content);
        } else if (holder instanceof MusicHolder) {
            initMusicHolder(holder, content);
        } else if (holder instanceof MovieHolder) {
            initMovieHolder(holder, content);
        } else {
            initHolder(holder, content);
        }

    }


    @Override
    public int getItemViewType(int position) {
        return Integer.valueOf(mData.content_list.get(position).content_type);
    }

    @Override
    public int getItemCount() {
        return mData.content_list.size();
    }

    /**
     * "one每天"item的holder
     */
    private class OneDayHolder extends RecyclerView.ViewHolder {


        ImageView iv_img;

        TextView tv_date;
        TextView tv_climate;
        TextView tv_volume;
        TextView tv_title_picInfo;
        TextView tv_forward;
        TextView tv_words_info;
        TextView tv_lick_count;

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

    /**
     * 初始one day holder数据
     *
     * @param holder  holder
     * @param content content
     */
    private void initOneDayHolder(RecyclerView.ViewHolder holder, ContentList content) {
        ((OneDayHolder) holder).tv_date.setText(mData.date.replace("-", "／").split(" ")[0]);
        ((OneDayHolder) holder).tv_climate.setText(mData.weather.climate +
                "，" + mData.weather.city_name);
        ((OneDayHolder) holder).tv_volume.setText(content.volume);
        ((OneDayHolder) holder).tv_title_picInfo.setText(content.title +
                "｜" + content.pic_info);
        ((OneDayHolder) holder).tv_forward.setText(content.forward);
        ((OneDayHolder) holder).tv_words_info.setText(content.words_info);
        ((OneDayHolder) holder).tv_lick_count.setText(content.like_count.toString());
        Picasso.with(App.getContext())
                .load(content.img_url)
                .placeholder(R.mipmap.ic_launcher)
                .into(((OneDayHolder) holder).iv_img);
        ((OneDayHolder) holder).iv_img.setMaxWidth(mWinWidth);
        ((OneDayHolder) holder).iv_img.setMinimumWidth(mWinWidth);
        ((OneDayHolder) holder).iv_img.setMaxHeight(mWinWidth * 5);
    }

    /**
     * music item的holder
     */
    private class MusicHolder extends RecyclerView.ViewHolder {

        TextView tv_type;
        TextView tv_title;
        TextView tv_author;
        CDView cdv_music;
        TextView tv_info;
        TextView tv_forward;
        TextView tv_post_time;
        TextView tv_lick_count;


        MusicHolder(View itemView) {
            super(itemView);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author);
            cdv_music = (CDView) itemView.findViewById(R.id.cdv_music);
            tv_info = (TextView) itemView.findViewById(R.id.tv_info);
            tv_forward = (TextView) itemView.findViewById(R.id.tv_forward);
            tv_post_time = (TextView) itemView.findViewById(R.id.tv_post_time);
            tv_lick_count = (TextView) itemView.findViewById(R.id.tv_lick_count);
        }
    }

    /**
     * 初始化music item holder数据
     *
     * @param holder  holder
     * @param content content
     */
    private void initMusicHolder(RecyclerView.ViewHolder holder, ContentList content) {
        ((MusicHolder) holder).tv_type.setText("- 音乐 -");
        ((MusicHolder) holder).tv_title.setText(content.title);
        ((MusicHolder) holder).tv_author.setText("文／" + content.author.user_name);
        ((MusicHolder) holder).tv_info.setText(content.music_name + " · " +
                content.audio_author + " | " + content.audio_album);
        ((MusicHolder) holder).tv_forward.setText(content.forward);
        int post_time = Integer.valueOf(DateUtil.getFormatH()) - 6;
        ((MusicHolder) holder).tv_post_time.setText(post_time + "小时前");
        ((MusicHolder) holder).tv_lick_count.setText(content.like_count.toString());
        Picasso.with(App.getContext())
                .load(content.img_url)
                .transform(new CircleTransform())
                .into(((MusicHolder) holder).cdv_music);
        ((MusicHolder) holder).cdv_music.setMaxWidth(mWinWidth);
        ((MusicHolder) holder).cdv_music.setMaxHeight(mWinWidth);
    }

    /**
     * read item holder
     */
    private class MovieHolder extends RecyclerView.ViewHolder {
        TextView tv_type;
        TextView tv_title;
        TextView tv_author;
        ImageView iv_img;
        TextView tv_forward;
        TextView tv_subtitle;
        TextView tv_post_time;
        TextView tv_lick_count;

        MovieHolder(View itemView) {
            super(itemView);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_forward = (TextView) itemView.findViewById(R.id.tv_forward);
            tv_subtitle = (TextView) itemView.findViewById(R.id.tv_subtitle);
            tv_post_time = (TextView) itemView.findViewById(R.id.tv_post_time);
            tv_lick_count = (TextView) itemView.findViewById(R.id.tv_lick_count);
        }
    }

    /**
     * 初始movie item数据
     *
     * @param holder  holder
     * @param content content
     */
    private void initMovieHolder(RecyclerView.ViewHolder holder, ContentList content) {
        ((MovieHolder) holder).tv_type.setText("- 影视 -");
        ((MovieHolder) holder).tv_title.setText(content.title);
        ((MovieHolder) holder).tv_author.setText("文／" + content.author.user_name);
        ((MovieHolder) holder).tv_forward.setText(content.forward);
        ((MovieHolder) holder).tv_subtitle.setText("－－《" + content.subtitle + "》");
        int post_time = Integer.valueOf(DateUtil.getFormatH()) - 6;
        ((MovieHolder) holder).tv_post_time.setText(post_time + "小时前");
        ((MovieHolder) holder).tv_lick_count.setText(content.like_count.toString());
        Picasso.with(App.getContext())
                .load(content.img_url)
                .placeholder(R.mipmap.ic_launcher)
                .into(((MovieHolder) holder).iv_img);
        ((MovieHolder) holder).iv_img.setMaxWidth(mWinWidth - DensityUtil.dip2px(90));
        ((MovieHolder) holder).iv_img.setMinimumWidth(mWinWidth - DensityUtil.dip2px(90));
        ((MovieHolder) holder).iv_img.setMaxHeight(mWinWidth * 4);
    }

    private class ReadHolder extends RecyclerView.ViewHolder {
        TextView tv_type;
        TextView tv_title;
        TextView tv_author;
        ImageView iv_img;
        TextView tv_forward;
        TextView tv_post_time;
        TextView tv_lick_count;

        ReadHolder(View itemView) {
            super(itemView);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author);
            tv_forward = (TextView) itemView.findViewById(R.id.tv_forward);
            tv_post_time = (TextView) itemView.findViewById(R.id.tv_post_time);
            tv_lick_count = (TextView) itemView.findViewById(R.id.tv_lick_count);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = Integer.valueOf(mData.content_list.get(getLayoutPosition()).item_id);
                    ReadActivity.start(mContext, id);
                }
            });
        }
    }

    private void initHolder(RecyclerView.ViewHolder holder, ContentList content) {
        ((ReadHolder) holder).tv_type.setText("- read type -");
        ((ReadHolder) holder).tv_title.setText(content.title);
        ((ReadHolder) holder).tv_author.setText("文／" + content.author.user_name);
        ((ReadHolder) holder).tv_forward.setText(content.forward);
        int post_time = Integer.valueOf(DateUtil.getFormatH()) - 6;
        ((ReadHolder) holder).tv_post_time.setText(post_time + "小时前");
        ((ReadHolder) holder).tv_lick_count.setText(content.like_count.toString());
        Picasso.with(App.getContext())
                .load(content.img_url)
                .resize(300, 180)
                .placeholder(R.mipmap.ic_launcher)
                .into(((ReadHolder) holder).iv_img);
        ((ReadHolder) holder).iv_img.setMaxWidth(mWinWidth - DensityUtil.dip2px(90));
        ((ReadHolder) holder).iv_img.setMinimumWidth(mWinWidth - DensityUtil.dip2px(90));
        ((ReadHolder) holder).iv_img.setMaxHeight(mWinWidth * 5);
    }
}
