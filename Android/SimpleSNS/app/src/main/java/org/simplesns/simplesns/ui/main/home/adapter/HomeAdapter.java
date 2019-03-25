package org.simplesns.simplesns.ui.main.home.adapter;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

import android.graphics.Point;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SizeReadyCallback;

import org.simplesns.simplesns.BuildConfig;
import org.simplesns.simplesns.MyApp;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.item.FeedItem;
import org.simplesns.simplesns.ui.main.MainActivity;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private static final String TAG = HomeAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<FeedItem> dataArrayList;

    public HomeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home, viewGroup, false);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int i) {
        try {
            holder.tvUserName.setText(dataArrayList.get(i).getUser().getUsername());
        } catch (NullPointerException e) {
            Log.d(TAG, "No MemberItem FeedItem");
            e.printStackTrace();
            return;
        }

        RequestOptions requestOptions = new RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .fitCenter();

        Glide.with(context)
                .load(dataArrayList.get(i).getUser().getProfilePicture())
                .apply(requestOptions)
                .into(holder.ivHomeProfilePhoto);

        Glide.with(context)
                .load(dataArrayList.get(i).getImages().getUrl())
                .apply(requestOptions)
                .into(holder.ivHomeFeedImg)
                .getSize((width, height) -> {
                    Timber.d("%d, %d", width, height);
                    holder.ivHomeFeedImg.setMinimumWidth(MyApp.getScreenWidth((MainActivity) context));
                    holder.ivHomeFeedImg.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                    holder.ivHomeFeedImg.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    holder.frameLayout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                    holder.frameLayout.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    // 파팅 - 덜 구현...
//                    if (height > MyApp.getScreenWidth((MainActivity) context)) {
//                        Timber.d("img is bigger: " + width + "," + height);
//                        holder.frameLayout.setLayoutParams(new LinearLayout.LayoutParams(MyApp.getScreenWidth(((MainActivity) context)), MyApp.getScreenWidth(((MainActivity) context))));
//                        holder.ivHomeFeedImg.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
//                    } else {
//                        Timber.d("img is smaller: " + width + "," + height);
//                        holder.frameLayout.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                        holder.ivHomeFeedImg.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                    }
                });
    }

    @Override
    public int getItemCount() {
        if (dataArrayList == null) return 0;
        return dataArrayList.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName;
        ImageView ivHomeProfilePhoto;
        ImageView ivHomeFeedImg;
        FrameLayout frameLayout;

        HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            ivHomeProfilePhoto = itemView.findViewById(R.id.iv_home_profile_photo);
            ivHomeFeedImg = itemView.findViewById(R.id.iv_home_feed_img);
            frameLayout = itemView.findViewById(R.id.frame_imgs);
        }
    }

    public void setItemList(ArrayList<FeedItem> data) {
        this.dataArrayList = data;
        Timber.d("data: %s", data.toString());
        notifyDataSetChanged();
    }

    public void removeList() {
        dataArrayList.clear();
        notifyDataSetChanged();
    }

    public ArrayList<FeedItem> getList() {
        return dataArrayList;
    }

}