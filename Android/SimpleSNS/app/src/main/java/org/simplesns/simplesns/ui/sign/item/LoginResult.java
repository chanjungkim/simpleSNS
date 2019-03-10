package org.simplesns.simplesns.ui.sign.item;

public class LoginResult {
    String message;
    int code;
    String jwt;
    boolean result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", jwt='" + jwt + '\'' +
                ", result=" + result +
                '}';
    }
}
