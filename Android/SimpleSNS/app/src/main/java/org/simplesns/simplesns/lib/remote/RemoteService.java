package org.simplesns.simplesns.lib.remote;

import org.simplesns.simplesns.ui.main.profile.model.ProfileResult;
import org.simplesns.simplesns.ui.sign.model.LoginResult;
import org.simplesns.simplesns.ui.sign.model.SignUpData;
import org.simplesns.simplesns.ui.sign.model.SignUpResult;
import org.simplesns.simplesns.ui.sign.model.BasicResult;
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
    String BASE_URL = "http://52.79.98.94:3000";

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

    @GET("/profile/{username}")
    Call<ProfileResult> getUserProfile(@Path("username") String username); // path != param

    @PUT("/member")
    Call<String> updateMember(@Body MemberItem memberItem);

    @DELETE("/member")
    Call<String> deleteMember(@Body MemberItem memberItem);

    @GET("/member")
    Call<String> getMyId(@Body MemberItem memberItem);
}