package com.ITOPW.itopw.dto;


import lombok.Getter;
import lombok.Setter;

// 댓글 작성자 DTO
@Getter
@Setter
public class CommenterDTO {
    private String commenterId;
    private String commenterName;
    private String commenterProfile;

    // 생성자
    public CommenterDTO(String commenterId, String commenterName, String commenterProfile) {
        this.commenterId = commenterId;
        this.commenterName = commenterName;
        this.commenterProfile = commenterProfile;
    }

    // Getters and Setters
}
