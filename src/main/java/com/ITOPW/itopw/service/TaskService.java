package com.ITOPW.itopw.service;

import com.ITOPW.itopw.entity.Task;
import com.ITOPW.itopw.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
