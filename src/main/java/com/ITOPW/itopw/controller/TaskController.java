package com.ITOPW.itopw.controller;

import com.ITOPW.itopw.dto.Response;
import com.ITOPW.itopw.dto.TaskRequest;
import com.ITOPW.itopw.dto.response.BaseResponseDTO;
import com.ITOPW.itopw.dto.response.TaskResponseDTO;
import com.ITOPW.itopw.entity.Task;
import com.ITOPW.itopw.repository.TaskRepository;
import com.ITOPW.itopw.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/addtask")
    public ResponseEntity<Response> createTask(@RequestBody TaskRequest taskRequest) {
        System.out.println("TaskRequest recurring"+taskRequest.isRecurring());
        if (taskRequest.isRecurring()) {
            List<Task> recurringTasks = taskService.createRecurringTasks(taskRequest);
            return recurringTasks.isEmpty()
                    ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(400, "Bad Request", "주기적 업무 생성 실패", null))
                    : ResponseEntity.ok(new Response(201, "Created", "주기적 업무가 성공적으로 생성되었습니다.", recurringTasks));
        } else {
            //return taskService.createTask(taskRequest);//담당자 한명일때
            return taskService.addTaskWithAssignees(taskRequest);
        }
    }

    @PostMapping("/addTaskMultiple")
    public ResponseEntity<Response> addTask(@RequestBody TaskRequest taskRequest) {

        return taskService.addTaskWithAssignees(taskRequest);
    }

    @GetMapping("") // 전체 태스크 조회(프로젝트 ID 기반 필터링)
    public ResponseEntity<Page<TaskResponseDTO>> getAllTasksByProjectId(
            @RequestParam List<String> projectIds,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<TaskResponseDTO> tasks = taskService.getTasksByProjectIds(projectIds, pageable);
        //List<TaskResponseDTO> tasks = taskService.getTasksByProjectIds(projectIds);

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
    public ResponseEntity<List<TaskResponseDTO>> getTasksByMonth(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam List<String> projectIds // 프로젝트 ID 목록을 배열로 받음
    ) {
        List<TaskResponseDTO> tasks = taskService.getTasksByMonthAndProjectIds(year, month, projectIds);
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
    public ResponseEntity<Page<TaskResponseDTO>> searchTasks(
            @RequestParam List<String> projectIds,
            @RequestParam(required = false) String itoProcessId,
            @RequestParam(required = false) String unit,
            @RequestParam(required = false) String assigneeId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate dueDate,
            @RequestParam(required = false) String taskName,
            @RequestParam int page,
            @RequestParam int size
    ) {

        System.out.println(projectIds.toString());
        Pageable pageable = PageRequest.of(page, size);
        Page<TaskResponseDTO> tasks = taskService.searchTasks(projectIds, itoProcessId, unit, assigneeId, startDate, dueDate, taskName, pageable);
        //List<TaskResponseDTO> tasks = taskService.getTasksByProjectIds(projectIds);

        return tasks.isEmpty()
                ? ResponseEntity.noContent().build() // 204 No Content
                : ResponseEntity.ok(tasks); // 200 OK
    }


    //Task 완료 처리
    @PostMapping("/{taskId}/complete")
    public ResponseEntity<String> completeTask(@PathVariable String taskId) {
        boolean success = taskService.completeTask(taskId);
        if (success) {
            return ResponseEntity.ok("Task상태 변경완료");
        } else {
            return ResponseEntity.badRequest().body("실패");
        }
    }

    // 확인완료 버튼 클릭 후 상태 변경
    @GetMapping("/{taskId}/confirm")
    public ResponseEntity<Void> confirmTask(@PathVariable String taskId, @RequestParam String confirmation) {
        try {
            // 해당 taskId로 작업 조회
            Task task = taskRepository.findByTaskId(taskId).orElseThrow(() -> new Exception("Task not found"));

            // 확인 버튼이 'Y'일 경우, assigneeConfirmation을 Y로 업데이트
            if ("Y".equals(confirmation)) {
                task.setAssigneeConfirmation("Y");
                taskRepository.save(task);  // DB에 반영
            }

            // 리다이렉트 URL 설정
            URI redirectUri = URI.create("http://210.127.59.84:31427/task/detail?taskId=" + taskId);

            // 리다이렉트 응답 반환
            return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();
        } catch (Exception e) {
            // 실패 시 HTTP 500 응답 반환
            return ResponseEntity.status(500).build();
        }
    }


}

