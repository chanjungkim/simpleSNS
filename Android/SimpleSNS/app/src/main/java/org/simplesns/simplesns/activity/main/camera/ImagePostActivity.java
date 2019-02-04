package org.simplesns.simplesns.activity.main.camera;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.simplesns.simplesns.R;
import org.simplesns.simplesns.activity.main.MainActivity;

public class ImagePostActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_post);

        toolbar = (Toolbar) findViewById(R.id.image_post_toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_keyboard_backspace_black_24dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.ic_post :
                Intent home_intent = new Intent(ImagePostActivity.this, MainActivity.class);
                home_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home_intent);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
