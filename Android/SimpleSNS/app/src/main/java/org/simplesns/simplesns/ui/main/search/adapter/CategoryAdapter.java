package org.simplesns.simplesns.ui.main.search.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.simplesns.simplesns.BuildConfig;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.item.FeedItem;
import org.simplesns.simplesns.lib.remote.RemoteService;
import org.simplesns.simplesns.ui.main.search.model.CategoryItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.HomeViewHolder> {
    private Context context;
    private ArrayList<CategoryItem> dataArrayList;

    public CategoryAdapter(Context context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        this.context = context;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommend_category, viewGroup, false);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int i) {

        if (i != 0) {
            holder.llUserPhotoContainer.setVisibility(View.INVISIBLE);
        } else {
            holder.llUserPhotoContainer.setVisibility(View.VISIBLE);
        }

        for(int j = 0 ; j < dataArrayList.size() ; j++){
            if (dataArrayList.get(holder.getPosition()).isSelected()) {
                holder.llBottomLine.setVisibility(View.VISIBLE);
            } else {
                holder.llBottomLine.setVisibility(View.GONE);
            }
        }

        RequestOptions requestOptions = new RequestOptions()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .fitCenter();

        Glide.with(context)
                .load(dataArrayList.get(i).getBackground())
                .apply(requestOptions)
                .centerCrop()
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        holder.rlCategoryContainer.setBackground(resource);
                    }
                });

        holder.rlCategoryContainer.setOnClickListener(v -> {
            Toast.makeText(context, holder.tvCategoryName.getText() + " selected", Toast.LENGTH_SHORT).show();
            for (int j = 0; j < dataArrayList.size(); j++) {
                if ( holder.getPosition() == j) {
                    holder.llBottomLine.setVisibility(View.VISIBLE);
                    dataArrayList.get(j).setSelected(true);
                } else {
                    holder.llBottomLine.setVisibility(View.GONE);
                    dataArrayList.get(j).setSelected(false);
                }
            }
            notifyDataSetChanged();
        });
        holder.tvCategoryName.setText(dataArrayList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        if (dataArrayList == null) return 0;
        return dataArrayList.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlCategoryContainer;
        LinearLayout llUserPhotoContainer;
        ImageView ivUesrPhoto;
        TextView tvCategoryName;
        LinearLayout llBottomLine;

        HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            rlCategoryContainer = itemView.findViewById(R.id.rl_category_container);
            llUserPhotoContainer = itemView.findViewById(R.id.ll_user_photo_container);
            ivUesrPhoto = itemView.findViewById(R.id.iv_user_photo);
            tvCategoryName = itemView.findViewById(R.id.tv_category_name);
            llBottomLine = itemView.findViewById(R.id.ll_bottom_line);
        }
    }

    public void setItemList(ArrayList<CategoryItem> data) {
        this.dataArrayList = data;
        Timber.d("data: %s", data.toString());
        notifyDataSetChanged();
    }

    public void removeList() {
        dataArrayList.clear();
        notifyDataSetChanged();
    }

    public ArrayList<CategoryItem> getList() {
        return dataArrayList;
    }

}