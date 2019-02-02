package org.simplesns.simplesns.activity.main.camera;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.activity.main.camera.adapter.ImageRegisterAdapter;

public class ImageRegisterActivity extends AppCompatActivity {
    ViewPager pager;
    RelativeLayout container;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_register);

        pager =(ViewPager) findViewById (R.id.image_register_viewpager);
        container = (RelativeLayout) findViewById (R.id.image_register_container);
        tabLayout = (TabLayout) findViewById (R.id.image_register_tabs);

        ImageRegisterAdapter irAdapter = new ImageRegisterAdapter(getSupportFragmentManager());
        pager.setAdapter(irAdapter);

        tabLayout.setupWithViewPager(pager);
    }
}
