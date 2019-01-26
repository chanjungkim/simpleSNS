package org.simplesns.simplesns;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.simplesns.simplesns.main.MainActivity;

public class LoginActivity extends AppCompatActivity {
    static String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }
}