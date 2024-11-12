package com.ITOPW.itopw.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssigneeResponse {
    private String userId;
    private String username;
    private String photo;

    public AssigneeResponse(String userId, String username, String photo) {
        this.userId = userId;
        this.username = username;
        this.photo = photo;
    }
}
