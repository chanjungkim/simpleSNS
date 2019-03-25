package org.simplesns.simplesns.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedItem {
//    private FeedCommentItem comments;
//    private int comment_count;
//    private FeedDescriptionItem caption;
//    private int like_count;
//    private MemberItem user;
//    private String createdTime;
//    private FeedImageItem images;

    // 현재는 이미지 뿐이지만 추후 비디오를 넣을수도 있으니..?
//    private String type;
//    private List<String> tags = null;
//    private String id;
//    private FeedLocationItem location;

    // 파팅 - 나중에 수정
    private long fid;
    private String username;
    private String photo_url;
    private String reg_time;
    private String url;
    private int width;
    private int height;

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "FeedItem{" +
                "fid=" + fid +
                ", username='" + username + '\'' +
                ", photo_url='" + photo_url + '\'' +
                ", reg_time='" + reg_time + '\'' +
                ", url='" + url + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
