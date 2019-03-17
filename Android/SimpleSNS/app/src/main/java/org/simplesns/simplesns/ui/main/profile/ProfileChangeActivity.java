package org.simplesns.simplesns.ui.main.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.simplesns.simplesns.GlobalUser;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.item.MemberItem;
import org.simplesns.simplesns.lib.remote.RemoteService;
import org.simplesns.simplesns.lib.remote.ServiceGenerator;
import org.simplesns.simplesns.ui.main.profile.model.CheckUsernameResult;
import org.simplesns.simplesns.ui.main.profile.model.ProfileResult;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/*
폴더를 어떻게 구성하는건지 몰라서 일단 밖으로 빼놓고 구현했습니다.
문제가 있을 경우 조언 부탁드립니다.
 */

public class ProfileChangeActivity extends AppCompatActivity {
    public static final String TAG = ProfileChangeActivity.class.getSimpleName();

    ImageView btnClose;
    ImageView btnSave;
    CircleImageView ivProfilePhoto;
    LinearLayout llProfilePhotoChange;
//    EditText etName;
    EditText etUsername;
    EditText etIntroduction;
    EditText etEmail;
//    EditText etPhone;
    String newUsername;
    String newIntroduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change);

        getUserProfileFromServer(GlobalUser.getInstance().getMyId());

//        체크나 X 버튼을 누르면 ProfileFragment.java(fragment_profile.xml) 파일로 돌아감
        btnClose = findViewById(R.id.btn_close);
        btnClose.setOnClickListener(v -> finish());
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(v -> {
            newUsername = etUsername.getText().toString();
            newIntroduction = etIntroduction.getText().toString();
            // TODO : 수정한 내용 저장해야 함.

//            setUserProfileFromServer(GlobalUser.getInstance().getMyId());
            finish();
        });

        ivProfilePhoto = findViewById(R.id.iv_profile_photo);
        llProfilePhotoChange = (LinearLayout) findViewById(R.id.ll_profile_photo_change);
        llProfilePhotoChange.setOnClickListener(v -> {
            // TODO : 프로필 이미지 바꾸는 코드 작성하기
            Toast.makeText(this, "이미지 바꾸기", Toast.LENGTH_SHORT).show();
        });

//        etName = findViewById(R.id.et_name);
        etUsername = findViewById(R.id.et_username);
        etIntroduction = findViewById(R.id.et_introduction);
        etEmail = findViewById(R.id.et_email);
//        etPhone = findViewById(R.id.et_phone);
//        etUsername.setText(GlobalUser.getInstance().getMyId());

        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
                newUsername = etUsername.getText().toString();
                if (newUsername.equals(GlobalUser.getInstance().getMyId())){

                } else {
                    checkUsernameFromServer(newUsername);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }
        });


    }

    private void getUserProfileFromServer(String username) {
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<ProfileResult> call = remoteService.getUserProfile(username);

        try {
            call.enqueue(new Callback<ProfileResult>() {
                @Override
                public void onResponse(Call<ProfileResult> call, Response<ProfileResult> response) {
                    ProfileResult profileResult = response.body();
                    Log.d(TAG, profileResult.toString());

                    switch (profileResult.code) {
                        case 200:
                            MemberItem memberItem = profileResult.data;
                            etUsername.setText(memberItem.getUsername());
                            etEmail.setText(memberItem.getEmail());
                            if (memberItem.getIntroduction() != null) {
                                etIntroduction.setText(memberItem.getIntroduction()+"");
                            }

                            Log.d(TAG, memberItem.toString());
                            break;
                        default:
                            Log.d(TAG, profileResult.code + "");
                            break;
                    }
                }

                @Override
                public void onFailure(Call<ProfileResult> call, Throwable throwable) {
                    Toast.makeText(ProfileChangeActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
                    throwable.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void checkUsernameFromServer(String newUsername) {
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<CheckUsernameResult> call = remoteService.checkUsername(newUsername);
        try {
            call.enqueue(new Callback<CheckUsernameResult>() {
                @Override
                public void onResponse(Call<CheckUsernameResult> call, Response<CheckUsernameResult> response) {
                    CheckUsernameResult checkUsernameResult = response.body();
                    Log.d(TAG, checkUsernameResult.toString());

                    switch (checkUsernameResult.code) {
                        case 200:
                            boolean doesUsernameExist = checkUsernameResult.result;
                            if (doesUsernameExist){
                                Toast toast = Toast.makeText(ProfileChangeActivity.this, "사용자 이름 " +newUsername+"을(를) 사용할 수 없습니다.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,120);
                                toast.show();
                            }
                            break;
                        default:
                            Log.d(TAG, checkUsernameResult.code + "");
                            break;
                    }
                }

                @Override
                public void onFailure(Call<CheckUsernameResult> call, Throwable throwable) {
                    Toast.makeText(ProfileChangeActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
                    throwable.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}