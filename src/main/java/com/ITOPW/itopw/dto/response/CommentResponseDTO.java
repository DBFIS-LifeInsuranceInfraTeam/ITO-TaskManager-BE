package com.ITOPW.itopw.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CommentResponseDTO {
    private Long commentId;
    private String commenterId;
    private String commentContent;
    private LocalDateTime createDate;
    private Integer likeCount;
    private List<String> likedUsers;

    public CommentResponseDTO(Long commentId,String commenterId, String commentContent, LocalDateTime createDate, Integer likeCount, List<String> likedUsers ) {
        this.commentId = commentId;
        this.commenterId = commenterId;
        this.commentContent = commentContent;
        this.createDate = createDate;
        this.likeCount = likeCount;
        this.likedUsers = likedUsers;
    }

}
