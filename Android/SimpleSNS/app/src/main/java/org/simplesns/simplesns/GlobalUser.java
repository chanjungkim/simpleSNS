package org.simplesns.simplesns;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import org.simplesns.simplesns.ui.sign.FirstActivity;
import org.simplesns.simplesns.ui.sign.model.LoginResult;
import org.simplesns.simplesns.ui.main.MainActivity;
import org.simplesns.simplesns.lib.remote.RemoteService;
import org.simplesns.simplesns.lib.remote.ServiceGenerator;
import org.simplesns.simplesns.util.SharedPreferenceUtil;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.MODE_PRIVATE;

/**
 * 계속 SharedPref를 쓰지 않고 글로벌하게 앱 내에서 유저 정보를 갖고 있는 클래스
 */
public class GlobalUser {
    private static String TAG = GlobalUser.class.getSimpleName();
    private static GlobalUser instance = null;
    String jwt;
    String my_id;
    String default_url = "https://st3.depositphotos.com/1767687/17621/v/1600/depositphotos_176214034-stock-illustration-default-avatar-profile-icon.jpg";

    /**
     * 싱글턴, 하나의 객체만을 사용함.
     *
     * @return
     */
    public static GlobalUser getInstance() {
        if (instance == null) {
            synchronized (GlobalUser.class) {
                if (instance == null) {
                    instance = new GlobalUser();
                }
            }
        }
        return instance;
    }

//    public String getJwt() {
//        return jwt;
//    }
//
//    public void setJwt(String jwt) {
//        this.jwt = jwt;
//    }

    public String getMyId() {
        return my_id;
    }

    public void setMyId(String my_id) {
        this.my_id = my_id;
    }

    public String getDefault_url() {
        return default_url;
    }

    public void setDefault_url(String default_url) {
        this.default_url = default_url;
    }

    /**
     * Get myId from SharedPreferences.
     *
     * @param context
     * @return
     */
    public String getMyIDPreference(Context context) {
        Log.d(TAG, "getMyIDPreference()");
        SharedPreferences pref2 = context.getSharedPreferences("pref2", MODE_PRIVATE);
        my_id = pref2.getString("my_id", "");
        if (my_id.equals("") || my_id == null) {
            Log.d(TAG, "No id....");
            return null;
        }
//        GlobalUser.getInstance().setMyId(my_id);
        Log.d(TAG, "my_id: " + my_id);

        return my_id;
    }

