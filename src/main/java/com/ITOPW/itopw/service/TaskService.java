package com.ITOPW.itopw.service;

import com.ITOPW.itopw.dto.Response;
import com.ITOPW.itopw.dto.TaskRequest;
import com.ITOPW.itopw.entity.Task;
import com.ITOPW.itopw.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public ResponseEntity<Response> createTask(TaskRequest taskRequest) {
        // 필수 필드 검증
        if (taskRequest.getTaskName() == null ||
                taskRequest.getAssigneeId() == null ||
                taskRequest.getStartDate() == null ||
                taskRequest.getCreatedDate() == null ||
                taskRequest.getStatus() == null ||
                taskRequest.getItoProcessId() == null) {

            return new ResponseEntity<>(new Response(400, "Bad Request", "필수 필드가 누락되었습니다.", null), HttpStatus.BAD_REQUEST);
        }

        // 태스크가 이미 존재하는지 확인
        if (taskRepository.existsById(taskRequest.getTaskId())) {
            return new ResponseEntity<>(new Response(409, "Conflict", "업무가 이미 존재합니다.", null), HttpStatus.CONFLICT);
        }

        // 새 태스크 생성
        Task newTask = new Task();
        newTask.setTaskId(taskRequest.getTaskId());
        newTask.setProjectId(taskRequest.getProjectId());
        newTask.setTaskName(taskRequest.getTaskName());
        newTask.setDescription(taskRequest.getDescription());
        newTask.setAssigneeId(taskRequest.getAssigneeId());
        newTask.setCreatedDate(taskRequest.getCreatedDate());
        newTask.setStartDate(taskRequest.getStartDate());
        newTask.setDueDate(taskRequest.getDueDate());
        newTask.setFrequencyId(taskRequest.getFrequencyId());
        newTask.setCommentCount(0); // 기본값 0
        newTask.setStatus(taskRequest.getStatus());
        newTask.setItoProcessId(taskRequest.getItoProcessId());
        newTask.setAssigneeConfirmation(taskRequest.getAssigneeConfirmation());

        Task createdTask = taskRepository.save(newTask);
        return new ResponseEntity<>(new Response(201, "Created", "업무가 성공적으로 생성되었습니다.", createdTask), HttpStatus.CREATED);
    }


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Integer id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<Task> getTasksByMonth(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        return taskRepository.findByDueDateBetween(startDate, endDate);
    }

    public ResponseEntity<Response> deleteTask(Integer id) {
        if (id == null || id <= 0) {
            // 유효하지 않은 ID가 제공된 경우
            return new ResponseEntity<>(new Response(400, "Bad Request", "유효하지 않은 요청입니다. ID를 확인하세요.", null), HttpStatus.BAD_REQUEST);
        }

        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            taskRepository.delete(task.get());
            return new ResponseEntity<>(new Response(200, "OK", "업무가 성공적으로 삭제되었습니다.", null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response(404, "Not Found", "해당 업무를 찾을 수 없습니다.", null), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Response> updateTask(Integer id, TaskRequest taskRequest) {
        // 필수 필드 유효성 검사
        if (taskRequest.getTaskId() == null || taskRequest.getProjectId() == null ||
                taskRequest.getTaskName() == null || taskRequest.getAssigneeId() == null ||
                taskRequest.getCreatedDate() == null || taskRequest.getStartDate() == null ||
                taskRequest.getStatus() == null || taskRequest.getItoProcessId() == null ||
                taskRequest.getAssigneeConfirmation() == null) {

            // 필수 필드가 누락된 경우 400 Bad Request 응답 반환
            return new ResponseEntity<>(new Response(400, "Bad Request", "잘못된 요청입니다. 필수 필드를 확인하세요.", null), HttpStatus.BAD_REQUEST);
        }

        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent()) {
            Task updatedTask = existingTask.get();

            // 모든 필드 업데이트
            updatedTask.setProjectId(taskRequest.getProjectId());
            updatedTask.setTaskName(taskRequest.getTaskName());
            updatedTask.setDescription(taskRequest.getDescription());
            updatedTask.setAssigneeId(taskRequest.getAssigneeId());
            updatedTask.setCreatedDate(taskRequest.getCreatedDate());
            updatedTask.setStartDate(taskRequest.getStartDate());
            updatedTask.setDueDate(taskRequest.getDueDate());
            updatedTask.setFrequencyId(taskRequest.getFrequencyId());
            updatedTask.setCommentCount(taskRequest.getCommentCount());
            updatedTask.setStatus(taskRequest.getStatus());
            updatedTask.setItoProcessId(taskRequest.getItoProcessId());
            updatedTask.setAssigneeConfirmation(taskRequest.getAssigneeConfirmation());

            Task savedTask = taskRepository.save(updatedTask);
            return new ResponseEntity<>(new Response(200, "OK", "업무가 성공적으로 수정되었습니다.", savedTask), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response(404, "Not Found", "해당 업무를 찾을 수 없습니다.", null), HttpStatus.NOT_FOUND);
    }

}
