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

import java.time.LocalDate;
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

    @GetMapping("") // 전체 태스크 조회(프로젝트 ID 기반 필터링)
    public ResponseEntity<List<Task>> getAllTasksByProjectId(@RequestParam List<String> projectIds) {
        List<Task> tasks = taskService.getTasksByProjectIds(projectIds);
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

    // 월별 업무 조회 (프로젝트 ID 기반 필터링 추가)
    @GetMapping("/monthly")
    public ResponseEntity<List<Task>> getTasksByMonth(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam List<String> projectIds // 프로젝트 ID 목록을 배열로 받음
    ) {
        List<Task> tasks = taskService.getTasksByMonthAndProjectIds(year, month, projectIds);
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

    // 업무 검색(ito프로세스, 업무명, 기간, 담당자)
    @GetMapping("/search")
    public List<Task> searchTasks(
            @RequestParam(required = false) String itoProcessId,
            @RequestParam(required = false) String assigneeId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate dueDate,
            @RequestParam(required = false) String taskName
    ) {
        return taskService.searchTasks(itoProcessId, assigneeId, startDate, dueDate, taskName);
    }
}
