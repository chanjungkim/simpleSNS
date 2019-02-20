package org.simplesns.simplesns.lib.page;

import android.support.v4.app.Fragment;

// 미구현
public class PageItem {
    Fragment fragment;
    int tapNum;
    Object data;

    PageItem(Fragment fragment, int tapNum, Object data){
        this.fragment = fragment;
        tapNum = tapNum;
        this.data = data;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public int getTapNum() {
        return tapNum;
    }

    public void setTapNum(int tapNum) {
        this.tapNum = tapNum;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageItem{" +
                "fragment=" + fragment.getTag() +
                ", tapNum=" + tapNum +
                ", data=" + data +
                '}';
    }
}
