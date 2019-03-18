package org.simplesns.simplesns.ui.main.favorite;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gigamole.navigationtabstrip.NavigationTabStrip;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.ui.main.BaseFragment;
import org.simplesns.simplesns.ui.main.favorite.sub_fragment.FollowingFragment;
import org.simplesns.simplesns.ui.main.favorite.sub_fragment.MyPostFragment;

public class FavoriteFragment extends BaseFragment {
    private static String TAG = FavoriteFragment.class.getSimpleName();

    int FRAGMENT_COUNT = 2;
    private ViewPager mViewPager;
    PagerAdapter mPagerAdapter;
    private NavigationTabStrip navigationTabStrip;

    public static FavoriteFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewPager = view.findViewById(R.id.vp);
        navigationTabStrip = view.findViewById(R.id.nts);
        setUI();
        navigationTabStrip.setViewPager(mViewPager, 0);
        navigationTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        navigationTabStrip.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
            @Override
            public void onStartTabSelected(String title, int index) {

            }

            @Override
            public void onEndTabSelected(String title, int index) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        return view;
    }

    private void setUI() {
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
//        mViewPager.setAdapter(new PagerAdapter() {
//            @Override
//            public int getCount() {
//                return FRAGMENT_COUNT;
//            }
//
//            @Override
//            public boolean isViewFromObject(final View view, final Object object) {
//                return view.equals(object);
//            }
//
//            @Override
//            public void destroyItem(final View container, final int position, final Object object) {
//                ((ViewPager) container).removeView((View) object);
//            }
//
//            @Override
//            public Object instantiateItem(final ViewGroup container, final int position) {
//                final View view = new View(getContext());
//                container.addView(view);
//                return view;
//            }
//        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    /**
     * ViewPagerAdapter
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private String TAG = ViewPagerAdapter.class.getSimpleName();
        String titles[] = {"FOLLOWING", "MY POST"};

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "getItem: " + position);
            switch (position) {
                case 0:
                    return new FollowingFragment();
                default:
                    return new MyPostFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}