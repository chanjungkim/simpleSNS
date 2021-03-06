package org.simplesns.simplesns.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedImageItem {

    // 썸네일 이미지(작은사각형) , 일반 이미지 구분 필요? ..
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

}
