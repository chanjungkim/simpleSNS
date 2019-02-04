package org.simplesns.simplesns.activity.main.camera;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.activity.main.camera.adapter.ImageRegisterAdapter;

public class ImageRegisterActivity extends AppCompatActivity {
    ViewPager pager;
    RelativeLayout container;
    TabLayout tabLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_register);

        pager =(ViewPager) findViewById (R.id.image_register_viewpager);
        container = (RelativeLayout) findViewById (R.id.image_register_container);
        tabLayout = (TabLayout) findViewById (R.id.image_register_tabs);
        toolbar = (Toolbar) findViewById (R.id.image_register_toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_clear_black_24dp);


        ImageRegisterAdapter irAdapter = new ImageRegisterAdapter(getSupportFragmentManager());
        pager.setAdapter(irAdapter);

        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
