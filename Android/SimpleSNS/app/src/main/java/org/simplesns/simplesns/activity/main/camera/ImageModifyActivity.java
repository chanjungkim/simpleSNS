package org.simplesns.simplesns.activity.main.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import org.simplesns.simplesns.R;

public class ImageModifyActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView mainView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_modify);

        toolbar = (Toolbar) findViewById(R.id.image_modify_toolbar);
        mainView = (ImageView) findViewById (R.id.image_modify_view);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_keyboard_backspace_black_24dp);

        Intent image_modify_intent = getIntent();
        String temp = image_modify_intent.getExtras().getString("file_path");
        Toast.makeText(this, "image path = "+temp,Toast.LENGTH_SHORT).show();   // 디버깅용, 지울것

        Bitmap bmp = BitmapFactory.decodeFile(temp);
        Matrix matrix = new Matrix();    // portrait 모드에서만 촬영
        matrix.postRotate(90);          // 코린이 : 왜 돌려야하는지??
        Bitmap newBmp = Bitmap.createBitmap(bmp, 0, 0,bmp.getWidth(), bmp.getHeight(),matrix, true);
        mainView.setImageBitmap(newBmp);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_modify_toolbar_menu, menu);
        return true;
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
