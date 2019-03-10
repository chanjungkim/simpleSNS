package org.simplesns.simplesns.ui.main.camera.fragment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.ui.main.camera.adapter.ImageGalleryAdapter;
import org.simplesns.simplesns.ui.main.camera.utils.ImageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryFragment extends Fragment {
    Activity mActivity;
    Context mContext;

    RecyclerView        rv_gallery;
    ImageGalleryAdapter galleryAdapter;
    static List<String> gallery_paths;

    ImageView iv_gallery;
    Cursor cursor;

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

        rv_gallery = mActivity.findViewById(R.id.rv_gallery);
        iv_gallery = mActivity.findViewById(R.id.iv_gallery);

        gallery_paths = new ArrayList<>();
        setImagePath();
        Collections.reverse(gallery_paths);

        galleryAdapter = new ImageGalleryAdapter (getContext(), gallery_paths, R.layout.item_gallery, iv_gallery);
        rv_gallery.setAdapter(galleryAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        rv_gallery.setLayoutManager(gridLayoutManager);
        rv_gallery.setItemAnimator(new DefaultItemAnimator());


        if (gallery_paths.size() > 0) {
            // 저장소에 이미지가 있는 경우
            iv_gallery.setImageBitmap(ImageUtil.rotateBitmapOrientation(gallery_paths.get(0)));
            ImageUtil.pFile = new File(gallery_paths.get(0));
        } else {
            // 저장소에 이미지가 없는 경우
            iv_gallery.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.no_image));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
    }

    private void setImagePath () {
        String[] STAR = { "*" };

        cursor = getActivity().managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                , STAR, null, null, null);

        if (cursor != null)
        {
            if (cursor.moveToFirst())
            {
                do
                {
                    String path = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    gallery_paths.add(path);
                } while (cursor.moveToNext());

            }
            //cursor.close(); // todo : 코린이-커서를 닫아버려서 이미지 변경 페이지로 갔다가 되돌아 오면 재호출 버그 발생 수정중
        }
    }
}
