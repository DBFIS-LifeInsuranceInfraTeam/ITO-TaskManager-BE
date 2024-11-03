package com.ITOPW.itopw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "statistics_view")
public class Statistics {

    @Id
    @Column(name = "project_id")
    private String projectId;

    @Column(name = "month")
    private LocalDate month;

    @Column(name = "total_tasks")
    private Integer totalTasks;

    @Column(name = "before_percentage")
    private BigDecimal beforePercentage;

    @Column(name = "progress_percentage")
    private BigDecimal progressPercentage;

    @Column(name = "complete_percentage")
    private BigDecimal completePercentage;

    @Column(name = "delayed_percentage")
    private BigDecimal delayedPercentage;

    @Column(name = "previous_before_percentage")
    private BigDecimal previousBeforePercentage;

    @Column(name = "previous_progress_percentage")
    private BigDecimal previousProgressPercentage;

    @Column(name = "previous_complete_percentage")
    private BigDecimal previousCompletePercentage;

    @Column(name = "previous_delayed_percentage")
    private BigDecimal previousDelayedPercentage;

}

