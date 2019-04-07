package org.simplesns.simplesns.lib.remote;

import org.simplesns.simplesns.item.ChangeProfileItem;
import org.simplesns.simplesns.item.FeedResult;
import org.simplesns.simplesns.item.MemberItem;
import org.simplesns.simplesns.ui.main.camera.model.ImagePostResult;
import org.simplesns.simplesns.ui.main.profile.model.CheckUsernameResult;
import org.simplesns.simplesns.ui.main.profile.model.ProfileChangeResult;
import org.simplesns.simplesns.ui.main.profile.model.ProfileResult;
import org.simplesns.simplesns.ui.main.search.model.FeedRecommendResult;
import org.simplesns.simplesns.ui.main.search.model.FollowResult;
import org.simplesns.simplesns.ui.main.search.model.GridRecommendResult;
import org.simplesns.simplesns.ui.sign.model.BasicResult;
import org.simplesns.simplesns.ui.sign.model.LoginResult;
import org.simplesns.simplesns.ui.sign.model.SignUpData;
import org.simplesns.simplesns.ui.sign.model.SignUpResult;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RemoteService {
    String TAG = RemoteService.class.getSimpleName();

    // ★★★ push 시에 주의할 것
    String BASE_URL = "http://ec2-13-124-229-143.ap-northeast-2.compute.amazonaws.com:3000";

    // User
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

    // Profile
    @GET("/profile/{username}")
    Call<ProfileResult> getUserProfile(@Path("username") String username); // path != param

    @GET("/profile/newuser/{newUsername}")
    Call<CheckUsernameResult> checkUsername(@Path("newUsername") String newUsername);

    @PUT("/profile/{username}")
    Call<ProfileChangeResult> setUserProfile(@Body ChangeProfileItem changeProfileItem);

    @Multipart
    @POST("images/upload_image.php")
    Call<ServerResponse> upload(
            @Header("Authorization") String authorization,
            @PartMap Map<String, RequestBody> map
    );

    @PUT("/member")
    Call<String> updateMember(@Body MemberItem memberItem);

    @DELETE("/member")
    Call<String> deleteMember(@Body MemberItem memberItem);

    @GET("/member")
    Call<String> getMyId(@Body MemberItem memberItem);

    // Camera
    @Multipart
    @POST ("/image")
    Call<ImagePostResult> uploadFeedImage (@Part MultipartBody.Part file);

    // Feed
    @GET("/feed")
    Call<FeedResult> getFeedItemsFromServer(@Query("username") String username, @Query("lastFeedNum") long lastFeedNum); // 파팅 - Username은 없애고 JWT를 헤더로 보내야함.

    // Search
    @GET("/search")
    Call<GridRecommendResult> getRecommendItemsFromServer(@Query("username") String username, @Query("lastFeedNum") long lastFeedNum); // 파팅 - Username은 없애고 JWT를 헤더로 보내야함.

    @GET("/search/feed")
    Call<FeedRecommendResult> getFeedRecommendItems(@Query("fid") long fid, @Query("username") String username);

    // 미구현
    @GET("/follow")
    Call<FollowResult> insertFollow(@Query("my_username") String myUsername, @Query("his_username") String hisUsername);
}