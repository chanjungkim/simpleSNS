package org.simplesns.simplesns.activity.main.camera;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.activity.main.camera.adapter.ImageRegisterAdapter;
import org.simplesns.simplesns.activity.main.camera.utils.ImageUtil;
import org.w3c.dom.Text;

import java.io.File;

public class ImageRegisterActivity extends AppCompatActivity {
    ViewPager pager;
    RelativeLayout container;
    TabLayout tabLayout;
    TextView tv_next;
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_register);

        initView ();

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                // not used yet
            }

            @Override
            public void onPageSelected(int position) {
                switch ((position)) {
                    case 0: // gallery fragment
                        setBtnVisibility(true);
                        break;
                    case 1: // camera fragment
                        setBtnVisibility(false);
                        break;
                    default:
                        setBtnVisibility(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                // not used yet
            }
        });

        tv_next.setOnClickListener(v->{
            sendToImageModify(ImageUtil.pFile);
        });

        btn_back.setOnClickListener(v->{
            finish();
        });
    }

    private void initView () {
        pager =(ViewPager) findViewById (R.id.image_register_viewpager);
        container = (RelativeLayout) findViewById (R.id.image_register_container);
        tabLayout = (TabLayout) findViewById (R.id.image_register_tabs);
        tv_next = (TextView) findViewById (R.id.tv_next);
        btn_back = (ImageButton) findViewById (R.id.btn_back);

        tv_next.setText(getString(R.string.menu_next));
        tv_next.setVisibility(View.VISIBLE);

        ImageRegisterAdapter irAdapter = new ImageRegisterAdapter(getSupportFragmentManager());
        pager.setAdapter(irAdapter);

        tabLayout.setupWithViewPager(pager);
    }

    public void setBtnVisibility (boolean flag) {
        if (flag) {
            tv_next.setVisibility(View.VISIBLE); // gallery page
        } else {
            tv_next.setVisibility(View.GONE);    // camera page
        }
    }

    public void sendToImageModify (File mFile) {
        Intent registerIntent = new Intent(ImageRegisterActivity.this, ImageModifyActivity.class);
        registerIntent.putExtra("file_path", mFile.getAbsoluteFile().toString());
        startActivity(registerIntent);
    }
}
