package org.simplesns.simplesns.activity.main.camera.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * 피드 등록 시간 기록
 * 카메라 캡쳐 시간 기록 IMG_20190305_231535.jpg (IMG_yyyyMMdd_HHmmss.jpg)
 * 시간 비교 함수 (정렬에 사용?)
 * 등등
 */
public class DateFormatUtil {

    private DateFormatUtil (){
        throw new AssertionError();
    }
    public static String getCurrentDateForCapture() {
        long now = System.currentTimeMillis();
        Date date = new Date (now);
        // https://developer.android.com/reference/java/text/SimpleDateFormat.html
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdFormat.format(date);
    }

}
