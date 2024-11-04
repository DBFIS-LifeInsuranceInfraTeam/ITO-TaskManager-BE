package com.ITOPW.itopw.service;

import com.ITOPW.itopw.entity.Statistics;
import com.ITOPW.itopw.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    public StatisticsResponse calculateStatistics(List<String> projectIds, String month) {
        YearMonth requestedMonth = YearMonth.parse(month);
        String previousMonth = requestedMonth.minusMonths(1).toString(); // 이전 달을 "YYYY-MM" 형식의 문자열로 변환
        String currentMonth = requestedMonth.toString();

        System.out.println(previousMonth);
        int totalCurrentTasks = 0;
        int totalPreviousTasks = 0;
        int totalCurrentBeforeTasks = 0;
        int totalPreviousBeforeTasks = 0;

        for (String projectId : projectIds) {
            // 현재 월 및 이전 월 데이터 조회
            Optional<Statistics> currentStats = statisticsRepository.findByProjectIdAndMonth(projectId, currentMonth);
            Optional<Statistics> previousStats = statisticsRepository.findByProjectIdAndMonth(projectId, "2024-10");

            System.out.println("Project ID: " + projectId);
            System.out.println("Current Month: " + currentMonth + ", Current Stats: " + currentStats);
            System.out.println("Previous Month: " + previousMonth + ", Previous Stats: " + previousStats);

            // 현재 월 데이터 합산
            if (currentStats.isPresent()) {
                Statistics current = currentStats.get();
                totalCurrentTasks += current.getTotalTasks();
                totalCurrentBeforeTasks += current.getTotalTasks() * current.getBeforePercentage().divide(BigDecimal.valueOf(100)).intValue();
            }

            // 이전 월 데이터 합산
            if (previousStats.isPresent()) {
                Statistics previous = previousStats.get();
                totalPreviousTasks += previous.getTotalTasks();
                totalPreviousBeforeTasks += previous.getTotalTasks() * previous.getBeforePercentage().divide(BigDecimal.valueOf(100)).intValue();
            }
        }

        // 비율 계산
        BigDecimal currentBeforePercentage = totalCurrentTasks > 0
                ? BigDecimal.valueOf((double) totalCurrentBeforeTasks / totalCurrentTasks).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        BigDecimal previousBeforePercentage = totalPreviousTasks > 0
                ? BigDecimal.valueOf((double) totalPreviousBeforeTasks / totalPreviousTasks).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        BigDecimal beforeIncrease = currentBeforePercentage.subtract(previousBeforePercentage).setScale(2, RoundingMode.HALF_UP);

        // 통합된 통계 생성
        Statistics combinedStatistics = Statistics.builder()
                .projectId(null) // 통합 결과로 개별 projectId는 설정하지 않음
                .month(currentMonth)
                .totalTasks(totalCurrentTasks)
                .beforePercentage(currentBeforePercentage.multiply(BigDecimal.valueOf(100)))
                .build();

        // 최종 결과 출력
        System.out.println("Combined Statistics for " + month + ": " + combinedStatistics);
        System.out.println("Before Increase: " + beforeIncrease);

        // 필요에 따라 통합된 통계를 반환하거나 추가 로직을 수행
        return new StatisticsResponse(combinedStatistics, beforeIncrease, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }


    public static class StatisticsResponse {
        public final Statistics statistics;
        public final BigDecimal beforeIncrease;
        public final BigDecimal progressIncrease;
        public final BigDecimal completeIncrease;
        public final BigDecimal delayedIncrease;

        public StatisticsResponse(Statistics statistics, BigDecimal beforeIncrease, BigDecimal progressIncrease, BigDecimal completeIncrease, BigDecimal delayedIncrease) {
            this.statistics = statistics;
            this.beforeIncrease = beforeIncrease;
            this.progressIncrease = progressIncrease;
            this.completeIncrease = completeIncrease;
            this.delayedIncrease = delayedIncrease;
        }
    }
}

