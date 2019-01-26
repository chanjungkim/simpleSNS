package org.simplesns.simplesns.main.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 게시글 하단에 생성되는 Description 부분을 지칭
 */
public class Caption {

    @SerializedName("created_time")
    @Expose
    private String createdTime;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("id")
    @Expose
    private String id;

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
