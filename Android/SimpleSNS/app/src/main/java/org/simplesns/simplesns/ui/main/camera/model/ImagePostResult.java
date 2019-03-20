package org.simplesns.simplesns.ui.main.camera.model;

public class ImagePostResult {
    public int code;        // 100 : success, 400 : fail
    public String message;  //
    public String result;   // image url

    @Override
    public String toString() {
        return "ImagePostResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
