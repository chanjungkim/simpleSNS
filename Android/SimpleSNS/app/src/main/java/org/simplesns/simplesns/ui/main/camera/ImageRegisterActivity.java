package org.simplesns.simplesns.ui.main.camera;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.ui.main.camera.adapter.ImageRegisterAdapter;
import org.simplesns.simplesns.ui.main.camera.utils.ImageUtil;
import org.simplesns.simplesns.ui.main.camera.utils.RegisterType;

import java.io.File;
import java.util.List;

import static org.simplesns.simplesns.constant.CameraConstant.RESISTER_TYPE_FROM_IMAGE_REGISTER;

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

        // permission check
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //Toast.makeText(ImageRegisterActivity.this,"Permission Granted", Toast.LENGTH_SHORT).show();
                initView();
                registerTypeCheck();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(ImageRegisterActivity.this, "Permission Denied\n"
                        + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getString(R.string.request_permission))
                .setDeniedMessage(getString(R.string.deny_permission))
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    private void initView() {
        pager = findViewById(R.id.image_register_viewpager);
        container = findViewById(R.id.image_register_container);
        tabLayout = findViewById(R.id.image_register_tabs);
        tv_next = findViewById(R.id.tv_next);
        btn_back = findViewById(R.id.btn_back);

        tv_next.setText(getString(R.string.menu_next));
        tv_next.setVisibility(View.VISIBLE);

        ImageRegisterAdapter irAdapter = new ImageRegisterAdapter(getSupportFragmentManager());
        pager.setAdapter(irAdapter);

        tabLayout.setupWithViewPager(pager);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        tv_next.setOnClickListener(v -> {
            if (ImageUtil.pFile != null) {
                sendToImageModify(ImageUtil.pFile);
            } else {
                Toast.makeText(this, getString(R.string.image_register_capture_first), Toast.LENGTH_SHORT).show();
            }

        });

        btn_back.setOnClickListener(v -> {
            finish();
        });
    }

    private void registerTypeCheck() {
        Intent registerIntent = getIntent();
        RegisterType.registerType = (RegisterType) registerIntent.getSerializableExtra(RESISTER_TYPE_FROM_IMAGE_REGISTER); // 상수로 보내는 쪽, 받는 쪽 맞추기.
        if (RegisterType.registerType == null) {
            throw new AssertionError(); // developer error: register 타입을 주어야함
        }
        switch (RegisterType.registerType) {
            case FEED:
                // todo FEED 등록을 위해 이미지를 사각형으로 표시
                break;
            case PROFILE:
                // todo PROFILE 등록을 위해 이미지를 원형으로 표시
                break;
        }
    }

    public void setBtnVisibility(boolean flag) {
        if (flag) {
            tv_next.setVisibility(View.VISIBLE); // next btn is visible in gallery page
        } else {
            tv_next.setVisibility(View.GONE);    // next btn is gone in camera page
        }
    }

    public void sendToImageModify(File mFile) {
        if (mFile != null) {
            Intent registerIntent = new Intent(ImageRegisterActivity.this, ImageModifyActivity.class);
            startActivity(registerIntent);
        }
    }
}
