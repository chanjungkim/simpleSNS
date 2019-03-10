package org.simplesns.simplesns.ui.sign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.simplesns.simplesns.GlobalUser;
import org.simplesns.simplesns.R;
import org.simplesns.simplesns.ui.main.MainActivity;
import org.simplesns.simplesns.ui.sign.item.BasicResult;
import org.simplesns.simplesns.ui.sign.item.SignUpData;
import org.simplesns.simplesns.ui.sign.item.SignUpResult;
import org.simplesns.simplesns.lib.BasicCountDownTimer;
import org.simplesns.simplesns.lib.remote.RemoteService;
import org.simplesns.simplesns.lib.remote.ServiceGenerator;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstActivity extends AppCompatActivity {
    private static String TAG = FirstActivity.class.getSimpleName();
    long timeLeftInMillionSeconds = 1000 * 60 * 3;
    int backCount = 0;

    String email;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (GlobalUser.getInstance().getMyIDPreference(FirstActivity.this) != null) {
//            GlobalUser.getInstance().loginByMyId(FirstActivity.this, GlobalUser.getInstance().getMyId());
//        }

        initFirst();
    }

    @Override
    public void onBackPressed() {
        backCount++;

        // 수정해야할 부분.
        switch (backCount) {
            case 0:
                initEmailValidView();
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
            initEmailValidView();
        });

        loginBTN.setOnClickListener((v) -> {
            initLoginView();
        });
    }

    public void initEmailValidView() {
        backCount = 0;
        setContentView(R.layout.activity_first_email_valid);
        EditText emailET = findViewById(R.id.email_edittext_first_email_valid);
        Button sendBTN = findViewById(R.id.send_button_first_email_valid);
        EditText codeET = findViewById(R.id.code_edittext_first_email_valid);
        TextView countDownTimerTV = findViewById(R.id.timecount_textview_first_email_valid);
        Button nextBTN = findViewById(R.id.next_button_first_email_valid);

        nextBTN.setClickable(false);
        countDownTimerTV.setVisibility(View.INVISIBLE);

        // If you run Activity backwards, get the email address that the user input already.
        if (email != null) {
            emailET.setText(email);
        }

        emailET.setImeOptions(EditorInfo.IME_ACTION_DONE);
        // Why it's not working?
        emailET.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                nextBTN.performClick();
            }
            return false;
        });

        sendBTN.setOnClickListener((v) -> {
            email = emailET.getText().toString();

            // Check validation email by Server if no problem, then go to next view.
            try {
                sendVerificationEmail(email, nextBTN, countDownTimerTV);
//                initCreateUserView(email);
            } catch (NullPointerException e) {
                Toast.makeText(FirstActivity.this, "Please input correect email address.", Toast.LENGTH_SHORT).show();
            }
        });

        nextBTN.setOnClickListener((v) -> {
            verifyEmailAndCode(emailET.getText().toString(), codeET.getText().toString());
        });
    }

    /**
     * Email format validation
     *
     * @param email
     * @return
     */
    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    /**
     * Username format validation for instagram, instagram.
     * <p>
     * Only 'a-Z', '0-9', '_' are allowed.
     *
     * @param username
     * @return
     */
    private boolean validUsername(String username) {
        String USERNAME_PATTERN = "^@?(\\w){4,25}$";
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        return pattern.matcher(username).matches();
    }

    /**
     * Password format validation.
     * <p>
     * ^                 # start-of-string
     * (?=.*[0-9])       # a digit must occur at least once
     * (?=.*[a-z])       # a lower case letter must occur at least once
     * (?=.*[A-Z])       # an upper case letter must occur at least once
     * (?=.*[@#$%^&+=])  # a special character must occur at least once
     * (?=\S+$)          # no whitespace allowed in the entire string
     * .{8,}             # anything, at least eight places though
     * $                 # end-of-string
     *
     * @param password
     * @return
     */
    private boolean validPassword(String password) {
        String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        return pattern.matcher(password).matches();
    }

    public void sendVerificationEmail(String to, Button nextBTN, TextView countDownTimerTV) {
        Log.d(TAG, "sendVerificationEmail()= to(email): " + email);

        // Check email Regex
        if (!validEmail(email)) {
            Toast.makeText(FirstActivity.this, "This is not a correct email format.", Toast.LENGTH_SHORT).show();
            return;
        }

        // backCount 세지 않음.
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<BasicResult> call = null;
        try {
            call = remoteService.validateEmail(to);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            call.enqueue(new Callback<BasicResult>() {
                @Override
                public void onResponse(Call<BasicResult> call, Response<BasicResult> response) {
                    Log.d(TAG, "onResponse()");
                    try {
                        BasicResult validResult = response.body();
                        Log.d(TAG, validResult.toString());

                        switch (validResult.getCode()) {
                            case 100:
                                nextBTN.setClickable(true);
                                nextBTN.setBackgroundColor(getResources().getColor(R.color.link_blue));
                                countDownTimerTV.setVisibility(View.VISIBLE);
                                BasicCountDownTimer basicCountDownTimer = BasicCountDownTimer.getInstance(FirstActivity.this);
                                if (!basicCountDownTimer.isTimerRunning()) { // not running. initialize.
                                    basicCountDownTimer.setCountDownTimerFormat("Verify your email in: (", ":", ")");
                                    basicCountDownTimer.setTimeLeftInMilliseconds(timeLeftInMillionSeconds);
                                    basicCountDownTimer.startTimer(countDownTimerTV, nextBTN);
                                } else { // running
                                    basicCountDownTimer.stopTimer();
                                    basicCountDownTimer.setTimeLeftInMilliseconds(timeLeftInMillionSeconds);
                                    basicCountDownTimer.startTimer(countDownTimerTV, nextBTN);
                                }
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
                public void onFailure(Call<BasicResult> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Toast.makeText(FirstActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initCreateUserView(String email) {
        Log.d(TAG, "initCreateUserView()= email: " + email);

        backCount = -1;
        setContentView(R.layout.activity_first_create);
        TextView infoTV = findViewById(R.id.info_textview_first_create_activity);
        EditText usernameET = findViewById(R.id.fullname_edittext_first_create_activity);
        EditText passwordET = findViewById(R.id.password_edittext_first_create_activity);

        Button continueBTN = findViewById(R.id.continue_button_first_create_activity);

        infoTV.setText(Html.fromHtml("Your contacts will be periodically synced and stored on instagram servers to help you and others find friends, and to help us provide a better service. To remove contacts, go to Settings and disconnect. <a href=''>Learn More</a>"));

        // If the user ever input username or password before, keep them again.
        if (username != null) {
            usernameET.setText(username);
        }

        if (password != null) {
            passwordET.setText(password);
        }

        passwordET.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                continueBTN.performClick();
            }
            return false;
        });

        continueBTN.setOnClickListener((v) -> {
            username = usernameET.getText().toString();
            password = passwordET.getText().toString();

            // Check if username is empty
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(FirstActivity.this, "Please input username.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if password is empty
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(FirstActivity.this, "Please input password.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check email Regex
            if (!validUsername(username)) {
                Toast.makeText(FirstActivity.this, "You can only use a-z, 0-9, _ for username.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check password Regex
            if (!validPassword(password)) {
                Toast.makeText(FirstActivity.this, "You can only use a-z, 0-9, _ for username.", Toast.LENGTH_SHORT).show();
                return;
            }
//            tempPass();

            try {
                SignUpData signUpData = new SignUpData(email, username, password);

                RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

                Call<SignUpResult> call = remoteService.insertMember(signUpData);

                call.enqueue(new Callback<SignUpResult>() {
                    @Override
                    public void onResponse(Call<SignUpResult> call, Response<SignUpResult> response) {
                        SignUpResult signUpResult = response.body();
                        try {
                            Log.d(TAG, signUpResult.toString());
                            switch (signUpResult.code) {
                                case 100:
                                    try {
                                        GlobalUser.getInstance().login(FirstActivity.this, username, password);
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

    public void verifyEmailAndCode(String email, String code) {
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<BasicResult> call = remoteService.verifyEmailAndCode(email, code);
        call.enqueue(new Callback<BasicResult>() {
            @Override
            public void onResponse(Call<BasicResult> call, Response<BasicResult> response) {
                BasicResult basicResult = response.body();

                switch (basicResult.getCode()) {
                    case 100:
                        Toast.makeText(FirstActivity.this, basicResult.getMessage(), Toast.LENGTH_SHORT).show();
                        initCreateUserView(email);
                        break;
                    default:
                        Toast.makeText(FirstActivity.this, basicResult.getCode() + ": " + basicResult.getMessage(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<BasicResult> call, Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(FirstActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initLoginView() {
        backCount = 0;
        setContentView(R.layout.activity_login);

        EditText usernameET = findViewById(R.id.username_edittext_loginactivity);
        EditText passwordET = findViewById(R.id.password_edittext_loginactivity);
        Button loginBTN = findViewById(R.id.login_button_loginactivity);

        loginBTN.setOnClickListener((v) -> {
            String username = usernameET.getText().toString();
            String password = passwordET.getText().toString();

            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                GlobalUser.getInstance().login(FirstActivity.this, username, password);
//                tempPass();
            } else {
                Toast.makeText(this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 임피 패스를 위한 메소드
     */
    public void tempPass() {
        // 임시 패스
        Intent intent = new Intent(FirstActivity.this, MainActivity.class);
        GlobalUser.getInstance().setMyId(username);
        startActivity(intent);
        finish();
    }
}