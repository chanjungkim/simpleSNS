package org.simplesns.simplesns.ui.main.profile.model;

import org.simplesns.simplesns.item.MemberItem;

public class ProfileResult {
    public MemberItem data;
    public int code;
    public String message;
    public String result;

    @Override
    public String toString() {
        return "ProfileResult{" +
                "data=" + data +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
