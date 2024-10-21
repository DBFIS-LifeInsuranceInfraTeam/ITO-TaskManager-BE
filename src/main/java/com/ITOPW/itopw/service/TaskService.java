package com.ITOPW.itopw.service;

import com.ITOPW.itopw.entity.Task;
import com.ITOPW.itopw.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional // 이 어노테이션 추가
    public Task createTask(Task task) {
        return taskRepository.save(task); // 데이터 삽입
    }

    public Optional<Task> getTaskById(Integer taskId) {
        return taskRepository.findById(taskId); // ID로 태스크 조회
    }
}
