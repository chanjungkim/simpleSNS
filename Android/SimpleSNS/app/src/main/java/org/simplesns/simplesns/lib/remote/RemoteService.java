package org.simplesns.simplesns.lib.remote;

public interface RemoteService {
    String TAG = RemoteService.class.getSimpleName();

    // ★★★ push 시에 주의할 것
    String BASE_URL = "http://xxx.xxx.xxx.xxx:xxxx/";

      // User Info
//    @GET("/user/select")
//    Call<UserItem> selectUser(@Body UserItem userItem);
//
//    @POST("/member/insert")
//    Call<String> insertUser(@Body UserItem userItem);
//
//    @POST("/member/update")
//    Call<String> updateUser(@Body UserItem userItem);
}