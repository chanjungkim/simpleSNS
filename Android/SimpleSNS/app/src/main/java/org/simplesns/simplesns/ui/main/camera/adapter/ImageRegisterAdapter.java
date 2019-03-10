package org.simplesns.simplesns.ui.main.camera.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.simplesns.simplesns.ui.main.camera.fragment.CameraFragment;
import org.simplesns.simplesns.ui.main.camera.fragment.GalleryFragment;

import java.util.ArrayList;


public class ImageRegisterAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> irPageFragments;    // image register
    private String [] tab_titles = new String []{"gallery" ,"camera"};

    public ImageRegisterAdapter(FragmentManager fm) {
        super(fm);
        irPageFragments = new ArrayList<>();
        irPageFragments.add(new GalleryFragment());
        irPageFragments.add(new CameraFragment());
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tab_titles[position];
    }

    @Override
    public Fragment getItem(int i) {
        return irPageFragments.get(i);
    }

    @Override
    public int getCount() {
        return tab_titles.length;
    }
}
