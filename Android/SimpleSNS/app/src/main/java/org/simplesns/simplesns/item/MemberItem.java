package org.simplesns.simplesns.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberItem {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("full_name")
    @Expose
    private String full_name;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("feed_count")
    @Expose
    private int feed_count;
    @SerializedName("follows")
    @Expose
    private int follows;
    @SerializedName("followed_by")
    @Expose
    private int followed_by;

    public MemberItem(){

    }

    public MemberItem(String email, String username, String password){
        this.email = email;
        this.username = username;
        this.password = password;
    }

    /**
     * @param email
     * @param username
     * @param full_name
     * @param profilePicture
     * @param bio
     * @param feed_count
     * @param follows
     * @param followed_by
     */
    public MemberItem(String email, String username, String full_name, String profilePicture, String bio, int feed_count, int follows, int followed_by) {
        this.email = email;
        this.username = username;
        this.full_name = full_name;
        this.profilePicture = profilePicture;
        this.bio = bio;
        this.feed_count = feed_count;
        this.follows = follows;
        this.followed_by = followed_by;
    }

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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
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
                ", full_name='" + full_name + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", bio='" + bio + '\'' +
                ", feed_count=" + feed_count +
                ", follows=" + follows +
                ", followed_by=" + followed_by +
                '}';
    }
}
