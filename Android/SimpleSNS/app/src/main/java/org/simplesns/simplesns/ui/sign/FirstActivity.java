package org.simplesns.simplesns.ui.sign;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
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
import org.simplesns.simplesns.ui.sign.model.BasicResult;
import org.simplesns.simplesns.ui.sign.model.SignUpData;
import org.simplesns.simplesns.ui.sign.model.SignUpResult;
import org.simplesns.simplesns.lib.BasicCountDownTimer;
import org.simplesns.simplesns.lib.remote.RemoteService;
import org.simplesns.simplesns.lib.remote.ServiceGenerator;
import org.simplesns.simplesns.util.SharedPreferenceUtil;

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

        SharedPreferenceUtil spUtil = new SharedPreferenceUtil(this);
        if (!TextUtils.isEmpty(spUtil.getSharedMyId()) && !TextUtils.isEmpty(spUtil.getSharedPassword())) {
            GlobalUser.getInstance().login(this, spUtil.getSharedMyId(), spUtil.getSharedPassword());
        }

        initFirst();
    }

    public void initFirst() {
        setContentView(R.layout.activity_first);
        Button btnCreate = findViewById(R.id.btn_create);
        Button btnLogin = findViewById(R.id.btn_login);

        btnCreate.setOnClickListener((v) -> {
            initEmailValidView();
        });

        btnLogin.setOnClickListener((v) -> {
            initLoginView();
        });
    }

    public void initEmailValidView() {
        backCount = 0;
        setContentView(R.layout.activity_first_email_valid);
        EditText etEmail = findViewById(R.id.et_email);
        Button btnSend = findViewById(R.id.btn_send);
        EditText etCode = findViewById(R.id.et_code);
        TextView tvCountDown = findViewById(R.id.tv_timecount);
        Button btnNext = findViewById(R.id.btn_next);

        btnNext.setClickable(false);
        tvCountDown.setVisibility(View.INVISIBLE);

        // If you run Activity backwards, get the email address that the user input already.
        if (email != null) {
            etEmail.setText(email);
        }

        etEmail.setImeOptions(EditorInfo.IME_ACTION_DONE);
        // Why it's not working?
        etEmail.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                btnNext.performClick();
            }
            return false;
        });

        btnSend.setOnClickListener((v) -> {
            email = etEmail.getText().toString();

            // Check validation email by Server if no problem, then go to next view.
            try {
                sendVerificationEmail(email, btnNext, tvCountDown);
//                initCreateUserView(email);
            } catch (NullPointerException e) {
                Toast.makeText(FirstActivity.this, "Please input correect email address.", Toast.LENGTH_SHORT).show();
            }
        });

        btnNext.setOnClickListener((v) -> {
            verifyEmailAndCode(etEmail.getText().toString(), etCode.getText().toString());
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
        String USERNAME_PATTERN = "^[A-Za-z0-9_]+$";
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
        String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        return pattern.matcher(password).matches();
    }

    /**
     * Send verification Email to the to(User's Email Address). And start Count Down.
     *
     * @param to
     * @param nextBTN
     * @param countDownTimerTV
     */
    public void sendVerificationEmail(String to, Button nextBTN, TextView countDownTimerTV) {
        Log.d(TAG, "sendVerificationEmail()= to(email): " + email);

        // Check email Regex
        if (!validEmail(email)) {
            Toast.makeText(FirstActivity.this, "This is not a correct email format.", Toast.LENGTH_SHORT).show();
            return;
        }

        // backCount 세지 않음.
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<BasicResult> call = remoteService.validateEmail(to);

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
                                nextBTN.setBackgroundColor(getResources().getColor(R.color.default_blue));
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

    /**
     * Change this activity's current view into Create User View.
     *
     * @param email
     */
    public void initCreateUserView(String email) {
        Log.d(TAG, "initCreateUserView()= email: " + email);

        backCount = -1;
        setContentView(R.layout.activity_first_create);
        TextView tvInfo = findViewById(R.id.tv_info);
        EditText etUsername = findViewById(R.id.et_fullname);
        EditText etPassword = findViewById(R.id.et_password);

        Button btnContinue = findViewById(R.id.btn_continue);

        tvInfo.setText(Html.fromHtml("Your contacts will be periodically synced and stored on instagram servers to help you and others find friends, and to help us provide a better service. To remove contacts, go to Settings and disconnect. <a href=''>Learn More</a>"));

        // If the user ever input username or password before, keep them again.
        if (username != null) {
            etUsername.setText(username);
        }

        if (password != null) {
            etPassword.setText(password);
        }

        etPassword.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                btnContinue.performClick();
            }
            return false;
        });

        btnContinue.setOnClickListener((v) -> {
            username = etUsername.getText().toString();
            password = etPassword.getText().toString();

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
                Toast.makeText(FirstActivity.this, "Password needs to be included at least one number, one lower case letter, one upper case letter, one special character and longer than 8 without any spaces.", Toast.LENGTH_SHORT).show();
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

    /**
     * Check if the email and code match.
     *
     * @param email
     * @param code
     */
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

    /**
     * Change this Activity's view into Login View.
     */
    public void initLoginView() {
        backCount = 0;
        setContentView(R.layout.activity_login);

        EditText etUsername = findViewById(R.id.et_username);
        EditText etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);

        etPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                btnLogin.performClick();
                return true;
            }
            return false;
        });

        btnLogin.setOnClickListener((v) -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                GlobalUser.getInstance().login(FirstActivity.this, username, password);
//                tempPass();
            } else {
                Toast.makeText(this, "Please check your username or password.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 임피 패스를 위한 메소드
     */
    public void tempPass() {
        // 임시 패스
        Intent intent = new Intent(FirstActivity.this, MainActivity.class);
//        GlobalUser.getInstance().setMyId(username);
        startActivity(intent);
        finish();
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
                new Handler().postDelayed(() -> --backCount, 2000);
                break;
            case 3:
                super.onBackPressed();
                finish();
                break;
        }
    }
}