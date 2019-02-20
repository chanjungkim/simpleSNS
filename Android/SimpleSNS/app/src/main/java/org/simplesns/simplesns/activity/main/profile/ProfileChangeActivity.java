package org.simplesns.simplesns.activity.main.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.simplesns.simplesns.R;

import de.hdodenhof.circleimageview.CircleImageView;
/*
폴더를 어떻게 구성하는건지 몰라서 일단 밖으로 빼놓고 구현했습니다.
문제가 있을 경우 조언 부탁드립니다.
 */

public class ProfileChangeActivity extends AppCompatActivity {
    ImageView btn_close;
    ImageView btn_save;
    CircleImageView iv_profile_photo;
    TextView tv_profile_photo;
    EditText et_name;
    EditText et_username;
    EditText et_introduction;
    EditText et_email;
    EditText et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change);

//        체크나 X 버튼을 누르면 ProfileFragment.java(fragment_profile.xml) 파일로 돌아감
        btn_close = (ImageView)findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_save = (ImageView)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : 수정한 내용 저장해야 함.  서버로 저장하나?
                finish();
            }
        });

        iv_profile_photo = (CircleImageView)findViewById(R.id.iv_profile_photo);
        iv_profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : 프로필 이미지 바꾸는 코드 작성하기
            }
        });
        tv_profile_photo = (TextView)findViewById(R.id.tv_profile_photo);
        tv_profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : 프로필 이미지 바꾸는 코드 작성하기
            }
        });

        // TODO : 서버에서 받은 값으로 설정하고, 새로 받은 값들 서버로 저장하는 코드
        et_name = (EditText)findViewById(R.id.et_name);
//        et_name.setText("");
        String new_name = et_name.getText().toString();

        et_username = (EditText)findViewById(R.id.et_username);
//        et_username.setText("");
        String new_username = et_username.getText().toString();

        et_introduction = (EditText)findViewById(R.id.et_introduction);
//        et_introduction.setText("");
        String new_introduction = et_introduction.getText().toString();

        et_email= (EditText)findViewById(R.id.et_email);
//        et_email.setText("");

        et_phone = (EditText)findViewById(R.id.et_phone);
//        et_phone.setText("");
    }
}