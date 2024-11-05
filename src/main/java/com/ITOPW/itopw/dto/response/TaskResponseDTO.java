package com.ITOPW.itopw.dto.response;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskResponseDTO {
    private String taskId;
    private String projectId;
    private String taskName;
    private String description;
    private String assigneeId;
    private String assigneeName;
    private String assigneeProfile;
    private LocalDate createdDate;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Integer frequencyId;
    private Integer commentCount;
    private Integer status;
    private String itoProcessId;
    private String assigneeConfirmation;
    private List<CommentResponseDTO> comments;

    public TaskResponseDTO(String taskId, String projectId, String taskName, String description, String assigneeId, LocalDate createdDate, LocalDate startDate, LocalDate dueDate, Integer frequencyId, Integer commentCount, Integer status, String itoProcessId, String assigneeConfirmation, List<CommentResponseDTO> comments) {
        this.taskId = taskId;
        this.projectId = projectId;
        this.taskName = taskName;
        this.description = description;
        this.assigneeId = assigneeId;

        this.createdDate = createdDate;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.frequencyId = frequencyId;
        this.commentCount = commentCount;
        this.status = status;
        this.itoProcessId = itoProcessId;
        this.assigneeConfirmation = assigneeConfirmation;
        this.comments = comments;
    }

    public TaskResponseDTO(String taskId, String projectId, String taskName, String description, String assigneeId, String assigneeName, String assigneeProfile, LocalDate createdDate, LocalDate startDate, LocalDate dueDate, Integer frequencyId, Integer commentCount, Integer status, String itoProcessId, String assigneeConfirmation, List<CommentResponseDTO> comments) {
        this.taskId = taskId;
        this.projectId = projectId;
        this.taskName = taskName;
        this.description = description;
        this.assigneeId = assigneeId;
        this.assigneeName = assigneeName;
        this.assigneeProfile = assigneeProfile;
        this.createdDate = createdDate;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.frequencyId = frequencyId;
        this.commentCount = commentCount;
        this.status = status;
        this.itoProcessId = itoProcessId;
        this.assigneeConfirmation = assigneeConfirmation;
        this.comments = comments;
    }

    public TaskResponseDTO() {

    }
}


