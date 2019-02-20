package org.simplesns.simplesns;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;

import org.json.JSONObject;
import org.simplesns.simplesns.activity.LoginActivity;
import org.simplesns.simplesns.activity.sign.item.User;
import org.simplesns.simplesns.activity.main.MainActivity;
import org.simplesns.simplesns.item.MemberItem;
import org.simplesns.simplesns.item.result.LoginResult;
import org.simplesns.simplesns.lib.remote.RemoteService;
import org.simplesns.simplesns.lib.remote.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
     * @return
     */
    public static GlobalUser getInstance(){
        if(instance == null){
            synchronized (GlobalUser.class){
                if(instance == null){
                    instance = new GlobalUser();
                }
            }
        }
        return instance;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

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

    public void logout(Context activityContext, Class<LoginActivity> loginActivityClass) {
        Log.d(TAG, "logOut()");
        //로그아웃 버튼 기능 추가
        SharedPreferences pref = activityContext.getSharedPreferences("pref", MODE_PRIVATE);
        pref.edit()
                .remove("jwt")
                .apply();
        SharedPreferences pref2 = activityContext.getSharedPreferences("pref2", MODE_PRIVATE);
        pref.edit()
                .remove("my_id")
                .apply();

        Toast.makeText(activityContext, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(activityContext, loginActivityClass);
        activityContext.startActivity(intent);
    }

    public void getMyIDPreference(Context context) {
        Log.d(TAG, "getMyIDPreference()");
        SharedPreferences pref2 = context.getSharedPreferences("pref2", MODE_PRIVATE);
        my_id = pref2.getString("my_id", "");
        if (my_id.equals("") || my_id == null) {
            Log.d(TAG, "No id....");
        } else {
            GlobalUser.getInstance().setMyId(my_id);
            Log.d(TAG, "my_id: " + my_id);
        }
    }

    public void login(Context context, String email, String password) {
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        try{
            Call<LoginResult> call = remoteService.loginMember(new MemberItem());

            call.enqueue(new Callback<LoginResult>() {
                @Override
                public void onResponse(Call<LoginResult> call, retrofit2.Response<LoginResult> response) {
                    try {
                        String message;
                        boolean result;
                        int code;
                        String responseResult = response.body().toString(); // ???
                        JSONObject jsonObject = new JSONObject(responseResult);
                        Log.d(TAG, "login resonse" + responseResult);
                        Log.d(TAG, "response messasge" + jsonObject.getString("message"));
                        Log.d(TAG, "response code" + String.valueOf(jsonObject.getInt("code")));
//                                    Log.d("response result", String.valueOf(jsonObject.getBoolean("result")));
                        message = jsonObject.getString("message");
//                    result = jsonObject.getBoolean("result");
                        code = jsonObject.getInt("code");
                        if (code == 100) {
                            //요청에 성공한 경우 호출됨.
                            //로그인 성공 시 response. result -> jwt토큰 반환(String Type)
                            jwt = jsonObject.getString("result");
                            //값 저장하기
                            SharedPreferences sp = context.getSharedPreferences("pref", MODE_PRIVATE);
                            sp.edit()
                                    .putString("jwt", jwt)
                                    .apply();

                            Log.d(TAG, "saved jwt: " + jwt);
                            GlobalUser.getInstance().setJwt(jwt);
                            getMyIdFromServer(context, MainActivity.class);
                            //로그인 성공 시 화면 이동
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show(); //핸들러 사용해야함
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                            ((Activity)context).finish();
                        } else //로그인 실패시 다시 입력
                        {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<LoginResult> call, Throwable throwable) {
                    Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void getMyIdFromServer(Context context, Class<MainActivity> mainActivityClass) {
        Log.d(TAG, "getMyIdFromServer()");

        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        try{
            Call<String> call = remoteService.getMyId(new MemberItem());

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    try {
                        String responseResult = response.body().toString(); // ???
                        Gson gson = new Gson();
                        Log.d("profile", responseResult);
                        User user = gson.fromJson(responseResult, User.class);
                        if (user != null) {
                            int code;
                            my_id = user.id;
                            code = user.code;

                            SharedPreferences pref2 = context.getSharedPreferences("pref2", MODE_PRIVATE);
                            pref2.edit()
                                    .putString("my_id", my_id)
                                    .apply();

                            Log.d(TAG, "saved my_id: " + my_id);
                            Log.d(TAG, "code: " + String.valueOf(code));

                            //자동로그인기능
                            if (code == 100) {
                                GlobalUser.getInstance().setMyId(my_id);
                                Intent intent = new Intent(context, mainActivityClass);
                                context.startActivity(intent);
                                Toast.makeText(context, "자동로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                                ((Activity)context).finish();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    Toast.makeText(context, "getMyId() 에러", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void logOut(Context activityContext, Class<LoginActivity> loginActivityClass) {
        Log.d(TAG, "logOut()");
        //로그아웃 버튼 기능 추가
        SharedPreferences pref = activityContext.getSharedPreferences("pref", MODE_PRIVATE);
        pref.edit()
                .remove("jwt")
                .apply();
        SharedPreferences pref2 = activityContext.getSharedPreferences("pref2", MODE_PRIVATE);
        pref.edit()
                .remove("my_id")
                .apply();

        Toast.makeText(activityContext, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(activityContext, loginActivityClass);
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