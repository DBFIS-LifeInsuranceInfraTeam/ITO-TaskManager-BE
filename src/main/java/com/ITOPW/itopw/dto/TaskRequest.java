package com.ITOPW.itopw.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {
    private String projectId;
    private String taskName;
    private String description;
    private String assigneeId;
    private LocalDate createdDate;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Integer frequencyId;
    private Integer status;
    private String itoProcessId;
    private String assigneeConfirmation;
}