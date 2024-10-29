package com.ITOPW.itopw.controller;

import com.ITOPW.itopw.dto.Response;
import com.ITOPW.itopw.dto.TaskRequest;
import com.ITOPW.itopw.entity.Task;
import com.ITOPW.itopw.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Response> createTask(@RequestBody TaskRequest taskRequest) {
        return taskService.createTask(taskRequest);
    }

    @GetMapping("/all") // 전체 태스크 조회
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return tasks.isEmpty()
                ? ResponseEntity.noContent().build() // 204 No Content
                : ResponseEntity.ok(tasks); // 200 OK
    }

    @DeleteMapping("/{id}") // ID로 태스크 삭제
    public ResponseEntity<Response> deleteTask(@PathVariable Integer id) {
        return taskService.deleteTask(id);
    }

    @PutMapping("/{id}") // ID로 태스크 수정
    public ResponseEntity<Response> updateTask(@PathVariable Integer id, @RequestBody TaskRequest taskRequest) {
        return taskService.updateTask(id, taskRequest);
    }
}
