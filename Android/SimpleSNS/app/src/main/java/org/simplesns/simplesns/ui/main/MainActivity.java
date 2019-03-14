package org.simplesns.simplesns.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavSwitchController;
import com.ncapdevi.fragnav.FragNavTransactionOptions;
import com.ncapdevi.fragnav.tabhistory.FragNavTabHistoryController;
import com.roughike.bottombar.BottomBar;

import org.simplesns.simplesns.GlobalUser;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.lib.UiLib;
import org.simplesns.simplesns.ui.main.camera.ImageRegisterActivity;
import org.simplesns.simplesns.ui.main.camera.utils.RegisterType;
import org.simplesns.simplesns.ui.main.favorite.FavoriteFragment;
import org.simplesns.simplesns.ui.main.home.HomeFragment;
import org.simplesns.simplesns.ui.main.profile.ProfileFragment;
import org.simplesns.simplesns.ui.main.search.RecommendFragment;
import org.simplesns.simplesns.ui.main.search.SearchFragment;
import org.simplesns.simplesns.ui.main.BaseFragment;

/**
 * 리뷰: https://youtu.be/3l3kQCNef28?t=5667
 */
public class MainActivity extends AppCompatActivity implements BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {
    private static String TAG = MainActivity.class.getSimpleName();
    //Better convention to properly name the indices what they are in your app
    private final int INDEX_HOME = FragNavController.TAB1;
    private final int INDEX_SEARCH = FragNavController.TAB2;
    //    private final int INDEX_CAMERA = FragNavController.TAB3;
    private final int INDEX_FAVORITE = FragNavController.TAB4;
    private final int INDEX_PROFILE = FragNavController.TAB5;
    private FragNavController mNavController;

    int backCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Window
        UiLib.getInstance(this).setHideNavigation(true);
        UiLib.getInstance(this).setStatusBarColor(getResources().getColor(R.color.grey));

        setContentView(R.layout.activity_main);


        Toast.makeText(this, "Welcome, " + GlobalUser.getInstance().getMyId(), Toast.LENGTH_SHORT).show();

        final BottomBar bottomBar = findViewById(R.id.bottombar);

        boolean initial = savedInstanceState == null;
        if (initial) {
            bottomBar.selectTabAtPosition(INDEX_HOME);
        }

        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.fl_main)
                .transactionListener(this)
                .rootFragmentListener(this, 5)
                .popStrategy(FragNavTabHistoryController.UNIQUE_TAB_HISTORY)
                .switchController(new FragNavSwitchController() {
                    @Override
                    public void switchTab(int index, FragNavTransactionOptions transactionOptions) {
                        bottomBar.selectTabAtPosition(index);
                    }
                })
                .build();

        bottomBar.setOnTabSelectListener(tabId -> {
            Log.d(TAG, "tap listener" + tabId);
            switch (tabId) {
                case R.id.bb_menu_home:
                    mNavController.switchTab(INDEX_HOME);
                    break;
                case R.id.bb_menu_search:
                    mNavController.switchTab(INDEX_SEARCH);
                    break;
                case R.id.bb_menu_camera:
                    Intent imageResisterIntent = new Intent(MainActivity.this, ImageRegisterActivity.class);
                    imageResisterIntent.putExtra("register_type", RegisterType.FEED);   // PROFILE 에서 호출시 RegisterType.PROFILE
                    startActivity(imageResisterIntent);
                    break;
                case R.id.bb_menu_favorite:
                    mNavController.switchTab(INDEX_FAVORITE);
                    break;
                case R.id.bb_menu_profile:
                    mNavController.switchTab(INDEX_PROFILE);
                    break;
                default:
                    Log.d(TAG, "No where to go");
                    break;
            }
        }, initial);

        bottomBar.setOnTabReselectListener(tabId -> mNavController.clearStack());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNavController != null) {
            mNavController.onSaveInstanceState(outState);
        }
    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.pushFragment(fragment);
        }
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }


    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case INDEX_HOME:
                return HomeFragment.newInstance(0);
            case INDEX_SEARCH:
                return RecommendFragment.newInstance(0);
            case INDEX_FAVORITE:
                return FavoriteFragment.newInstance(0);
            case INDEX_PROFILE:
                return ProfileFragment.newInstance(0);
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mNavController.popFragment();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        /**
         * 프레그먼트 관리에 따라 추후 수정 필요
         */
        if (!mNavController.popFragment()) {
            backCount++;
            switch (backCount) {
                case 1:
                    Toast.makeText(this, "Press back to exit.", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> --backCount, 2000);
                    break;
                case 2:
                    finish();
                    break;
            }
        }
    }
}

