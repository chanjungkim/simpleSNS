package org.simplesns.simplesns.main.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("comments")
    @Expose
    private Comments comments;

    @SerializedName("comment_count")
    @Expose
    private int comment_count;

    @SerializedName("caption")
    @Expose
    private Caption caption;

    @SerializedName("like_count")
    @Expose
    private int like_count;

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("created_time")
    @Expose
    private String createdTime;

    @SerializedName("images")
    @Expose
    private Images images;

    // 현재는 이미지 뿐이지만 추후 비디오를 넣을수도 있으니..?
    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("tags")
    @Expose
    private List<String> tags = null;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("location")
    @Expose
    private Location location;

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public Caption getCaption() {
        return caption;
    }

    public void setCaption(Caption caption) {
        this.caption = caption;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
