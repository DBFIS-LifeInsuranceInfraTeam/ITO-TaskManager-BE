package com.ITOPW.itopw.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {
    private String taskId;
    private String commenterId;
    private String commentContent;
}