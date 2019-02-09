package org.simplesns.simplesns.activity.main.camera.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.activity.main.camera.adapter.ImageGalleryAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GalleryFragment extends Fragment {
    Activity mActivity;
    Context mContext;
    List<Bitmap> tempBmp;   // 코린이 : to be deleted 파일경로로 변경 예정
    ImageView iv_gallery;
    GridView gv_gallery;
    ImageGalleryAdapter imageGalleryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_register_gallery,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();
        mContext = getContext();

        iv_gallery = (ImageView) mActivity.findViewById(R.id.iv_gallery);
        gv_gallery = (GridView) mActivity.findViewById (R.id.gv_gallery);

        // 코린이 : to be deleted 임시 테스트 -> file path로
        tempBmp = new ArrayList<>();
        tempBmp.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.a));
        tempBmp.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.b));
        tempBmp.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.lena));

        imageGalleryAdapter = new ImageGalleryAdapter(mContext, tempBmp, iv_gallery);
        gv_gallery.setAdapter(imageGalleryAdapter);

        iv_gallery.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.a));
    }
}
