package com.ITOPW.itopw.controller;

import com.ITOPW.itopw.entity.Statistics;
import com.ITOPW.itopw.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getStatisticsByProjectIdAndMonth(
            @PathVariable String projectId,
            @RequestParam(required = false) String month) {

        LocalDate requestedMonth = month != null ? YearMonth.parse(month).atDay(1) : LocalDate.now().withDayOfMonth(1);

        Optional<Statistics> statisticsOpt = statisticsService.getStatisticsByProjectIdAndMonth(projectId, requestedMonth);

        if (statisticsOpt.isPresent()) {
            Statistics statistics = statisticsOpt.get();

            // 이전 월 대비 증감률 계산 (원하는 형식으로 반환)
            double beforeIncrease = statistics.getBeforePercentage().subtract(statistics.getPreviousBeforePercentage()).doubleValue();
            double progressIncrease = statistics.getProgressPercentage().subtract(statistics.getPreviousProgressPercentage()).doubleValue();
            double completeIncrease = statistics.getCompletePercentage().subtract(statistics.getPreviousCompletePercentage()).doubleValue();
            double delayedIncrease = statistics.getDelayedPercentage().subtract(statistics.getPreviousDelayedPercentage()).doubleValue();

            // 응답 데이터 생성
            return ResponseEntity.ok(new StatisticsResponse(
                    statistics,
                    beforeIncrease, progressIncrease, completeIncrease, delayedIncrease
            ));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 내부 클래스: API 응답을 위한 DTO
    private static class StatisticsResponse {
        public final Statistics statistics;
        public final double beforeIncrease;
        public final double progressIncrease;
        public final double completeIncrease;
        public final double delayedIncrease;

        public StatisticsResponse(Statistics statistics, double beforeIncrease, double progressIncrease, double completeIncrease, double delayedIncrease) {
            this.statistics = statistics;
            this.beforeIncrease = beforeIncrease;
            this.progressIncrease = progressIncrease;
            this.completeIncrease = completeIncrease;
            this.delayedIncrease = delayedIncrease;
        }
    }
}
