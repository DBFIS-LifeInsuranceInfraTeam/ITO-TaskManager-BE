package com.ITOPW.itopw.service;

import com.ITOPW.itopw.entity.Task;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskSpecification {
    public static Specification<Task> buildSpecification(String itoProcessId, String assigneeId, LocalDate startDate, LocalDate dueDate, String taskName) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (itoProcessId != null && !itoProcessId.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("itoProcessId"), itoProcessId));
            }
            if (assigneeId != null && !assigneeId.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("assigneeId"), assigneeId));
            }
            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate));
            }
            if (dueDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dueDate"), dueDate));
            }
            if (taskName != null && !taskName.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("taskName"), "%" + taskName + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }
}