    /**
     * For sign-up and login. This is for normal login. It needs username, password.
     *
     * @param context
     * @param username
     * @param password
     */
    public void login(Context context, String username, String password) {
        Log.d(TAG, "login()= email: " + username + ", password: " + password);

        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        try {
            Call<LoginResult> call = remoteService.loginMember(username, password);

            call.enqueue(new Callback<LoginResult>() {
                @Override
                public void onResponse(Call<LoginResult> call, retrofit2.Response<LoginResult> response) {
                    try {
                        LoginResult responseResult = response.body(); // ???
                        Log.d(TAG, "login resonse" + responseResult.toString());
                        switch (responseResult.getCode()) {
                            case 100:
                                //요청에 성공한 경우 호출됨.
                                //로그인 성공 시 response. result -> jwt토큰 반환(String Type), JWT가 없으면 접속 못함.
                                jwt = responseResult.getJwt();
                                if (jwt.equals("") || jwt == null) {
                                    Toast.makeText(context, "Login Failed.", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    /**
                                     * 리뷰: https://youtu.be/3l3kQCNef28?t=3333
                                     */

                                    //값 저장하기
                                    SharedPreferenceUtil spUtil = new SharedPreferenceUtil(context);
                                    if(TextUtils.isEmpty(spUtil.getSharedMyId()) || TextUtils.isEmpty(spUtil.getSharedMyId())){
                                        spUtil.setSharedMyId(username);
                                        spUtil.setSharedPassword(password);
                                    }

                                    Log.d(TAG, "saved jwt: " + jwt);
//                                    GlobalUser.getInstance().setJwt(jwt);
                                    GlobalUser.getInstance().setMyId(username);
                                    //로그인 성공 시 화면 이동
                                    Intent intent = new Intent(context, MainActivity.class);
                                    context.startActivity(intent);
                                    Toast.makeText(context, responseResult.getMessage(), Toast.LENGTH_SHORT).show(); //핸들러 사용해야함
                                    ((Activity) context).finish();
//                                    getMyIdFromServer(context, MainActivity.class);
                                }

                                break;
                            default://로그인 실패시 다시 입력
                                Toast.makeText(context, responseResult.getMessage(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<LoginResult> call, Throwable throwable) {
                    Toast.makeText(context, "Check your internet status.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, throwable.toString(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

//    /**
//     * ???
//     * @param context
//     * @param mainActivityClass
//     */
//    public void getMyIdFromServer(Context context, Class<MainActivity> mainActivityClass) {
//        Log.d(TAG, "getMyIdFromServer()");
//
//        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
//
//        try {
//            Call<String> call = remoteService.getMyId(new MemberItem());
//
//            call.enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String> call, Response<String> response) {
//                    try {
//                        String responseResult = response.body().toString(); // ???
//                        Gson gson = new Gson();
//                        Log.d("profile", responseResult);
//                        User user = gson.fromJson(responseResult, User.class);
//                        if (user != null) {
//                            int code;
//                            my_id = user.id;
//                            code = user.code;
//
//                            SharedPreferences pref2 = context.getSharedPreferences("pref2", MODE_PRIVATE);
//                            pref2.edit()
//                                    .putString("my_id", my_id)
//                                    .apply();
//
//                            Log.d(TAG, "saved my_id: " + my_id);
//                            Log.d(TAG, "code: " + String.valueOf(code));
//
//                            //자동로그인기능
//                            switch (code) {
//                                case 100:
//                                    GlobalUser.getInstance().setMyId(my_id);
//                                    Intent intent = new Intent(context, mainActivityClass);
//                                    context.startActivity(intent);
//                                    Toast.makeText(context, "자동로그인 되었습니다.", Toast.LENGTH_SHORT).show();
//                                    ((Activity) context).finish();
//                                    break;
//                                default:
//                                    Toast.makeText(context, code+": ?", Toast.LENGTH_SHORT).show();
//                                    break;
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<String> call, Throwable throwable) {
//                    Toast.makeText(context, "getMyId() 에러", Toast.LENGTH_SHORT).show();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
//        }
//    }

//    /**
//     * For auto login, if the client ever logged in before. Skip FirstActivity and Show MainActivity directly.
//     * @param activityContext
//     * @param myId
//     */
//    public void loginByMyId(Context activityContext, String myId) {
//        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
//
//        Call<LoginResult> call = remoteService.loginByMyId(myId);
//
//        call.enqueue(new Callback<LoginResult>() {
//            @Override
//            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
//                LoginResult loginResult = response.body();
//
//                switch (loginResult.getCode()) {
//                    case 100:
//                        // 성공
//                        break;
//                    default:
//                        Toast.makeText(activityContext, loginResult.getCode() + ":" + loginResult.getMessage(), Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginResult> call, Throwable throwable) {
//                throwable.printStackTrace();
//                Toast.makeText(activityContext, throwable.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    /**
//     * ???
//     * @param activityContext
//     * @param loginActivityClass
//     */
    public void logOut(Context activityContext, Class<FirstActivity> firstActivityClass) {
        Log.d(TAG, "logOut()");

        // Delete all the sharedpreferences.
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(activityContext);
        sharedPreferenceUtil.removeSharedPreference();

        Toast.makeText(activityContext, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(activityContext, firstActivityClass);
        activityContext.startActivity(intent);
    }

    @Override
    public String toString() {
        return "GlobalUser{" +
                "jwt='" + jwt + '\'' +
                ", my_id='" + my_id + '\'' +
                ", default_url='" + default_url + '\'' +
                '}';
    }
}
