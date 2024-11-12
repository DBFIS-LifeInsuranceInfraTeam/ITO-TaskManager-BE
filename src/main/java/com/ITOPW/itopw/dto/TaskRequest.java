package com.ITOPW.itopw.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {
    private String projectId;
    private String taskName;
    private String description;
    private String assigneeId;

    private List<String> assigneeIds; // 선택된 사용자 ID 리스트

    private LocalDate createdDate;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Integer frequencyId;
    private Integer status;
    private String itoProcessId;
    private String assigneeConfirmation;
    private String createdBy;
    // 주기적 업무 관련 필드
    private boolean recurring;            // 주기적 업무 여부
    private String frequencyType;         // 주기 유형: "daily", "weekly", "monthly", "yearly"
    private int frequencyInterval;        // n일, n주, n개월 등의 간격
    private boolean hasEndDate;           // 종료일 여부
    private LocalDate endDate;            // 종료일 설정

    // 매주 반복
    private List<DayOfWeek> weeklyDay;          // 매주 반복 시 특정 요일 선택 (월, 화 등)

    // 매월 반복
    private Integer monthlyDayOfMonth;    // 매월 반복 시 특정 일자 선택 (예: 매월 6일)
    private Integer monthlyWeekOfMonth;   // 매월 반복 시 특정 주차 (예: 첫째 주)
    private DayOfWeek monthlyDayOfWeek;   // 매월 반복 시 특정 요일 선택 (예: 첫째 주 수요일)

    // 매년 반복
    private Integer yearlyMonth;          // 매년 반복 시 특정 월 (예: 11월)
    private Integer yearlyDayOfMonth;     // 매년 반복 시 특정 일자 (예: 6일)
    private Integer yearlyWeekOfMonth;    // 매년 반복 시 특정 주차 (예: 첫째 주)
    private DayOfWeek yearlyDayOfWeek;    // 매년 반복 시 특정 요일 (예: 첫째 주 수요일)



}