package org.simplesns.simplesns.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedCommentItem {
    /**
     * 리뷰 체크: https://youtu.be/3l3kQCNef28?t=3071
     * 1. false할 게 아니면, Expose 필요 없음.
     * 2. POJO는 getters, setters 사용 안 하고 변수들을 public으로 접근해도 상관없음.(규칙 정한게 있다면 상관없음. 코틀린은 안 만들어도됨.)
     */
    @SerializedName("created_time")
    @Expose
    private String createdTime;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("user")
    @Expose
    private MemberItem user;
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

    public MemberItem getUser() {
        return user;
    }

    public void setUser(MemberItem user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
