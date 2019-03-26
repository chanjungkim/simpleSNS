package org.simplesns.simplesns.ui.main.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.simplesns.simplesns.BuildConfig;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.item.FeedItem;
import org.simplesns.simplesns.lib.remote.RemoteService;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.HomeViewHolder> {
    private Context context;
    private ArrayList<FeedItem> dataArrayList;

    public RecommendAdapter(Context context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        this.context = context;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend, viewGroup, false);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int i) {

        RequestOptions requestOptions = new RequestOptions()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .fitCenter();

        Glide.with(context)
                .load(RemoteService.BASE_URL+dataArrayList.get(i).getUrl())
                .apply(requestOptions)
                .centerCrop()
                .into(holder.ivFeedImg)
                .getSize((width, height) -> {
                    Timber.d("%d, %d", width, height);
//                    holder.ivFeedImg.setMinimumWidth(MyApp.getScreenWidth((MainActivity) context));
//                    holder.ivFeedImg.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
//                    holder.ivFeedImg.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                    holder.frameLayout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
//                    holder.frameLayout.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                });
    }

    @Override
    public int getItemCount() {
        if (dataArrayList == null) return 0;
        return dataArrayList.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llItem;
        ImageView ivFeedImg;
        FrameLayout frameLayout;

        HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.ll_item);
            ivFeedImg = itemView.findViewById(R.id.iv_feed_img);
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