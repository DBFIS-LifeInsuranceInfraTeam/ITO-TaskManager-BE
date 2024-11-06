package com.ITOPW.itopw.service;

import com.ITOPW.itopw.entity.Task;
import com.ITOPW.itopw.entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskSpecification {

    private final UserService userService;

    public TaskSpecification(UserService userService) {
        this.userService = userService;
    }


    public Specification<Task> buildSpecification(List<String> projectIds, String itoProcessId, String unit, String assigneeId, LocalDate startDate, LocalDate dueDate, String taskName) {
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


            System.out.println(unit);
            // Project ID와 Unit 조건 결합
            if (projectIds != null && !projectIds.isEmpty()) {
                List<User> usersInProjects = userService.getUsersByProjectIdListAndUnit(projectIds, unit);
                List<String> userIds = new ArrayList<>();
                for (User user : usersInProjects) {
                    userIds.add(user.getUserId());
                }
                predicates.add(root.get("assigneeId").in(userIds));


//                if (!userIdsWithUnit.isEmpty()) {
//                    predicates.add(root.get("assigneeId").in(userIdsWithUnit));
//                } else {
//                    // 유닛에 해당하는 사용자가 없는 경우, 빈 결과를 반환하도록 설정
//                    return criteriaBuilder.disjunction();
//                }
            }

            return criteriaBuilder.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }
}
