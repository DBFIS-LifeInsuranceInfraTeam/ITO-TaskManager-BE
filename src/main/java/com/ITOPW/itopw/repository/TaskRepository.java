package com.ITOPW.itopw.repository;

import com.ITOPW.itopw.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    // 기본적으로 JpaRepository에서 기본 CRUD 메서드를 제공합니다.
    // 추가적인 쿼리가 필요하면 여기에 메서드를 정의할 수 있습니다.
    List<Task> findByDueDateBetween(LocalDate startDate, LocalDate endDate);
    List<Task> findByDueDate(LocalDate dueDate);
}