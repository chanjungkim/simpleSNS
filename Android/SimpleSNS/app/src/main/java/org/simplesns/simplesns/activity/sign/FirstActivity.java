package org.simplesns.simplesns.activity.sign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.simplesns.simplesns.GlobalUser;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.activity.main.MainActivity;
import org.simplesns.simplesns.activity.sign.item.SignUpResult;
import org.simplesns.simplesns.activity.sign.item.ValidResult;
import org.simplesns.simplesns.item.MemberItem;
import org.simplesns.simplesns.lib.remote.RemoteService;
import org.simplesns.simplesns.lib.remote.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstActivity extends AppCompatActivity {
    private static String TAG = FirstActivity.class.getSimpleName();
    int backCount = 0;

    String email;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFirst();
    }

    @Override
    public void onBackPressed() {
        backCount++;

        switch (backCount) {
            case 0:
                initFirstEmailValid();
                break;
            case 1:
                initFirst();
                break;
            case 2:
                Toast.makeText(this, "Press back to exit.", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                super.onBackPressed();
                finish();
                break;
        }
    }

    public void initFirst() {
        setContentView(R.layout.activity_first);
        Button createBTN = findViewById(R.id.create_button_firstactivity);
        Button loginBTN = findViewById(R.id.login_button_firstactivity);

        createBTN.setOnClickListener((v) -> {
            initFirstEmailValid();
        });

        loginBTN.setOnClickListener((v) -> {
            initLogin();
        });
    }

    public void initFirstEmailValid() {
        backCount = 0;
        setContentView(R.layout.activity_first_email_valid);
        EditText emailET = findViewById(R.id.email_edittext_first_create_ctivity);
        Button nextBTN = findViewById(R.id.next_button_first_create_activity);

        if (email != null) {
            emailET.setText(email);
        }

        nextBTN.setOnClickListener((v) -> {
            email = emailET.getText().toString();

            try {
                validateEmail(email);
//                initFirstCreate(email);
            } catch (NullPointerException e) {
                Toast.makeText(FirstActivity.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void validateEmail(String to) {
        Log.d(TAG, "validateEmail()= to(email): "+email);

        // backCount 세지 않음.
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<ValidResult> call = null;
        try {
            call = remoteService.validateEmail(to);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            call.enqueue(new Callback<ValidResult>() {
                @Override
                public void onResponse(Call<ValidResult> call, Response<ValidResult> response) {
                    Log.d(TAG, "onResponse()");
                    try {
                        ValidResult validResult = response.body();
                        Log.d(TAG, validResult.toString());

                        switch (validResult.getCode()) {
                            case 100:
                                initFirstCreate(email);
                                Toast.makeText(FirstActivity.this, validResult.getMessage(), Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(FirstActivity.this, validResult.getMessage(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ValidResult> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Toast.makeText(FirstActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initFirstCreate(String email) {
        Log.d(TAG, "initFirstCreate()= email: "+email);
        backCount = -1;
        setContentView(R.layout.activity_first_create);
        TextView infoTV = findViewById(R.id.info_textview_first_create_activity);
        EditText usernameET = findViewById(R.id.fullname_edittext_first_create_activity);
        EditText passwordET = findViewById(R.id.password_edittext_first_create_activity);
        Button continueBTN = findViewById(R.id.continue_button_first_create_activity);

        infoTV.setText(Html.fromHtml("Your contacts will be periodically synced and stored on instagram servers to help you and others find friends, and to help us provide a better service. To remove contacts, go to Settings and disconnect. <a href=''>Learn More</a>"));

        if (username != null) {
            usernameET.setText(username);
        }

        if (password != null) {
            passwordET.setText(password);
        }

        continueBTN.setOnClickListener((v) -> {
            try {
                username = usernameET.getText().toString();
                try {
                    password = passwordET.getText().toString();
                } catch (NullPointerException e) {
                    Toast.makeText(FirstActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            } catch (NullPointerException e) {
                Toast.makeText(FirstActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }

            tempPass();

            try {
                MemberItem memberItem = new MemberItem(email, username, password);

                RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

                Call<SignUpResult> call = remoteService.insertMember(memberItem);

                call.enqueue(new Callback<SignUpResult>() {
                    @Override
                    public void onResponse(Call<SignUpResult> call, Response<SignUpResult> response) {
                        SignUpResult signUpResult = response.body();
                        try {
                            Log.d(TAG, signUpResult.toString());
                            switch (signUpResult.code) {
                                case 100:
                                    try {
                                        GlobalUser.getInstance().login(FirstActivity.this, email, passwordET.getText().toString());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(FirstActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                default:
                                    Toast.makeText(FirstActivity.this, signUpResult.code + ": " + signUpResult.message, Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(FirstActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SignUpResult> call, Throwable throwable) {
                        throwable.printStackTrace();
                        Toast.makeText(FirstActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(FirstActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initLogin() {
        backCount = 0;
        setContentView(R.layout.activity_login);

        EditText usernameET = findViewById(R.id.username_edittext_loginactivity);
        EditText passwordET = findViewById(R.id.password_edittext_loginactivity);
        Button loginBTN = findViewById(R.id.login_button_loginactivity);

        loginBTN.setOnClickListener((v) -> {
            if(usernameET.getText().toString() != null && passwordET.getText().toString() != null){
                GlobalUser.getInstance().setMyId(usernameET.getText().toString());
                tempPass();
            }else{
                Toast.makeText(this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 임피 패스를 위한 메소드
     */
    public void tempPass(){
        // 임시 패스
        Intent intent = new Intent(FirstActivity.this, MainActivity.class);
        GlobalUser.getInstance().setMyId(username);
        startActivity(intent);
        finish();
    }
}