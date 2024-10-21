package com.ITOPW.itopw.controller;

import org.springframework.http.HttpStatus;
import com.ITOPW.itopw.entity.Task;
import com.ITOPW.itopw.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        try {
            Task savedTask = taskService.createTask(task);
            return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // 에러 발생 시 500 반환
        }
    }

    @GetMapping("/{id}") // ID로 태스크 조회
    public ResponseEntity<Task> getTaskById(@PathVariable Integer id) {
        Optional<Task> taskData = taskService.getTaskById(id);
        return taskData.map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 태스크가 없으면 404 반환
    }

    @GetMapping("/all") // 전체 태스크 조회
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 데이터가 없으면 204 반환
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK); // 데이터가 있으면 200 반환
    }

    @DeleteMapping("/{id}") // ID로 태스크 삭제
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable Integer id) {
        try {
            if (taskService.deleteTask(id)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 삭제 성공 시 204 반환
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 태스크가 없으면 404 반환
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 에러 발생 시 500 반환
        }
    }

    @PutMapping("/{id}") // ID로 태스크 수정
    public ResponseEntity<Task> updateTask(@PathVariable Integer id, @RequestBody Task task) {
        try {
            Optional<Task> updatedTask = taskService.updateTask(id, task);
            return updatedTask.map(t -> new ResponseEntity<>(t, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

