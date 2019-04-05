package org.simplesns.simplesns.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberItem {
    private String email;
    private String username;
    private String introduction;
    private String full_name;
    private String password;
//    private String profilePicture;       //csy
    private String photo_url;              //csy
    private String bio;
    private int feed_count;
    private int follows;
    private int followed_by;

    public MemberItem(){

    }

    public MemberItem(String email, String username, String password, String introduction){
        this.email = email;
        this.username = username;
        this.password = password;
        this.introduction = introduction;
    }

    /**
     * @param email
     * @param username
     * @param full_name
     * @param photo_url
     * @param bio
     * @param feed_count
     * @param follows
     * @param followed_by
     */
    public MemberItem(String email, String username, String full_name, String photo_url, String bio, int feed_count, int follows, int followed_by) {
        this.email = email;
        this.username = username;
        this.full_name = full_name;
        this.photo_url = photo_url;
        this.bio = bio;
        this.feed_count = feed_count;
        this.follows = follows;
        this.followed_by = followed_by;
    }

    public String getIntroduction() { return introduction; }

    public void setIntroduction(String introduction) { this.introduction = introduction; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getFeed_count() {
        return feed_count;
    }

    public void setFeed_count(int feed_count) {
        this.feed_count = feed_count;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFollowed_by() {
        return followed_by;
    }

    public void setFollowed_by(int followed_by) {
        this.followed_by = followed_by;
    }

    @Override
    public String toString() {
        return "MemberItem{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", introduction='" + introduction + '\'' +
                ", full_name='" + full_name + '\'' +
                ", password='" + password + '\'' +
                ", photo_url='" + photo_url + '\'' +
                ", bio='" + bio + '\'' +
                ", feed_count=" + feed_count +
                ", follows=" + follows +
                ", followed_by=" + followed_by +
                '}';
    }
}
