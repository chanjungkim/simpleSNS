package org.simplesns.simplesns.main.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.main.Model.Data;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private static final String TAG = "HomeAdapter";
    private Context context;
    private ArrayList<Data> dataArrayList = new ArrayList<>();

    public HomeAdapter(Context context) {
        this.context = context;
    }

    public void addItem(ArrayList<Data> data) {
        this.dataArrayList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home, viewGroup, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder Holder, int i) {
        try{
            Holder.tv_user_name.setText(dataArrayList.get(i).getUser().getUsername());
        }catch (NullPointerException e){
            Log.d(TAG, "No User Data");
            e.printStackTrace();
            return;
        }

        RequestOptions requestOptions = new RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .fitCenter()
                .override(360,360);

        Glide.with(context)
                .load(dataArrayList.get(i).getUser().getProfilePicture())
                .apply(requestOptions)
                .into(Holder.iv_home_profile_photo);

        Glide.with(context)
                .load(dataArrayList.get(i).getImages().getUrl())
                .apply(requestOptions)
                .into(Holder.iv_home_feed_img);
    }

    @Override
    public int getItemCount() {
        if (dataArrayList == null) return 0;
        return dataArrayList.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        TextView tv_user_name;
        ImageView iv_home_profile_photo;
        ImageView iv_home_feed_img;


        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            iv_home_profile_photo = itemView.findViewById(R.id.iv_home_profile_photo);
            iv_home_feed_img = itemView.findViewById(R.id.iv_home_feed_img);
        }
    }
}
