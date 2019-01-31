package org.simplesns.simplesns.activity.main.camera.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import org.simplesns.simplesns.activity.main.camera.Fragment.CameraFragment;
import org.simplesns.simplesns.activity.main.camera.Fragment.GalleryFragment;

import java.util.ArrayList;


public class ImageRegisterAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> irPageFragments;    // image register

    public ImageRegisterAdapter(FragmentManager fm) {
        super(fm);
        irPageFragments = new ArrayList<>();
        irPageFragments.add(new GalleryFragment());
        irPageFragments.add(new CameraFragment());
    }

    @Override
    public Fragment getItem(int i) {
        return irPageFragments.get(i);
    }

    @Override
    public int getCount() {
        return 2;
    }
}