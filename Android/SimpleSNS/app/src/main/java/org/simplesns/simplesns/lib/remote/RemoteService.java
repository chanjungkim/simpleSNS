package org.simplesns.simplesns.lib.remote;

import org.simplesns.simplesns.activity.sign.item.LoginResult;
import org.simplesns.simplesns.activity.sign.item.SignUpData;
import org.simplesns.simplesns.activity.sign.item.SignUpResult;
import org.simplesns.simplesns.activity.sign.item.BasicResult;
import org.simplesns.simplesns.item.MemberItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RemoteService {
    String TAG = RemoteService.class.getSimpleName();

    // ★★★ push 시에 주의할 것
    String BASE_URL = "http://13.125.159.29:3000";

    @GET("/email/send")
    Call<BasicResult> validateEmail(@Query("to") String to);

    @POST("/member")
    Call<SignUpResult> insertMember(@Body SignUpData signUpData);

    @POST("/member/login")
    Call<LoginResult> loginMember(@Body MemberItem memberItem);

    @PUT("/member")
    Call<String> updateMember(@Body MemberItem memberItem);

    @DELETE("/member")
    Call<String> deleteMember(@Body MemberItem memberItem);

    @GET("/member")
    Call<String> getMyId(@Body MemberItem memberItem);
}