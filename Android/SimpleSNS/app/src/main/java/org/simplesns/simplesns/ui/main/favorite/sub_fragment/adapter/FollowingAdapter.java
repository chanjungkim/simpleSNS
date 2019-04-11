package org.simplesns.simplesns.ui.main.favorite.sub_fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.simplesns.simplesns.BuildConfig;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.ui.main.MainActivity;
import org.simplesns.simplesns.ui.main.favorite.sub_fragment.model.FollowingActivityItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

import static org.simplesns.simplesns.lib.remote.RemoteService.BASE_URL;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.FollowingHolder> {
    private static final int VIEW_START_FOLLOW = 0;
    private static final int VIEW_COMMENT = 1;
    private static final int VIEW_REACTION = 2;

    ArrayList<FollowingActivityItem> datalist;
    Context context;

    public FollowingAdapter(Context context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        this.context = context;
    }

    public void setDatalist(java.util.ArrayList<FollowingActivityItem> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public FollowingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case VIEW_FEED:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_feed_activity, parent, false);
                break;
            case VIEW_COMMENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_comment_activity, parent, false);
                break;
            case VIEW_REACTION:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_reaction_activity, parent, false);
                break;
        }
        return new FollowingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowingHolder holder, int position) {
        FollowingActivityItem item = datalist.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_COMMENT:
                Timber.d(item.toString());
                Glide.with((MainActivity) context).load(BASE_URL + item.getPhoto_url()).into(holder.ivUserPhoto);
                holder.tvLinkyfy.setText(item.getUsername() + "님께서 " + item.getWhom() + "의 글에 댓글을 달았습니다.: " + item.getContent() + " " + timeFromNow(item.getReg_time()));
                break;
            case VIEW_REACTION:
                Timber.d(item.toString());
                Glide.with((MainActivity) context).load(BASE_URL + item.getPhoto_url()).into(holder.ivUserPhoto);
                holder.tvLinkyfy.setText(item.getUsername() + "님께서 " + item.getWhom() + "의 게시물을 좋아합니다.: " + timeFromNow(item.getReg_time()));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public String timeFromNow(String reg_time) {
        SimpleDateFormat mysqlTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Date d1 = new Date();

        Date d2 = null;
        try {
            d2 = mysqlTime.parse(reg_time);
        } catch (ParseException p) {
            p.printStackTrace();
        }

        String result = null;
        long diff = (System.currentTimeMillis() - d2.getTime()) / 1000;
        if (diff < (3600 * 24)) { // 24시간 내에 발생
            if (diff >= 3600) { // 1시간 이상에 발생
                diff = diff / (60 * 60); // 시간으로 계산
                result = diff + "시간";
            } else if (diff >= 60) { // 1분 이상에 발생
                diff = diff / 60; // 분으로 환산
                result = diff + "분";
            }
        } else if (diff < (3600 * 24 * 7)) { // 1주 내에 발생
            diff = diff / (3600 * 24); // 일로 환산
            result = diff + "일";
        } else if (diff < (3600 * 24 * 29)) {// 29일 내에 발생( 대략 한 달 )
            diff = diff / (3600 * 24 * 7); // 일로 환산
            result = diff + "주";
        } else if (diff < (3600 * 24 * 365)) { // 1년 내에 발생
            diff = diff / (3600 * 24 * 29); // 일로 환산
            result = diff + "달";
        } else {
            diff = diff / (3600 * 24 * 365); // 일로 환산
            result = diff + "년";
        }

        return result;
    }

    @Override
    public int getItemViewType(int position) {
        Timber.d("getItemViewType()= position: " + position);

        switch (datalist.get(position).getType()) {
            case "follow":
                return VIEW_START_FOLLOW;
            case "comment":
                return VIEW_COMMENT;
            case "reaction":
                return VIEW_REACTION;
        }

        return -1;
    }

    class FollowingHolder extends RecyclerView.ViewHolder {
        ImageView ivUserPhoto;
        TextView tvLinkyfy;
        ImageView ivFeed;

        public FollowingHolder(@NonNull View itemView) {
            super(itemView);
            ivUserPhoto = itemView.findViewById(R.id.iv_user_photo);
            tvLinkyfy = itemView.findViewById(R.id.tv_linkify);
            ivFeed = itemView.findViewById(R.id.iv_feed);
        }
    }
}
