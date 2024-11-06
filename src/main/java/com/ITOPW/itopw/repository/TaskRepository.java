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
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task>  {
    // 기본적으로 JpaRepository에서 기본 CRUD 메서드를 제공합니다.
    // 추가적인 쿼리가 필요하면 여기에 메서드를 정의할 수 있습니다.
    List<Task> findByDueDateBetween(LocalDate startDate, LocalDate endDate);

    Optional<Task> findByTaskId(String taskId);

    boolean existsByTaskId(String taskId);

    List<Task> findByDueDate(LocalDate nextDay);


    List<Task> findByProjectId(String projectId);

    //List<Task> findByProjectIdIn(List<String> projectIds);
    Page<Task> findByProjectIdIn(List<String> projectIds, Pageable pageable);
    Page<Task> findAll(Specification<Task> spec, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE YEAR(t.dueDate) = :year AND MONTH(t.dueDate) = :month AND t.projectId IN :projectIds")
    List<Task> findTasksByMonthAndProjectIds(@Param("year") int year, @Param("month") int month, @Param("projectIds") List<String> projectIds);


    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.status = 1 WHERE t.startDate <= :today AND t.status = 0")
    void updateStatusForPastTasks(LocalDate today);

}