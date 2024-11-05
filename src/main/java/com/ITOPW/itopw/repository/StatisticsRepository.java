package com.ITOPW.itopw.repository;

import com.ITOPW.itopw.entity.Statistics;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, String> {

    @Query("SELECT s FROM Statistics s WHERE s.projectId IN :projectId AND s.month = :month")
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "false")) // 특정 쿼리 캐싱 비활성화
    List<Statistics> findByProjectIdAndMonth(List<String> projectId, String month);
}
