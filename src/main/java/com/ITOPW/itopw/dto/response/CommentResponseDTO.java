package com.ITOPW.itopw.dto.response;

import com.ITOPW.itopw.dto.CommenterDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CommentResponseDTO {
    private Long commentId;
    //private String commentId;
    private CommenterDTO commenter; // 댓글 작성자 정보
    private String commentContent;
    private LocalDateTime createDate;
    private int likeCount;
    private List<String> likedUsers;


    // 생성자
    public CommentResponseDTO(Long commentId, CommenterDTO commenter, String commentContent, LocalDateTime createDate, int likeCount, List<String> likedUsers) {
        this.commentId = commentId;
        this.commenter = commenter;
        this.commentContent = commentContent;
        this.createDate = createDate;
        this.likeCount = likeCount;
        this.likedUsers = likedUsers;
    }

}


