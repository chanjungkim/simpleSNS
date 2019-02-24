package org.simplesns.simplesns.lib.remote;

import org.simplesns.simplesns.activity.sign.item.LoginResult;
import org.simplesns.simplesns.activity.sign.item.SignUpData;
import org.simplesns.simplesns.activity.sign.item.SignUpResult;
import org.simplesns.simplesns.activity.sign.item.BasicResult;
import org.simplesns.simplesns.item.MemberItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RemoteService {
    String TAG = RemoteService.class.getSimpleName();

    // ★★★ push 시에 주의할 것
    String BASE_URL = "http://13.125.159.29:3000";

    @POST("/member")
    Call<SignUpResult> insertMember(@Body SignUpData signUpData);

    @FormUrlEncoded
    @POST("/member/login")
    Call<LoginResult> loginMember(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("/member/login/myid")
    Call<LoginResult> loginByMyId(@Field("my_id") String myId);

    @GET("/email/send")
    Call<BasicResult> validateEmail(@Query("to") String to);

    @FormUrlEncoded
    @POST("/email/verify")
    Call<BasicResult> verifyEmailAndCode(@Field("email") String email, @Field("code") String code);

    @PUT("/member")
    Call<String> updateMember(@Body MemberItem memberItem);

    @DELETE("/member")
    Call<String> deleteMember(@Body MemberItem memberItem);

    @GET("/member")
    Call<String> getMyId(@Body MemberItem memberItem);
}