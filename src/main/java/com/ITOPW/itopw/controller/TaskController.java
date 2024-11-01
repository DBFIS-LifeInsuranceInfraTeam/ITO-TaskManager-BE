package com.ITOPW.itopw.controller;

import com.ITOPW.itopw.dto.Response;
import com.ITOPW.itopw.dto.TaskRequest;
import com.ITOPW.itopw.dto.response.BaseResponseDTO;
import com.ITOPW.itopw.dto.response.TaskResponseDTO;
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

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable String taskId) {
        TaskResponseDTO taskResponse = taskService.getTaskById(taskId);
        return taskResponse != null
                ? ResponseEntity.ok(taskResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 월별 업무 조회
    @GetMapping("/monthly")
    public ResponseEntity<List<Task>> getTasksByMonth(
            @RequestParam int year,
            @RequestParam int month
    ) {
        List<Task> tasks = taskService.getTasksByMonth(year, month);
        return tasks.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/{id}") // ID로 태스크 삭제
    public ResponseEntity<Response> deleteTask(@PathVariable String id) { // String으로 수정
        return taskService.deleteTask(id);
    }

    @PutMapping("/{id}") // ID로 태스크 수정
    public ResponseEntity<Response> updateTask(@PathVariable String id, @RequestBody TaskRequest taskRequest) { // String으로 수정
        return taskService.updateTask(id, taskRequest);
    }
}
