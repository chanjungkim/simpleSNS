package org.simplesns.simplesns.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedItem {

    @SerializedName("comments")
    @Expose
    private FeedCommentItem comments;

    @SerializedName("comment_count")
    @Expose
    private int comment_count;

    @SerializedName("caption")
    @Expose
    private FeedDescriptionItem caption;

    @SerializedName("like_count")
    @Expose
    private int like_count;

    @SerializedName("user")
    @Expose
    private UserItem user;

    @SerializedName("created_time")
    @Expose
    private String createdTime;

    @SerializedName("images")
    @Expose
    private FeedImageItem images;

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
    private FeedLocationItem location;

    public FeedCommentItem getComments() {
        return comments;
    }

    public void setComments(FeedCommentItem comments) {
        this.comments = comments;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public FeedDescriptionItem getCaption() {
        return caption;
    }

    public void setCaption(FeedDescriptionItem caption) {
        this.caption = caption;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public UserItem getUser() {
        return user;
    }

    public void setUser(UserItem user) {
        this.user = user;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public FeedImageItem getImages() {
        return images;
    }

    public void setImages(FeedImageItem images) {
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

    public FeedLocationItem getLocation() {
        return location;
    }

    public void setLocation(FeedLocationItem location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "FeedItem{" +
                "comments=" + comments +
                ", comment_count=" + comment_count +
                ", caption=" + caption +
                ", like_count=" + like_count +
                ", user=" + user +
                ", createdTime='" + createdTime + '\'' +
                ", images=" + images +
                ", type='" + type + '\'' +
                ", tags=" + tags +
                ", id='" + id + '\'' +
                ", location=" + location +
                '}';
    }
}
