package org.simplesns.simplesns.item;

public class ChangeProfileItem {
    private String username;
    private String newUsername;
    private String introduction;
    private String photo_url;

    public ChangeProfileItem() {

    }

    public ChangeProfileItem(String username, String newUsername, String introduction) {
            this.username = username;
            this.newUsername = newUsername;
            this.introduction = introduction;
    }
    public ChangeProfileItem(String username, String photo_url) {
        this.username = username;
        this.photo_url = photo_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    @Override
    public String toString() {
        return "ChangeProfileItem{" +
                "username='" + username + '\'' +
                ", newUsername='" + newUsername + '\'' +
                ", introduction='" + introduction + '\'' +
                ", photo_url='" + photo_url + '\'' +
                '}';
    }
}
