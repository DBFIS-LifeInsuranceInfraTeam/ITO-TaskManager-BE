package com.ITOPW.itopw.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssigneeDTO {
    private String assigneeId;
    private String assigneeName;
    private String assigneeProfile;

    public AssigneeDTO(String assigneeId, String assigneeName, String assigneeProfile) {
        this.assigneeId = assigneeId;
        this.assigneeName = assigneeName;
        this.assigneeProfile = assigneeProfile;
    }
}
