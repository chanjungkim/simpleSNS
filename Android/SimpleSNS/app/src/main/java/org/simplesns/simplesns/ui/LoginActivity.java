package org.simplesns.simplesns.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import org.simplesns.simplesns.GlobalUser;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.ui.main.MainActivity;

public class LoginActivity extends AppCompatActivity {
//    static String TAG = LoginActivity.class.getSimpleName();
//    String jwt;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //        getJWTPreference();
//
//        setContentView(R.layout.activity_login);
//
//        EditText etUsername = findViewById(R.id.et_username);
//        EditText etPassword = findViewById(R.id.et_password);
//        Button btnLogin = findViewById(R.id.btn_login);
//
//        btnLogin.setOnClickListener((v) -> {
//            GlobalUser.getInstance().login(LoginActivity.this, etUsername.getText().toString(), etPassword.getText().toString());
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//        });
//
//        // 패스워드 edit text에서 enter하면 바로 로그인
//        etUsername.setOnEditorActionListener((v, actionId, event) -> {
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                btnLogin.performClick();
//                return true;
//            }
//            return false;
//        });
//
//        etPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        getJWTPreference();
//        GlobalUser.getInstance().getMyIdFromServer(this, MainActivity.class);
//        GlobalUser.getInstance().getMyIDPreference(this);
//    }
//
//    public void getJWTPreference() {
//        Log.d(TAG, "getJWTPreference()");
//        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
//        jwt = pref.getString("jwt", "");
//        if (jwt == null || jwt.equals("")) {
//            Log.d(TAG, "No jwt....");
//        } else {
//            GlobalUser.getInstance().getMyIdFromServer(this, MainActivity.class); // 자동 로그인
//            GlobalUser.getInstance().setJwt(jwt);
//        }
//        Log.d(TAG, "jwt: " + jwt);
//    }

}