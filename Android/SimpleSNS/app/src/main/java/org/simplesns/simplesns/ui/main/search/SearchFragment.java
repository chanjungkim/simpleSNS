package org.simplesns.simplesns.ui.main.search;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gigamole.navigationtabstrip.NavigationTabStrip;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.ui.main.BaseFragment;
import org.simplesns.simplesns.ui.main.favorite.FavoriteFragment;
import org.simplesns.simplesns.ui.main.favorite.sub_fragment.FollowingFragment;
import org.simplesns.simplesns.ui.main.favorite.sub_fragment.MyPostFragment;
import org.simplesns.simplesns.ui.main.home.HomeFragment;
import org.simplesns.simplesns.ui.main.search.sub_fragment.HotFragment;
import org.simplesns.simplesns.ui.main.search.sub_fragment.PeopleFragment;
import org.simplesns.simplesns.ui.main.search.sub_fragment.PlaceFragment;
import org.simplesns.simplesns.ui.main.search.sub_fragment.TagFragment;

public class SearchFragment extends BaseFragment {
    int FRAGMENT_COUNT = 4;
    private ViewPager mViewPager;
    PagerAdapter mPagerAdapter;
    private NavigationTabStrip navigationTabStrip;

    public static SearchFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        RelativeLayout toolbar = view.findViewById(R.id.tb_search);
        EditText etSearch = view.findViewById(R.id.et_search);
        ImageView ivBackButton = view.findViewById(R.id.iv_back_button);

        ivBackButton.setOnClickListener((v) -> {
            getActivity().onBackPressed();
        });

        // Force focus
        etSearch.post(() -> {
            etSearch.setFocusableInTouchMode(true);
            etSearch.requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etSearch, 0);

        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                CharSequence keyword = etSearch.getText();
                if (TextUtils.isEmpty(keyword)) {
                    getSearchResult(keyword.toString());
                }
                return true;
            }
            return false;
        });


        // View Pager
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

        return view;
    }

    private void setUI() {
        mViewPager.setAdapter(new SearchFragment.ViewPagerAdapter(getChildFragmentManager()));
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

    public void getSearchResult(String keyword) {
        // 구현
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * ViewPagerAdapter
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private String TAG = SearchFragment.ViewPagerAdapter.class.getSimpleName();
        String titles[] = {"Hot", "People", "Tag", "Place"};

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "getItem: " + position);
            switch (position) {
                case 1:
                    return new PeopleFragment();
                case 2:
                    return new TagFragment();
                case 3:
                    return new PlaceFragment();
                default:
                    return new HotFragment();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
