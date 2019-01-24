package org.simplesns.simplesns;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import org.simplesns.simplesns.main.MainActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameET = findViewById(R.id.username_edittext_loginactivity);
        EditText passwordET = findViewById(R.id.password_edittext_loginactivity);

        Button loginBTN = findViewById(R.id.login_button_loginactivity);

        loginBTN.setOnClickListener((v) -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}