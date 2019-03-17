package org.simplesns.simplesns.ui.main.profile.model;

public class CheckUsernameResult {
    public int code;
    public String message;
    public boolean result;

    @Override
    public String toString() {
        return "CheckUsernameResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
