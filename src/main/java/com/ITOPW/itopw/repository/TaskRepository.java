package com.ITOPW.itopw.repository;

import com.ITOPW.itopw.entity.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {
    // 기본 CRUD 메서드와 추가적인 쿼리 메서드

    List<Task> findByDueDateBetween(LocalDate startDate, LocalDate endDate);

    Optional<Task> findByTaskId(String taskId);

    boolean existsByTaskId(String taskId);

    List<Task> findByDueDate(LocalDate nextDay);

    List<Task> findByProjectId(String projectId);

    Page<Task> findByProjectIdIn(List<String> projectIds, Pageable pageable);

    Page<Task> findAll(Specification<Task> spec, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE YEAR(t.dueDate) = :year AND MONTH(t.dueDate) = :month AND t.projectId IN :projectIds")
    List<Task> findTasksByMonthAndProjectIds(@Param("year") int year, @Param("month") int month, @Param("projectIds") List<String> projectIds);

    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.status = 1 WHERE t.startDate <= :today AND t.status = 0")
    void updateStatusForPastTasks(LocalDate today);

    // Task 완료시 status 2로 변경
    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.status = 2 WHERE t.taskId = :taskId AND t.status IN (0, 1, 3)")
    int updateStatusToCompleted(@Param("taskId") String taskId);
}
