package org.simplesns.simplesns.item;

public class ChangeProfileItem {
    private String username;
    private String newUsername;
    private String introduction;

    public ChangeProfileItem() {

    }
    public ChangeProfileItem(String username, String newUsername, String introduction) {
        this.username = username;
        this.newUsername = newUsername;
        this.introduction = introduction;
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

    @Override
    public String toString() {
        return "ChangeProfileItem{" +
                "username='" + username + '\'' +
                ", newUsername='" + newUsername + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
