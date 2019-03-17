package org.simplesns.simplesns.ui.main.profile.model;

public class ProfileChangeResult {
    public int code;
    public String message;
    public String result;

    @Override
    public String toString() {
        return "ProfileChangeResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
