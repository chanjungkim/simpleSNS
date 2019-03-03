package org.simplesns.simplesns.activity.main.camera.utils;

/**
 * feed 등록을 위해 호출하는 경우
 * Profile 등록을 위해 호출하는 경우
 */
public enum RegisterType {
    FEED,       // 홈 + 버튼 으로 호출
    PROFILE;    // 프로필 수정 페이지에서

    public static RegisterType registerType = PROFILE;
}
