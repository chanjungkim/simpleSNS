package org.simplesns.simplesns.main.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *  REST API Query 에 대한 서버로부터 Response 정의
 */
public class Response {

    /**
     * @param code  응답코드
     * @param error_type  에러 Exception 타입
     * @param error_msg  에러 메세지
     * @param data Feed 나 Comment 요청시 List 로 받음.
     */

    public Response(String code, String error_type, String error_msg, List<Data> data) {
        this.code = code;
        this.error_type = error_type;
        this.error_msg = error_msg;
        this.data = data;
    }

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("error_type")
    @Expose
    private String error_type;

    @SerializedName("error_msg")
    private String error_msg;

    @SerializedName("data")
    @Expose
    private List<Data> data = null;



    // 페이징 도 추가해야함


}
