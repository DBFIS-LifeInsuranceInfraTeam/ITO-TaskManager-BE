package com.ITOPW.itopw.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // null인 필드는 JSON에서 제외
public class BaseResponseDTO<T> {
    private int code; // 상태 코드
    private String httpStatus; // HTTP 상태
    private String message; // 상태 메시지
    private T data; // 응답 데이터

    // 기본 생성자
    public BaseResponseDTO(int code, String httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = null;
    }

    // 데이터가 있을 때 사용할 생성자
    public BaseResponseDTO(int code, String httpStatus, String message, T data) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
    }
}

