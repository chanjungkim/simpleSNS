package org.simplesns.simplesns.ui.main.camera.fragment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.simplesns.simplesns.MyApp;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.ui.main.camera.adapter.ImageGalleryAdapter;
import org.simplesns.simplesns.ui.main.camera.customview.CustomZoomableImageView;
import org.simplesns.simplesns.ui.main.camera.utils.ImageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 1차 리뷰: https://youtu.be/3l3kQCNef28?t=5908
 */
public class GalleryFragment extends Fragment {
    Activity mActivity;
    Context mContext;

    RecyclerView rvGallery;
    ImageGalleryAdapter galleryAdapter;
    static List<String> galleryPaths;

    CustomZoomableImageView ivGallery;
    Cursor cursor;

    // 파팅 - preview motion
    private int _xDelta;
    private int _yDelta;
    private RelativeLayout rlPreviewContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_register_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();
        mContext = getContext();

        rlPreviewContainer = mActivity.findViewById(R.id.rl_preview_container);
        ivGallery = mActivity.findViewById(R.id.iv_preview_gallery);
        rvGallery = mActivity.findViewById(R.id.rv_gallery);

        // Get screen dimension.
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
//        int height = size.y;
        Log.e("Width", "" + width);
//        Log.e("height", "" + height);

        rlPreviewContainer.setLayoutParams(new RelativeLayout.LayoutParams(MyApp.getScreenWidth(getActivity()), MyApp.getScreenWidth(getActivity())));
        ivGallery.setLayoutParams(new RelativeLayout.LayoutParams(MyApp.getScreenWidth(getActivity()), MyApp.getScreenWidth(getActivity())));

        galleryPaths = new ArrayList<>();
        setImagePath();
        Collections.reverse(galleryPaths);

        galleryAdapter = new ImageGalleryAdapter(getContext(), galleryPaths, R.layout.item_gallery, ivGallery);
        rvGallery.setAdapter(galleryAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        rvGallery.setLayoutManager(gridLayoutManager);
        rvGallery.setItemAnimator(new DefaultItemAnimator());


        if (galleryPaths.size() > 0) {
            // 저장소에 이미지가 있는 경우
            ivGallery.setImageBitmap(ImageUtil.rotateBitmapOrientation(galleryPaths.get(0)));
            ImageUtil.pFile = new File(galleryPaths.get(0));
        } else {
            // 저장소에 이미지가 없는 경우
            ivGallery.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.no_image));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
    }

    private void setImagePath() {
        String[] STAR = {"*"};

        cursor = getActivity().managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                , STAR, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String path = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    galleryPaths.add(path);
                } while (cursor.moveToNext());

            }
            //cursor.close(); // todo : 코린이-커서를 닫아버려서 이미지 변경 페이지로 갔다가 되돌아 오면 재호출 버그 발생 수정중
        }
    }
}
