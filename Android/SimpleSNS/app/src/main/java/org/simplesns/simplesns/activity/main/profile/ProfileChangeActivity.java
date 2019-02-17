package org.simplesns.simplesns.activity.main.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import org.simplesns.simplesns.R;
/*
폴더를 어떻게 구성하는건지 몰라서 일단 밖으로 빼놓고 구현했습니다.
문제가 있을 경우 조언 부탁드립니다.
 */

public class ProfileChangeActivity extends AppCompatActivity {
    ImageView btn_close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change);


        // TODO

//        X 버튼을 누르면 ProfileFragment.java(fragment_profile.xml) 파일로 돌아감
        btn_close = (ImageView)findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}