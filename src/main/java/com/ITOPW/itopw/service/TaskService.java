package com.ITOPW.itopw.service;

import com.ITOPW.itopw.entity.Task;
import com.ITOPW.itopw.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
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

    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // 전체 태스크를 반환
    }

    public boolean deleteTask(Integer id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            taskRepository.delete(task.get());
            return true; // 삭제 성공
        }
        return false; // 태스크가 존재하지 않음
    }

    public Optional<Task> updateTask(Integer id, Task task) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent()) {
            Task updatedTask = existingTask.get();
            // 기존 Task의 정보를 업데이트합니다.
            updatedTask.setTaskName(task.getTaskName());
            updatedTask.setAssigneeId(task.getAssigneeId());
            updatedTask.setItoProcessId(task.getItoProcessId());
            updatedTask.setStartDate(task.getStartDate());
            updatedTask.setDueDate(task.getDueDate());
            updatedTask.setDescription(task.getDescription());
            // 필요에 따라 다른 필드도 업데이트할 수 있습니다.

            return Optional.of(taskRepository.save(updatedTask));
        }
        return Optional.empty(); // 태스크가 존재하지 않음
    }
}
