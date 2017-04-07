package com.liuzh.one.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuzh.one.R;
import com.liuzh.one.bean.comment.CommentData;
import com.liuzh.one.bean.comment.Datum;
import com.liuzh.one.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 刘晓彬 on 2017/4/7.
 */

public class CommentRvAdapter extends RecyclerView.Adapter {
    private CommentData mCommentData;
    private Context mContext;

    public CommentRvAdapter(Context context, CommentData data) {
        mContext = context;
        mCommentData = data;
        boolean added = false;
        boolean hasHot = false;
        List<Datum> datums = mCommentData.data;
        for (int i = 0; i < datums.size(); i++) {
            Datum datum = datums.get(i);
            if (datum.type == 0) {
                hasHot = true;
            } else if (datum.type == 1 && !added && hasHot) {
                Datum d = new Datum();
                d.type = 3;
                datums.add(i, d);
                added = true;
            }
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder;
        switch (viewType) {
            case 3:
                holder = new DecorHolder(inflater.inflate(
                        R.layout.layout_hot_decoration, parent, false));
                break;
            default:
                holder = new CommentHolder(inflater.inflate(
                        R.layout.layout_rv_item_commet, parent, false));
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return mCommentData.data.get(position).type;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommentHolder) {
            Datum datum = mCommentData.data.get(position);
            if (!TextUtils.isEmpty(datum.user.web_url)) {
                Picasso.with(mContext)
                        .load(datum.user.web_url)
                        .transform(new CircleTransform())
                        .into(((CommentHolder) holder).ivHead);
            }
            ((CommentHolder) holder).tvUserName.setText(datum.user.user_name);
            ((CommentHolder) holder).tvTime.setText(datum.created_at);
            ((CommentHolder) holder).tvContent.setText(datum.content);
            ((CommentHolder) holder).tvLaudNum.setText(datum.praisenum + "");
            if (datum.touser != null) {
                ((CommentHolder) holder).llToUser.setVisibility(View.VISIBLE);
                ((CommentHolder) holder).tvToUserName.setText(datum.touser.user_name + "：");
                ((CommentHolder) holder).tvToUserContent.setText(datum.quote);
            } else {
                ((CommentHolder) holder).llToUser.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mCommentData.data.size();
    }


    class CommentHolder extends RecyclerView.ViewHolder {

        ImageView ivHead;
        ImageView ivComment;
        ImageView ivLaud;
        TextView tvUserName;
        TextView tvTime;
        TextView tvContent;
        TextView tvLaudNum;
        LinearLayout llToUser;
        TextView tvToUserName;
        TextView tvToUserContent;

        public CommentHolder(View itemView) {
            super(itemView);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            ivComment = (ImageView) itemView.findViewById(R.id.iv_comment);
            ivLaud = (ImageView) itemView.findViewById(R.id.iv_laud);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_username);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvLaudNum = (TextView) itemView.findViewById(R.id.tv_laud_num);
            llToUser = (LinearLayout) itemView.findViewById(R.id.ll_to_user);
            tvToUserName = (TextView) itemView.findViewById(R.id.tv_to_user_username);
            tvToUserContent = (TextView) itemView.findViewById(R.id.tv_to_user_content);
        }
    }

    class DecorHolder extends RecyclerView.ViewHolder {

        public DecorHolder(View itemView) {
            super(itemView);
        }
    }
}
