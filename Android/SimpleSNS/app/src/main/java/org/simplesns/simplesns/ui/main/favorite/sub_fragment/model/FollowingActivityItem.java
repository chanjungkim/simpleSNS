package org.simplesns.simplesns.ui.main.favorite.sub_fragment.model;

public class FollowingActivityItem {
    long uid;
    String username;
    String content;
    String reg_time;
    String photo_url;
    String type;
    String whom;
    long fid;

    public long getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public String getReg_time() {
        return reg_time;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public String getType() {
        return type;
    }

    public String getWhom() {
        return whom;
    }

    public long getFid() {
        return fid;
    }

    @Override
    public String toString() {
        return "FollowingActivityItem{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", reg_time='" + reg_time + '\'' +
                ", photo_url='" + photo_url + '\'' +
                ", type='" + type + '\'' +
                ", whom='" + whom + '\'' +
                ", fid=" + fid +
                '}';
    }
}
