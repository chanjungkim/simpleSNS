package org.simplesns.simplesns.ui.sign.item;

public class SignUpData {
    public String username;
    public String email;
    public String password;

    public SignUpData(String email, String username, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
