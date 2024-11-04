package com.ITOPW.itopw.repository;

import com.ITOPW.itopw.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, String> {

    Optional<Statistics> findByProjectIdAndMonth(String projectId, String month);
}
