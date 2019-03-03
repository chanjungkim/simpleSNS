package org.simplesns.simplesns.activity.main;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.simplesns.simplesns.GlobalUser;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.activity.main.camera.utils.RegisterType;
import org.simplesns.simplesns.activity.main.favorite.FavoriteFragment;
import org.simplesns.simplesns.lib.UiLib;
import org.simplesns.simplesns.activity.main.camera.ImageRegisterActivity;
import org.simplesns.simplesns.activity.main.home.HomeFragment;
import org.simplesns.simplesns.activity.main.profile.ProfileFragment;
import org.simplesns.simplesns.activity.main.search.SearchFragment;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    private TextView mTextMessage;
    private BottomNavigationViewEx bottomNavigationViewEx;
    int backCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UiLib.getInstance(this).setHideNavigation(true);
        UiLib.getInstance(this).setStatusBarColor(getResources().getColor(R.color.grey));

        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Welcome, "+ GlobalUser.getInstance().getMyId(), Toast.LENGTH_SHORT).show();

        mTextMessage = findViewById(R.id.message);
        bottomNavigationViewEx = findViewById(R.id.navigation);
        bottomNavigationViewEx.enableAnimation(false)
                .enableAnimation(false)
                .enableItemShiftingMode(false) // Enable the shifting mode for each item. It will have a shifting animation for item if true. Otherwise the item text is always shown. Default true when item count > 3.
                .enableShiftingMode(false) // Enable the shifting mode for navigation. It will has a shift animation if true. Otherwise all items are the same width. Default true when item count > 3.
                .setIconSize(35) // Set all item ImageView size(dp).
                .setIconsMarginTop(0) // set margin top for all icons.
                .setTextVisibility(false) // Hide Text.
                .setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        replaceFragment(HomeFragment.newInstance());
        changeItemColor(0);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                replaceFragment(HomeFragment.newInstance());
                changeItemColor(0);
                return false;
            case R.id.navigation_search:
                replaceFragment(SearchFragment.newInstance());
                changeItemColor(1);
                return false;
            case R.id.navigation_plus:
                Intent imageResisterIntent = new Intent(MainActivity.this, ImageRegisterActivity.class);
                imageResisterIntent.putExtra("register_type", RegisterType.FEED);   // PROFILE 에서 호출시 RegisterType.PROFILE
                startActivity(imageResisterIntent);
                return false;
            case R.id.navigation_like:
                replaceFragment(FavoriteFragment.newInstance());
                changeItemColor(3);
                return false;
            case R.id.navigation_profile:
                replaceFragment(ProfileFragment.newInstance());
                changeItemColor(4);
                return false;
        }
        return false;
    };

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_main, fragment).commit();
    }

    private void changeItemColor(int position) {
        Log.d(TAG, "position: " + position);
        Log.d(TAG, "currentItem: " + bottomNavigationViewEx.getCurrentItem());
        for (int i = 0; i < 5; i++) {
            ImageViewCompat.setImageTintList(bottomNavigationViewEx.getIconAt(i), ColorStateList.valueOf(Color.parseColor("#ffbfbfbf")));
        }
        ImageViewCompat.setImageTintList(bottomNavigationViewEx.getIconAt(position), ColorStateList.valueOf(Color.parseColor("#ff000000")));
    }

    @Override
    public void onBackPressed() {
        backCount++;

        switch (backCount) {
            case 1:
                Toast.makeText(this, "Press back to exit.", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                super.onBackPressed();
                finish();
                break;
        }
    }
}

