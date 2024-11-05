package com.ITOPW.itopw.service;

import com.ITOPW.itopw.dto.Response;
import com.ITOPW.itopw.dto.TaskRequest;
import com.ITOPW.itopw.dto.response.CommentResponseDTO;
import com.ITOPW.itopw.dto.response.TaskResponseDTO;
import com.ITOPW.itopw.entity.Task;
import com.ITOPW.itopw.entity.User;
import com.ITOPW.itopw.repository.TaskRepository;
import com.ITOPW.itopw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    private UserRepository userRepository;

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


        // UUID를 이용해 taskId 생성
        String generatedTaskId = UUID.randomUUID().toString();

        // 태스크가 이미 존재하는지 확인
        if (taskRepository.existsByTaskId(generatedTaskId)) {
            return new ResponseEntity<>(new Response(409, "Conflict", "업무가 이미 존재합니다.", null), HttpStatus.CONFLICT);
        }

        // 새 태스크 생성
        Task newTask = new Task();
        newTask.setTaskId(generatedTaskId);
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



//    @Transactional(readOnly = true)
//    @EntityGraph(attributePaths = {"comments"})
//    public Task getTaskById(String taskId) {
//        return taskRepository.findByTaskId(taskId).orElse(null);
//    }

    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(String taskId) {
        Task task = taskRepository.findByTaskId(taskId).orElse(null);
        if (task == null) return null;

        List<CommentResponseDTO> comments = task.getComments().stream()
                .map(comment -> new CommentResponseDTO(comment.getCommentId(), comment.getCommenterId(),comment.getCommentContent(), comment.getCreateDate(), comment.getLikeCount(), comment.getLikedUsers()))
                .collect(Collectors.toList());

        TaskResponseDTO taskDto = new TaskResponseDTO();

        // Task 정보 설정
        taskDto.setTaskId(task.getTaskId());
        taskDto.setProjectId(task.getProjectId());
        taskDto.setTaskName(task.getTaskName());
        taskDto.setDescription(task.getDescription());
        taskDto.setCreatedDate(task.getCreatedDate());
        taskDto.setStartDate(task.getStartDate());
        taskDto.setDueDate(task.getDueDate());
        taskDto.setStatus(task.getStatus());
        taskDto.setItoProcessId(task.getItoProcessId());
        taskDto.setAssigneeConfirmation(task.getAssigneeConfirmation());
        taskDto.setComments(comments);


        Optional<User> assignee = userRepository.findById(task.getAssigneeId());
        if (assignee.isPresent()) {
            taskDto.setAssigneeId(assignee.get().getUserId());
            taskDto.setAssigneeName(assignee.get().getName());
            taskDto.setAssigneeProfile(assignee.get().getPhoto());
        }
        return taskDto;
    }

    public List<Task> getTasksByMonthAndProjectIds(int year, int month, List<String> projectIds) {
        // 데이터베이스 쿼리를 통해 해당 연도, 월, 프로젝트 ID에 맞는 태스크를 필터링하여 반환
        return taskRepository.findTasksByMonthAndProjectIds(year, month, projectIds);
    }


    public ResponseEntity<Response> deleteTask(String taskId) {
        if (taskId == null) {
            // 유효하지 않은 ID가 제공된 경우
            return new ResponseEntity<>(new Response(400, "Bad Request", "유효하지 않은 요청입니다. ID를 확인하세요.", null), HttpStatus.BAD_REQUEST);
        }

        Optional<Task> task = taskRepository.findByTaskId(taskId);
        if (task.isPresent()) {
            taskRepository.delete(task.get());
            return new ResponseEntity<>(new Response(200, "OK", "업무가 성공적으로 삭제되었습니다.", null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response(404, "Not Found", "해당 업무를 찾을 수 없습니다.", null), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Response> updateTask(String taskId, TaskRequest taskRequest) {
        // 필수 필드 유효성 검사
        if (taskRequest.getProjectId() == null ||
                taskRequest.getTaskName() == null || taskRequest.getAssigneeId() == null ||
                taskRequest.getCreatedDate() == null || taskRequest.getStartDate() == null ||
                taskRequest.getStatus() == null || taskRequest.getItoProcessId() == null ||
                taskRequest.getAssigneeConfirmation() == null) {

            // 필수 필드가 누락된 경우 400 Bad Request 응답 반환
            return new ResponseEntity<>(new Response(400, "Bad Request", "잘못된 요청입니다. 필수 필드를 확인하세요.", null), HttpStatus.BAD_REQUEST);
        }

        Optional<Task> existingTask = taskRepository.findByTaskId(taskId);
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
            updatedTask.setStatus(taskRequest.getStatus());
            updatedTask.setItoProcessId(taskRequest.getItoProcessId());
            updatedTask.setAssigneeConfirmation(taskRequest.getAssigneeConfirmation());

            Task savedTask = taskRepository.save(updatedTask);
            return new ResponseEntity<>(new Response(200, "OK", "업무가 성공적으로 수정되었습니다.", savedTask), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response(404, "Not Found", "해당 업무를 찾을 수 없습니다.", null), HttpStatus.NOT_FOUND);
    }


    @Transactional(readOnly = true)
    public List<Task> searchTasks(String itoProcessId, String assigneeId, LocalDate startDate, LocalDate dueDate, String taskName) {
        Specification<Task> spec = TaskSpecification.buildSpecification(itoProcessId, assigneeId, startDate, dueDate, taskName);
        return taskRepository.findAll(spec);
    }


    public List<TaskResponseDTO> getTasksByProjectIds(List<String> projectIds) {

        List<Task> tasks = taskRepository.findByProjectIdIn(projectIds);
        List<TaskResponseDTO> taskDTOs = new ArrayList<>();

        for (Task task : tasks) {
            Optional<User> assignee = userRepository.findById(task.getAssigneeId());
            TaskResponseDTO dto = new TaskResponseDTO();

            // Task 정보 설정
            dto.setTaskId(task.getTaskId());
            dto.setProjectId(task.getProjectId());
            dto.setTaskName(task.getTaskName());
            dto.setDescription(task.getDescription());
            dto.setCreatedDate(task.getCreatedDate());
            dto.setStartDate(task.getStartDate());
            dto.setDueDate(task.getDueDate());
            dto.setStatus(task.getStatus());
            dto.setItoProcessId(task.getItoProcessId());
            dto.setAssigneeConfirmation(task.getAssigneeConfirmation());

            // Assignee 정보 설정
            if (assignee.isPresent()) {
                dto.setAssigneeId(assignee.get().getUserId());
                dto.setAssigneeName(assignee.get().getName());
                dto.setAssigneeProfile(assignee.get().getPhoto());
            }
            taskDTOs.add(dto);
        }
        //
        return taskDTOs;
        //return taskRepository.findByProjectIdIn(projectIds);
    }
}