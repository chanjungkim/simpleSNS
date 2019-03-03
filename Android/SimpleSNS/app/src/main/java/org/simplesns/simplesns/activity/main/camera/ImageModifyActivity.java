package org.simplesns.simplesns.activity.main.camera;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.activity.main.camera.utils.ImageUtil;

public class ImageModifyActivity extends AppCompatActivity {
    String file_path;
    ImageView mainView;
    TabHost tabHost;
    TextView tv_next;
    ImageButton btn_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_modify);

        mainView  = findViewById (R.id.image_modify_view);
        tabHost   = findViewById (R.id.image_modify_tabs);
        tv_next   = findViewById (R.id.tv_next);
        btn_back  = findViewById (R.id.btn_back);
        tabHost.setup();

        tv_next.setText(getString(R.string.menu_next));
        tv_next.setVisibility(View.VISIBLE);

        TabHost.TabSpec ts1 = tabHost.newTabSpec("Tab Spec 1") ;
        ts1.setContent(R.id.color_filter_menu_list) ;
        ts1.setIndicator(this.getString(R.string.image_modify_filter)) ;
        tabHost.addTab(ts1)  ;

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost.newTabSpec("Tab Spec 2") ;
        ts2.setContent(R.id.shape_modify_menu_list) ;
        ts2.setIndicator(this.getString(R.string.image_modify_shape)) ;
        tabHost.addTab(ts2);

        mainView.setImageBitmap(ImageUtil.rotateBitmapOrientation(ImageUtil.pFile.getAbsolutePath()));

        tv_next.setOnClickListener(v->{
            Intent image_post_intent = new Intent(ImageModifyActivity.this, ImagePostActivity.class);
            startActivity(image_post_intent);
        });

        btn_back.setOnClickListener(v->{
            finish();
        });
    }
}
