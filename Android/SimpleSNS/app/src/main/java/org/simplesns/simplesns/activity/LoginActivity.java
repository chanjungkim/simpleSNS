package org.simplesns.simplesns.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.simplesns.simplesns.GlobalUser;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.activity.main.MainActivity;

public class LoginActivity extends AppCompatActivity {
    static String TAG = LoginActivity.class.getSimpleName();
    String jwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getJWTPreference();

        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_login);
        }catch (Exception e){
            Log.d(TAG, "★★★ Exception!!!");
            e.printStackTrace();
        }

        EditText usernameET = findViewById(R.id.username_edittext_loginactivity);
        EditText passwordET = findViewById(R.id.password_edittext_loginactivity);

        Button loginBTN = findViewById(R.id.login_button_loginactivity);

        loginBTN.setOnClickListener((v) -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalUser.getInstance().login(LoginActivity.this, usernameET.getText().toString(), passwordET.getText().toString());
            }
        });

        // 패스워드 edit text에서 enter하면 바로 로그인
        usernameET.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginBTN.performClick();
                    return true;
                }
                return false;
            }
        });

        passwordET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getJWTPreference();
        GlobalUser.getInstance().getMyIdFromServer(this, MainActivity.class);
        GlobalUser.getInstance().getMyIDPreference(this);
    }

    public void getJWTPreference() {
        Log.d(TAG, "getJWTPreference()");
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        jwt = pref.getString("jwt", "");
        if (jwt == null || jwt.equals("")) {
            Log.d(TAG, "No jwt....");
        } else {
            GlobalUser.getInstance().getMyIdFromServer(this, MainActivity.class); // 자동 로그인
            GlobalUser.getInstance().setJwt(jwt);
        }
        Log.d(TAG, "jwt: " + jwt);
    }

}