package org.simplesns.simplesns.ui.main.search.model;

public class FollowResult {
    public String message;
    public boolean result;
    public int code;

    @Override
    public String toString() {
        return "FollowResult{" +
                "message='" + message + '\'' +
                ", result=" + result +
                ", code=" + code +
                '}';
    }
}
