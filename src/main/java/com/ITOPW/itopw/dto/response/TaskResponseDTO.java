package com.ITOPW.itopw.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.ITOPW.itopw.dto.AssigneeDTO;
import com.ITOPW.itopw.entity.Assignee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskResponseDTO {
    private String taskId;
    private String projectId;
    private String taskName;
    private String description;
    // Assignee 객체

    //private Assignee assignee; // Assignee 객체 추가
    private List<Assignee> assignees;
    public static class Assignee {
        private String assigneeId;
        private String assigneeName;
        private String assigneeProfile;

        public Assignee() {}

        public Assignee(String assigneeId, String assigneeName, String assigneeProfile) {
            this.assigneeId = assigneeId;
            this.assigneeName = assigneeName;
            this.assigneeProfile = assigneeProfile;
        }

        public String getAssigneeId() {
            return assigneeId;
        }

        public void setAssigneeId(String assigneeId) {
            this.assigneeId = assigneeId;
        }

        public String getAssigneeName() {
            return assigneeName;
        }

        public void setAssigneeName(String assigneeName) {
            this.assigneeName = assigneeName;
        }

        public String getAssigneeProfile() {
            return assigneeProfile;
        }

        public void setAssigneeProfile(String assigneeProfile) {
            this.assigneeProfile = assigneeProfile;
        }
    }

    private LocalDate createdDate;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Integer frequencyId;
    private Integer commentCount;
    private Integer status;
    private String itoProcessId;
    private String assigneeConfirmation;
    private String createdBy;
    private List<CommentResponseDTO> comments;

    public TaskResponseDTO(String taskId, String projectId, String taskName, String description, List<Assignee> assignees, LocalDate createdDate, LocalDate startDate, LocalDate dueDate, Integer frequencyId, Integer commentCount, Integer status, String itoProcessId, String assigneeConfirmation, List<CommentResponseDTO> comments, String createdBy) {
        this.taskId = taskId;
        this.projectId = projectId;
        this.taskName = taskName;
        this.description = description;
        this.assignees = assignees;

        this.createdDate = createdDate;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.frequencyId = frequencyId;
        this.commentCount = commentCount;
        this.status = status;
        this.itoProcessId = itoProcessId;
        this.assigneeConfirmation = assigneeConfirmation;
        this.comments = comments;
        this.createdBy = createdBy;
    }

    public TaskResponseDTO(String taskId, String taskName, List<Assignee> assignees, LocalDate dueDate, Integer status, String itoProcessId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.assignees = assignees;

        this.dueDate = dueDate;
        this.status = status;
        this.itoProcessId = itoProcessId;
    }

//    public TaskResponseDTO(String taskId, String projectId, String taskName, String description, Assignee assignee, LocalDate createdDate, LocalDate startDate, LocalDate dueDate, Integer frequencyId, Integer commentCount, Integer status, String itoProcessId, String assigneeConfirmation, List<CommentResponseDTO> comments,String createdBy) {
//        this.taskId = taskId;
//        this.projectId = projectId;
//        this.taskName = taskName;
//        this.description = description;
//        this.assignee = assignee;
//
//        this.createdDate = createdDate;
//        this.startDate = startDate;
//        this.dueDate = dueDate;
//        this.frequencyId = frequencyId;
//        this.commentCount = commentCount;
//        this.status = status;
//        this.itoProcessId = itoProcessId;
//        this.assigneeConfirmation = assigneeConfirmation;
//       this.comments = comments;
//       this.createdBy = createdBy;
//    }

    //private List<AssigneeResponse> assignees; // 담당자 리스트

    public TaskResponseDTO() {

    }
}


