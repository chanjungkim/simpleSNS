package org.simplesns.simplesns.ui.main.camera.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.ui.main.camera.utils.ImageUtil;

import java.io.File;
import java.util.List;

/**
 * author : 코린이
 * Created by default on 2018-04-14.
 */
public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.ViewHolder> {
    private Context context;
    private List<String> items;
    private int itemLayout;
    private ImageView mainView;

    public ImageGalleryAdapter(Context context, List<String> items, int itemLayout, ImageView mainView) {
        this.context = context;
        this.items = items;
        this.itemLayout = itemLayout;
        this.mainView = mainView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        View v = LayoutInflater.from (parent.getContext()).inflate(itemLayout, parent, false);

        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) v.getLayoutParams();
        params.height = metrics.widthPixels/4;
        //params.width = metrics.heightPixels/3;
        v.setLayoutParams(params);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.background_gray)
                .error(R.drawable.background_gray)          // 오류 이미지 바꾸어야 할듯.
                .centerCrop();

        Glide.with(context)
                .load(items.get(position))
                .apply(requestOptions)
                .into(viewHolder.iv_grid_gallery);

        viewHolder.itemView.setOnClickListener(v-> {
                ImageUtil.pFile = new File(items.get(position));
                Bitmap bitmap =  ImageUtil.rotateBitmapOrientation(items.get(position));

                if (bitmap == null) {
                    ImageUtil.pFile = null;
                    Toast.makeText(context,context.getString(R.string.image_register_cant_open_file), Toast.LENGTH_SHORT).show();
                }
                mainView.setImageBitmap(bitmap);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_grid_gallery;

        ViewHolder (View itemView) {
            super(itemView);
            iv_grid_gallery = itemView.findViewById(R.id.iv_gallery);
        }
    }
}
