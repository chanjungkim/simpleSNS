package org.simplesns.simplesns.activity.main.camera.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * author : 코린이
 * Created by default on 2018-04-14.
 */
public class ImageGalleryAdapter  extends BaseAdapter {
    Context mContext;
    List<Bitmap> imageBmps; // 코린이 file path 로 변경 할듯
    ImageView iv_main;

    public ImageGalleryAdapter(Context context, List<Bitmap> imageBmps, ImageView iv_main) {
        this.mContext = context;
        this.imageBmps = imageBmps; // 코린이 file path 로 변경 할듯
        this.iv_main = iv_main;
    }

    @Override
    public int getCount() {
        return (imageBmps!=null) ? imageBmps.size() :0;
    }

    @Override
    public Bitmap getItem(int i) {
        return (imageBmps!=null) ? imageBmps.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (imageBmps == null) {
            return null;
        }

        // 코린이 : to do, get images from file path and use glide library
        ImageView imageView = null;
        imageView = new ImageView(mContext);
        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(imageBmps.get(i));
        imageView.invalidate();

        // 메인 이미지 뷰 변경
        imageView.setOnClickListener(v-> {
            iv_main.setImageBitmap(imageBmps.get(i));
        });
        return imageView;
    }

}
