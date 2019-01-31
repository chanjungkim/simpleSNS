package org.simplesns.simplesns.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.lib.UIlib;
import org.simplesns.simplesns.activity.main.camera.ImageRegisterActivity;
import org.simplesns.simplesns.activity.main.home.HomeFragment;
import org.simplesns.simplesns.activity.main.profile.ProfileFragment;
import org.simplesns.simplesns.activity.main.search.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UIlib.getInstance(this).setImmersiveMode();
        setContentView(R.layout.activity_main);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.navigation);
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        replaceFragment(HomeFragment.newInstance());
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        replaceFragment(HomeFragment.newInstance());
                        return true;
                    case R.id.navigation_search:
                        replaceFragment(SearchFragment.newInstance());
                        return true;
                    case R.id.navigation_plus:
                        Intent image_reister_intent = new Intent (MainActivity.this, ImageRegisterActivity.class);
                        startActivity(image_reister_intent);
                        return true;
                    case R.id.navigation_like:

                        return true;
                    case R.id.navigation_profile:
                        replaceFragment(ProfileFragment.newInstance());
                        return true;
                }
                return false;
            };

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_main, fragment).commit();
    }
}

