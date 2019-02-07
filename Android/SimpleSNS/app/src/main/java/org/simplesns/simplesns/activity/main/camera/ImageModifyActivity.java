package org.simplesns.simplesns.activity.main.camera;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.activity.main.camera.utils.ImageUtil;

public class ImageModifyActivity extends AppCompatActivity {
    String file_path;
    Toolbar toolbar;
    ImageView mainView;
    TabHost tabHost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_modify);

        toolbar = (Toolbar) findViewById(R.id.image_modify_toolbar);
        mainView = (ImageView) findViewById (R.id.image_modify_view);
        tabHost = (TabHost) findViewById (R.id.image_modify_tabs);
        tabHost.setup();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_keyboard_backspace_black_24dp);

        TabHost.TabSpec ts1 = tabHost.newTabSpec("Tab Spec 1") ;
        ts1.setContent(R.id.color_filter_menu_list) ;
        ts1.setIndicator(this.getString(R.string.image_modify_filter)) ;
        tabHost.addTab(ts1)  ;

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost.newTabSpec("Tab Spec 2") ;
        ts2.setContent(R.id.shape_modify_menu_list) ;
        ts2.setIndicator(this.getString(R.string.image_modify_shape)) ;
        tabHost.addTab(ts2) ;

        Intent image_modify_intent = getIntent();
        file_path = image_modify_intent.getExtras().getString("file_path");
        Toast.makeText(this, "image path = "+file_path,Toast.LENGTH_SHORT).show();   // 디버깅용, 지울것

        mainView.setImageBitmap(ImageUtil.rotateBitmapOrientation(file_path));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_modify_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.ic_next :
                Intent image_post_intent = new Intent(ImageModifyActivity.this, ImagePostActivity.class);
                image_post_intent.putExtra("file_path", file_path);
                startActivity(image_post_intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
