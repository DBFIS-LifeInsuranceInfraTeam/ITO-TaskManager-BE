package com.ITOPW.itopw.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    private String id; // 사용자 ID
    private String password; // 비밀번호
}
