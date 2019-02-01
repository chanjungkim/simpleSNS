package org.simplesns.simplesns.activity.main.camera;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.activity.main.camera.adapter.ImageRegisterAdapter;

public class ImageRegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_register);

        ViewPager pager =(ViewPager) findViewById (R.id.image_register_viewpager);

        ImageRegisterAdapter irAdaoter = new ImageRegisterAdapter(getSupportFragmentManager());
        pager.setAdapter(irAdaoter);
    }
}
