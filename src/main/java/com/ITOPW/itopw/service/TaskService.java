package com.ITOPW.itopw.service;

import com.ITOPW.itopw.dto.AssigneeDTO;
import com.ITOPW.itopw.dto.CommenterDTO;
import com.ITOPW.itopw.dto.Response;
import com.ITOPW.itopw.dto.TaskRequest;
import com.ITOPW.itopw.dto.response.AssigneeResponse;
import com.ITOPW.itopw.dto.response.CommentResponseDTO;
import com.ITOPW.itopw.dto.response.TaskResponseDTO;
import com.ITOPW.itopw.entity.Task;
import com.ITOPW.itopw.entity.User;
import com.ITOPW.itopw.repository.TaskRepository;
import com.ITOPW.itopw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
//
//@Service
//public class TaskService {
//    private final TaskRepository taskRepository;
//    private final TaskSpecification taskSpecification;
//    private final UserRepository userRepository;
//
//
//
//    @Autowired
//    public TaskService(TaskRepository taskRepository, TaskSpecification taskSpecification, UserRepository userRepository) {
//        this.taskRepository = taskRepository;
//        this.taskSpecification = taskSpecification;
//        this.userRepository = userRepository;
//    }
//
//    @Transactional
//    public ResponseEntity<Response> createTask(TaskRequest taskRequest) {
//        // 필수 필드 검증
//        if (taskRequest.getTaskName() == null ||
//                taskRequest.getAssigneeId() == null ||
//                taskRequest.getStartDate() == null ||
//                taskRequest.getCreatedDate() == null ||
//                taskRequest.getStatus() == null ||
//                taskRequest.getItoProcessId() == null) {
//
//            return new ResponseEntity<>(new Response(400, "Bad Request", "필수 필드가 누락되었습니다.", null), HttpStatus.BAD_REQUEST);
//        }
//
//
//        // UUID를 이용해 taskId 생성
//        //String generatedTaskId = UUID.randomUUID().toString();
//        String generatedTaskId = "TASK_" + UUID.randomUUID().toString().substring(0, 8);
//        // 태스크가 이미 존재하는지 확인
//        if (taskRepository.existsByTaskId(generatedTaskId)) {
//            return new ResponseEntity<>(new Response(409, "Conflict", "업무가 이미 존재합니다.", null), HttpStatus.CONFLICT);
//        }
//
//        // 새 태스크 생성
//        Task newTask = new Task();
//        newTask.setTaskId(generatedTaskId);
//        newTask.setProjectId(taskRequest.getProjectId());
//        newTask.setTaskName(taskRequest.getTaskName());
//        newTask.setDescription(taskRequest.getDescription());
//        newTask.setAssigneeId(taskRequest.getAssigneeId());
//        newTask.setCreatedDate(taskRequest.getCreatedDate());
//        newTask.setStartDate(taskRequest.getStartDate());
//        newTask.setDueDate(taskRequest.getDueDate());
//        newTask.setFrequencyId(taskRequest.getFrequencyId());
//        newTask.setCommentCount(0); // 기본값 0
//        newTask.setStatus(taskRequest.getStatus());
//        newTask.setItoProcessId(taskRequest.getItoProcessId());
//        newTask.setAssigneeConfirmation(taskRequest.getAssigneeConfirmation());
//        newTask.setCreatedBy(taskRequest.getCreatedBy());
//
//        Task createdTask = taskRepository.save(newTask);
//        return new ResponseEntity<>(new Response(201, "Created", "업무가 성공적으로 생성되었습니다.", createdTask), HttpStatus.CREATED);
//    }
//
//
////    public List<Task> getAllTasks() {
////        return taskRepository.findAll();
////    }
////
////
////
//////    @Transactional(readOnly = true)
//////    @EntityGraph(attributePaths = {"comments"})
//////    public Task getTaskById(String taskId) {
//////        return taskRepository.findByTaskId(taskId).orElse(null);
//////    }
////
////    @Transactional(readOnly = true)
////    public TaskResponseDTO getTaskById(String taskId) {
////        Task task = taskRepository.findByTaskId(taskId).orElse(null);
////        if (task == null) return null;
////
////        List<CommentResponseDTO> comments = task.getComments().stream()
////                .map(comment -> new CommentResponseDTO(comment.getCommentId(), comment.getCommenterId(),comment.getCommentContent(), comment.getCreateDate(), comment.getLikeCount(), comment.getLikedUsers()))
////                .collect(Collectors.toList());
////
////
////
////        TaskResponseDTO taskDto = new TaskResponseDTO();
////
////        // Task 정보 설정
////        taskDto.setTaskId(task.getTaskId());
////        taskDto.setProjectId(task.getProjectId());
////        taskDto.setTaskName(task.getTaskName());
////        taskDto.setDescription(task.getDescription());
////        taskDto.setCreatedDate(task.getCreatedDate());
////        taskDto.setStartDate(task.getStartDate());
////        taskDto.setDueDate(task.getDueDate());
////        taskDto.setStatus(task.getStatus());
////        taskDto.setItoProcessId(task.getItoProcessId());
////        taskDto.setAssigneeConfirmation(task.getAssigneeConfirmation());
////        taskDto.setComments(comments);
////        taskDto.setCreatedBy(task.getCreatedBy());
////
////
////        Optional<User> assignee = userRepository.findById(task.getAssigneeId());
////        if (assignee.isPresent()) {
////            taskDto.setAssigneeId(assignee.get().getUserId());
////            taskDto.setAssigneeName(assignee.get().getName());
////            taskDto.setAssigneeProfile(assignee.get().getPhoto());
////        }
////
////        // 담당자 정보 설정 (여러 명)
//////        List<AssigneeResponse> assignees = task.getAssignees().stream()
//////                .map(user -> new AssigneeResponse(
//////                        user.getUserId(),
//////                        user.getName(),
//////                        user.getPhoto()
//////                ))
//////                .collect(Collectors.toList());
//////        taskDto.setAssignees(assignees);
////
////        return taskDto;
////    }
//
//    public List<Task> getTasksByMonthAndProjectIds(int year, int month, List<String> projectIds) {
//        // 데이터베이스 쿼리를 통해 해당 연도, 월, 프로젝트 ID에 맞는 태스크를 필터링하여 반환
//        return taskRepository.findTasksByMonthAndProjectIds(year, month, projectIds);
//    }
//
//
//    public ResponseEntity<Response> deleteTask(String taskId) {
//        if (taskId == null) {
//            // 유효하지 않은 ID가 제공된 경우
//            return new ResponseEntity<>(new Response(400, "Bad Request", "유효하지 않은 요청입니다. ID를 확인하세요.", null), HttpStatus.BAD_REQUEST);
//        }
//
//        Optional<Task> task = taskRepository.findByTaskId(taskId);
//        if (task.isPresent()) {
//            taskRepository.delete(task.get());
//            return new ResponseEntity<>(new Response(200, "OK", "업무가 성공적으로 삭제되었습니다.", null), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(new Response(404, "Not Found", "해당 업무를 찾을 수 없습니다.", null), HttpStatus.NOT_FOUND);
//    }
//
//    public ResponseEntity<Response> updateTask(String taskId, TaskRequest taskRequest) {
//        // 필수 필드 유효성 검사
//        if (taskRequest.getProjectId() == null ||
//                taskRequest.getTaskName() == null || taskRequest.getAssigneeId() == null ||
//                taskRequest.getCreatedDate() == null || taskRequest.getStartDate() == null ||
//                taskRequest.getStatus() == null || taskRequest.getItoProcessId() == null ||
//                taskRequest.getAssigneeConfirmation() == null) {
//
//            // 필수 필드가 누락된 경우 400 Bad Request 응답 반환
//            return new ResponseEntity<>(new Response(400, "Bad Request", "잘못된 요청입니다. 필수 필드를 확인하세요.", null), HttpStatus.BAD_REQUEST);
//        }
//
//        Optional<Task> existingTask = taskRepository.findByTaskId(taskId);
//        if (existingTask.isPresent()) {
//            Task updatedTask = existingTask.get();
//
//            // 모든 필드 업데이트
//            updatedTask.setProjectId(taskRequest.getProjectId());
//            updatedTask.setTaskName(taskRequest.getTaskName());
//            updatedTask.setDescription(taskRequest.getDescription());
//            updatedTask.setAssigneeId(taskRequest.getAssigneeId());
//            updatedTask.setCreatedDate(taskRequest.getCreatedDate());
//            updatedTask.setStartDate(taskRequest.getStartDate());
//            updatedTask.setDueDate(taskRequest.getDueDate());
//            updatedTask.setFrequencyId(taskRequest.getFrequencyId());
//            updatedTask.setStatus(taskRequest.getStatus());
//            updatedTask.setItoProcessId(taskRequest.getItoProcessId());
//            updatedTask.setAssigneeConfirmation(taskRequest.getAssigneeConfirmation());
//
//            Task savedTask = taskRepository.save(updatedTask);
//            return new ResponseEntity<>(new Response(200, "OK", "업무가 성공적으로 수정되었습니다.", savedTask), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(new Response(404, "Not Found", "해당 업무를 찾을 수 없습니다.", null), HttpStatus.NOT_FOUND);
//    }
//
//
////    @Transactional(readOnly = true)
////    public List<TaskResponseDTO> searchTasks(List<String> projectIds, String itoProcessId, String unit,String assigneeId, LocalDate startDate, LocalDate dueDate, String taskName) {
////        Specification<Task> spec = taskSpecification.buildSpecification(projectIds, itoProcessId, unit, assigneeId, startDate, dueDate, taskName);
////        //List<Task> tasks = taskRepository.findAll(spec);
////
////        Page<Task> tasks = taskRepository.findAll(spec);
////
////
////            return tasks.stream().map(task -> {
////            TaskResponseDTO dto = new TaskResponseDTO();
////
////            // Task 정보 설정
////            dto.setTaskId(task.getTaskId());
////            dto.setTaskName(task.getTaskName());
////            dto.setAssigneeId(task.getAssigneeId());
////            dto.setDueDate(task.getDueDate());
////            dto.setStatus(task.getStatus());
////
////            // Assignee 정보 추가 설정
////            Optional<User> assignee = userRepository.findById(task.getAssigneeId());
////            if (assignee.isPresent()) {
////                dto.setAssigneeName(assignee.get().getName());
////                dto.setAssigneeProfile(assignee.get().getPhoto());
////            }
////
////            return dto;
////        }).collect(Collectors.toList());
////    }
//
//
//    @Transactional(readOnly = true)
//    public Page<TaskResponseDTO> searchTasks(List<String> projectIds, String itoProcessId, String unit, String assigneeId, LocalDate startDate, LocalDate dueDate, String taskName, Pageable pageable) {
//        Specification<Task> spec = taskSpecification.buildSpecification(projectIds, itoProcessId, unit, assigneeId, startDate, dueDate, taskName);
//
//        // 페이징된 Task 목록을 불러옵니다.
//        Page<Task> tasks = taskRepository.findAll(spec, pageable);
//
//        // Task를 TaskResponseDTO로 변환하여 반환합니다.
//        return tasks.map(task -> {
//            TaskResponseDTO dto = new TaskResponseDTO();
//
//            // Task 정보 설정
//            dto.setTaskId(task.getTaskId());
//            dto.setTaskName(task.getTaskName());
//            dto.setAssigneeId(task.getAssigneeId());
//            dto.setDueDate(task.getDueDate());
//            dto.setStatus(task.getStatus());
//
//            // Assignee 정보 추가 설정
//            Optional<User> assignee = userRepository.findById(task.getAssigneeId());
//            assignee.ifPresent(user -> {
//                dto.setAssigneeName(user.getName());
//                dto.setAssigneeProfile(user.getPhoto());
//            });
//
//            return dto;
//        });
//    }
//
//    public Page<TaskResponseDTO> getTasksByProjectIds(List<String> projectIds, Pageable pageable) {
//
//        Page<Task> tasks = taskRepository.findByProjectIdIn(projectIds, pageable);
//        Page<TaskResponseDTO> taskDTOs = tasks.map(task -> {
//            Optional<User> assignee = userRepository.findById(task.getAssigneeId());
//            TaskResponseDTO dto = new TaskResponseDTO();
//
//            // Task 정보 설정
//            dto.setTaskId(task.getTaskId());
//            dto.setProjectId(task.getProjectId());
//            dto.setTaskName(task.getTaskName());
//            dto.setDescription(task.getDescription());
//            dto.setCreatedDate(task.getCreatedDate());
//            dto.setStartDate(task.getStartDate());
//            dto.setDueDate(task.getDueDate());
//            dto.setStatus(task.getStatus());
//            dto.setItoProcessId(task.getItoProcessId());
//            dto.setAssigneeConfirmation(task.getAssigneeConfirmation());
//
//            // Assignee 정보 설정
//            assignee.ifPresent(a -> {
//                TaskResponseDTO.Assignee assigneeDTO = new TaskResponseDTO.Assignee();
//                assigneeDTO.setAssigneeId(a.getUserId());
//                assigneeDTO.setAssigneeName(a.getName());
//                assigneeDTO.setAssigneeProfile(a.getPhoto());
//                dto.setAssignee(assigneeDTO); // Assignee 객체 설정
//            });
//
//            return dto;
//        });
//
//        return taskDTOs;
//    }
//
//
//    /*
//20241106 주기적 업무를 가능하게...
// */
//    // 주기적 업무 생성 로직
//    public List<Task> createRecurringTasks(TaskRequest taskRequest) {
//        List<Task> recurringTasks = new ArrayList<>();
//
//        LocalDate startDate = taskRequest.getStartDate();
//        LocalDate endDate = taskRequest.getEndDate();
//        boolean hasEndDate = taskRequest.isHasEndDate(); // 종료일 여부
//        int frequencyInterval = taskRequest.getFrequencyInterval(); // 주기 간격
//
//        System.out.println(taskRequest.getFrequencyInterval());
//        // 반복을 종료할 기준 설정
//        LocalDate limitDate = hasEndDate ? endDate : LocalDate.of(startDate.getYear() + 1, 12, 31);
//
//        switch (taskRequest.getFrequencyType()) {
//            case "daily":
//                recurringTasks.addAll(createDailyTasks(taskRequest, startDate, limitDate, frequencyInterval));
//                break;
//
//            case "weekly":
//                recurringTasks.addAll(createWeeklyTasks(taskRequest, startDate, limitDate, frequencyInterval));
//                break;
//
//            case "monthly":
//                recurringTasks.addAll(createMonthlyTasks(taskRequest, startDate, limitDate, frequencyInterval));
//                break;
//
//            case "yearly":
//                recurringTasks.addAll(createYearlyTasks(taskRequest, startDate, limitDate));
//                break;
//
//            default:
//                throw new IllegalArgumentException("Invalid frequency type");
//        }
//
//        // DB에 저장
//        return taskRepository.saveAll(recurringTasks);
//    }
//
//    private List<Task> createDailyTasks(TaskRequest taskRequest, LocalDate startDate, LocalDate limitDate, int interval) {
//        List<Task> tasks = new ArrayList<>();
//        String baseTaskId = "TASK_" + UUID.randomUUID().toString().substring(0, 8); // 공통 접두사 생성
//
//        int instanceNumber = 1;
//        while (startDate.isBefore(limitDate) || startDate.equals(limitDate)) {
//
//            Task task = new Task(taskRequest);
//
//            // 고유한 taskId 생성
//            String uniqueTaskId = baseTaskId + "_" + instanceNumber;
//            task.setTaskId(uniqueTaskId);
//
//            task.setStartDate(startDate);
//            task.setDueDate(startDate); // 종료일을 시작일과 동일하게 설정
//            tasks.add(task);
//
//            // 다음 반복 주기 시작일을 설정
//            startDate = startDate.plusDays(interval);
//            instanceNumber++; // 인스턴스 번호 증가
//        }
//
//        return tasks;
//    }
//
//    private List<Task> createWeeklyTasks(TaskRequest taskRequest, LocalDate startDate, LocalDate limitDate, int interval) {
//        List<Task> tasks = new ArrayList<>();
//        //DayOfWeek dayOfWeek = taskRequest.getWeeklyDay(); // 사용자가 선택한 요일 (월, 화 등)
//
//        String baseTaskId = "TASK_" + UUID.randomUUID().toString().substring(0, 8); // 공통 접두사 생성
//
//        int instanceNumber = 1;
//        // 문자열 요일을 DayOfWeek로 변환 (람다식 사용)
//        List<DayOfWeek> weeklyDays = taskRequest.getWeeklyDay(); // 이미 DayOfWeek 타입
//
//
//        while (startDate.isBefore(limitDate) || startDate.equals(limitDate)) {
//            for (DayOfWeek dayOfWeek : weeklyDays) { // 선택된 요일 각각에 대해 반복
//                LocalDate nextDate = getNextWeekday(startDate, dayOfWeek);
//                if (nextDate.isAfter(limitDate)) continue;
//
//                Task task = new Task(taskRequest);
//
//                // 고유한 taskId 생성
//                String uniqueTaskId = baseTaskId + "_" + instanceNumber;
//                task.setTaskId(uniqueTaskId);
//                task.setStartDate(nextDate);
//                task.setDueDate(nextDate);
//                tasks.add(task);
//
//                instanceNumber++; // 인스턴스 번호 증가
//            }
//            startDate = startDate.plusWeeks(interval);
//        }
//        return tasks;
//    }
//
////    private LocalDate getNextWeekday(LocalDate date, DayOfWeek dayOfWeek) {
////        int daysToAdd = (dayOfWeek.getValue() - date.getDayOfWeek().getValue() + 7) % 7;
////        return date.plusDays(daysToAdd == 0 ? 7 : daysToAdd); // 다음 요일 계산
////    }
//
//    private LocalDate getNextWeekday(LocalDate date, DayOfWeek dayOfWeek) {
//        int daysToAdd = (dayOfWeek.getValue() - date.getDayOfWeek().getValue() + 7) % 7;
//        return date.plusDays(daysToAdd == 0 ? 0 : daysToAdd); // 현재 날짜가 요일과 같으면 바로 반환
//    }
//
//
//    private List<Task> createMonthlyTasks(TaskRequest taskRequest, LocalDate startDate, LocalDate limitDate, int interval) {
//        List<Task> tasks = new ArrayList<>();
//
//
//        String baseTaskId = "TASK_" + UUID.randomUUID().toString().substring(0, 8); // 공통 접두사 생성
//
//        int instanceNumber = 1;
//        while (startDate.isBefore(limitDate) || startDate.equals(limitDate)) {
//            LocalDate nextDate;
//
//            if (taskRequest.getMonthlyDayOfMonth() != null) {
//                // 1. 매월 특정 날짜 반복 (예: 매월 20일)
//                nextDate = startDate.withDayOfMonth(taskRequest.getMonthlyDayOfMonth());
//            } else if (taskRequest.getMonthlyWeekOfMonth() != null && taskRequest.getMonthlyDayOfWeek() != null) {
//                // 2. 매월 특정 주차의 특정 요일 반복 (예: 매월 2번째 수요일)
//                nextDate = getMonthlyWeekday(startDate, taskRequest.getMonthlyWeekOfMonth(), taskRequest.getMonthlyDayOfWeek());
//            } else {
//                throw new IllegalArgumentException("유효하지 않은 월간 반복 설정입니다.");
//            }
//
//            if (nextDate.isAfter(limitDate)) break;
//
//            Task task = new Task(taskRequest);
//            // 고유한 taskId 생성
//            String uniqueTaskId = baseTaskId + "_" + instanceNumber;
//            task.setTaskId(uniqueTaskId);
//            task.setStartDate(nextDate);
//            task.setDueDate(nextDate);
//            tasks.add(task);
//
//            startDate = startDate.plusMonths(interval);
//            instanceNumber++; // 인스턴스 번호 증가
//        }
//        return tasks;
//    }
//
//
//    private LocalDate getMonthlyWeekday(LocalDate date, int weekOfMonth, DayOfWeek dayOfWeek) {
//        LocalDate firstDayOfMonth = date.withDayOfMonth(1);
//        int dayOfWeekValue = dayOfWeek.getValue() - firstDayOfMonth.getDayOfWeek().getValue();
//        dayOfWeekValue = (dayOfWeekValue < 0) ? dayOfWeekValue + 7 : dayOfWeekValue;
//        return firstDayOfMonth.plusDays(dayOfWeekValue + 7 * (weekOfMonth - 1));
//    }
//
//    private List<Task> createYearlyTasks(TaskRequest taskRequest, LocalDate startDate, LocalDate limitDate) {
//        List<Task> tasks = new ArrayList<>();
//        Integer monthOfYear = taskRequest.getYearlyMonth();      // 매년 월 (예: 11월)
//        Integer dayOfMonth = taskRequest.getYearlyDayOfMonth();  // 매년 특정 일자 (예: 6일)
//        Integer weekOfMonth = taskRequest.getYearlyWeekOfMonth();// 매년 몇 번째 주
//        DayOfWeek dayOfWeek = taskRequest.getYearlyDayOfWeek();  // 매년 요일
//
//
//        String baseTaskId = "TASK_" + UUID.randomUUID().toString().substring(0, 8); // 공통 접두사 생성
//
//        int instanceNumber = 1;
//        // 시작일을 설정된 월로 변경하여 초기화
//        LocalDate currentDate = startDate.withMonth(monthOfYear).withDayOfMonth(1);
//
//        while (currentDate.isBefore(limitDate) || currentDate.equals(limitDate)) {
//            LocalDate nextDate;
//
//            if (dayOfMonth != null) {
//                // 케이스 1: 매년 같은 월의 특정 일자 (예: 11월 6일)
//                nextDate = currentDate.withDayOfMonth(dayOfMonth);
//            } else if (weekOfMonth != null && dayOfWeek != null) {
//                // 케이스 2: 매년 같은 월의 특정 주와 요일 (예: 11월 첫 번째 수요일)
//                nextDate = getYearlyWeekday(currentDate, weekOfMonth, dayOfWeek);
//            } else {
//                throw new IllegalArgumentException("유효하지 않은 연간 반복 설정입니다.");
//            }
//
//            if (nextDate.isAfter(limitDate)) break;
//
//            Task task = new Task(taskRequest);
//            String uniqueTaskId = baseTaskId + "_" + instanceNumber; // 고유한 taskId 생성
//            task.setTaskId(uniqueTaskId);
//            task.setStartDate(nextDate);
//            task.setDueDate(nextDate);
//            tasks.add(task);
//
//            // 다음 해의 첫 날로 이동
//            currentDate = currentDate.plusYears(1).withMonth(monthOfYear).withDayOfMonth(1);
//            System.out.println(currentDate);
//            instanceNumber++;
//        }
//
//        return tasks;
//    }
//
//    private LocalDate getYearlyWeekday(LocalDate date, int weekOfMonth, DayOfWeek dayOfWeek) {
//        LocalDate firstDayOfMonth = date.withDayOfMonth(1);
//        int dayOfWeekValue = dayOfWeek.getValue() - firstDayOfMonth.getDayOfWeek().getValue();
//        dayOfWeekValue = (dayOfWeekValue < 0) ? dayOfWeekValue + 7 : dayOfWeekValue;
//        return firstDayOfMonth.plusDays(dayOfWeekValue + 7 * (weekOfMonth - 1));
//    }
//
//
    //담당자 여러명
//    public  void addTaskWithAssignees(TaskRequest taskRequest) {
//        Task task = new Task();
//        task.setTaskName(taskRequest.getTaskName());
//        String generatedTaskId = "TASK_" + UUID.randomUUID().toString().substring(0, 8);
//        task.setTaskId(generatedTaskId);
//        // 사용자 엔터티 조회
//        List<User> users = userRepository.findAllByUserIdIn(taskRequest.getAssigneeIds());
//        if (users.isEmpty()) {
//            throw new RuntimeException("사용자를 찾을 수 없습니다.");
//        }
//
//
//        task.setProjectId(taskRequest.getProjectId());
//        task.setTaskName(taskRequest.getTaskName());
//        task.setAssigneeId("192133");
//        task.setDescription(taskRequest.getDescription());
//        task.setCreatedDate(taskRequest.getCreatedDate());
//        task.setStartDate(taskRequest.getStartDate());
//        task.setDueDate(taskRequest.getDueDate());
//        task.setFrequencyId(taskRequest.getFrequencyId());
//        task.setCommentCount(0); // 기본값 0
//        task.setStatus(taskRequest.getStatus());
//        task.setItoProcessId(taskRequest.getItoProcessId());
//        task.setAssigneeConfirmation(taskRequest.getAssigneeConfirmation());
//        task.setCreatedBy(taskRequest.getCreatedBy());
//
//        // 사용자 엔터티를 Set으로 변환하여 할당
//        task.setAssignees(new HashSet<>(users));
//
//        Task createdTask = taskRepository.save(task);
//
//
//    }
//
//
//    // 매일 자정에 실행 (cron 표현식 사용)
//    @Scheduled(cron = "0 0 0 * * *")
//    public void updateTaskStatus() {
//        LocalDate today = LocalDate.now();
//        taskRepository.updateStatusForPastTasks(today);
//    }
//
//    // Task 완료처리
//    public boolean completeTask(String taskId) {
//        int rowsUpdated = taskRepository.updateStatusToCompleted(taskId);
//        return rowsUpdated > 0; // 업데이트 성공 여부 반환
//    }
//
//    public boolean confirmTask(String taskId, String email) {
//        Task task = taskRepository.findById(taskId).orElse(null);
//        if (task != null && task.getAssigneeConfirmation().equals("N")) { // 초기값이 "N"
//            task.setAssigneeConfirmation("Y");  // "Y"로 변경
//            taskRepository.save(task);
//            return true;
//        }
//        return false;
//    }
//
//}


@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskSpecification taskSpecification;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskSpecification taskSpecification, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskSpecification = taskSpecification;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<Response> createTask(TaskRequest taskRequest) {
        if (taskRequest.getTaskName() == null ||
                taskRequest.getAssigneeIds() == null ||
                taskRequest.getStartDate() == null ||
                taskRequest.getCreatedDate() == null ||
                taskRequest.getStatus() == null ||
                taskRequest.getItoProcessId() == null) {
            return new ResponseEntity<>(new Response(400, "Bad Request", "필수 필드가 누락되었습니다.", null), HttpStatus.BAD_REQUEST);
        }

        String generatedTaskId = "TASK_" + UUID.randomUUID().toString().substring(0, 8);

        if (taskRepository.existsByTaskId(generatedTaskId)) {
            return new ResponseEntity<>(new Response(409, "Conflict", "업무가 이미 존재합니다.", null), HttpStatus.CONFLICT);
        }

        Task newTask = new Task();
        newTask.setTaskId(generatedTaskId);
        newTask.setProjectId(taskRequest.getProjectId());
        newTask.setTaskName(taskRequest.getTaskName());
        newTask.setDescription(taskRequest.getDescription());
        //newTask.setAssigneeId(taskRequest.getAssigneeId());
        newTask.setCreatedDate(taskRequest.getCreatedDate());
        newTask.setStartDate(taskRequest.getStartDate());
        newTask.setDueDate(taskRequest.getDueDate());
        //newTask.setFrequencyId(taskRequest.getFrequencyId());
        newTask.setCommentCount(0);
        newTask.setStatus(taskRequest.getStatus());
        newTask.setItoProcessId(taskRequest.getItoProcessId());
        newTask.setAssigneeConfirmation(taskRequest.getAssigneeConfirmation());
        newTask.setCreatedBy(taskRequest.getCreatedBy());

        Task createdTask = taskRepository.save(newTask);
        return new ResponseEntity<>(new Response(201, "Created", "업무가 성공적으로 생성되었습니다.", createdTask), HttpStatus.CREATED);
    }

    //담당자 여러명
    public  ResponseEntity<Response> addTaskWithAssignees(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTaskName(taskRequest.getTaskName());
        String generatedTaskId = "TASK_" + UUID.randomUUID().toString().substring(0, 8);
        task.setTaskId(generatedTaskId);
        // 사용자 엔터티 조회
        List<User> users = userRepository.findAllByUserIdIn(taskRequest.getAssigneeIds());
        if (users.isEmpty()) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }


        task.setProjectId(taskRequest.getProjectId());
        task.setTaskName(taskRequest.getTaskName());
        //task.setAssigneeId("192133");
        task.setDescription(taskRequest.getDescription());
        task.setCreatedDate(taskRequest.getCreatedDate());
        task.setStartDate(taskRequest.getStartDate());
        task.setDueDate(taskRequest.getDueDate());
        //task.setFrequencyId(taskRequest.getFrequencyId());
        task.setCommentCount(0); // 기본값 0
        task.setStatus(taskRequest.getStatus());
        task.setItoProcessId(taskRequest.getItoProcessId());
        task.setAssigneeConfirmation(taskRequest.getAssigneeConfirmation());
        task.setCreatedBy(taskRequest.getCreatedBy());

        // 사용자 엔터티를 Set으로 변환하여 할당
        task.setAssignees(new HashSet<>(users));

        Task createdTask = taskRepository.save(task);
        return new ResponseEntity<>(new Response(201, "Created", "업무가 성공적으로 생성되었습니다.", createdTask), HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
    public Page<TaskResponseDTO> searchTasks(List<String> projectIds, String itoProcessId, String unit, String assigneeId, LocalDate startDate, LocalDate dueDate, String taskName, Pageable pageable) {
        Specification<Task> spec = taskSpecification.buildSpecification(projectIds, itoProcessId, unit, assigneeId, startDate, dueDate, taskName);

        Page<Task> tasks = taskRepository.findAll(spec, pageable);

        return tasks.map(task -> mapToTaskResponseDTO(task));
    }

//    public Page<TaskResponseDTO> getTasksByProjectIds(List<String> projectIds, Pageable pageable) {
//        Page<Task> tasks = taskRepository.findByProjectIdIn(projectIds, pageable);
//        return tasks.map(this::mapToTaskResponseDTO);
//    }

    public Page<TaskResponseDTO> getTasksByProjectIds(List<String> projectIds, Pageable pageable) {
        Page<Task> tasks = taskRepository.findByProjectIdInWithAssignees(projectIds, pageable);

        return tasks.map(task -> {
            TaskResponseDTO dto = mapToTaskResponseDTO(task);

            // 연관된 Assignee 설정
            List<TaskResponseDTO.Assignee> assigneeDTOs = task.getAssignees().stream()
                    .map(user -> {
                        TaskResponseDTO.Assignee assigneeDTO = new TaskResponseDTO.Assignee();
                        assigneeDTO.setAssigneeId(user.getUserId());
                        assigneeDTO.setAssigneeName(user.getName());
                        assigneeDTO.setAssigneeProfile(user.getPhoto());
                        return assigneeDTO;
                    })
                    .collect(Collectors.toList());


            dto.setAssignees(assigneeDTOs);
            return dto;
        });
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(String taskId) {
        Task task = taskRepository.findByTaskId(taskId).orElseThrow(() -> new NoSuchElementException("Task not found"));


        // 댓글 정보를 매핑
        List<CommentResponseDTO> comments = task.getComments().stream()
                .map(comment -> {
                    // 댓글 작성자 정보 생성
                    User commenter = userRepository.findById(comment.getCommenterId())
                            .orElseThrow(() -> new NoSuchElementException("Commenter not found"));

                    CommenterDTO commenterDTO = new CommenterDTO(
                            commenter.getUserId(),
                            commenter.getName(),
                            commenter.getPhoto()
                    );

                    // CommentResponseDTO 생성
                    return new CommentResponseDTO(
                            comment.getCommentId(),
                            commenterDTO,
                            comment.getCommentContent(),
                            comment.getCreateDate(),
                            comment.getLikeCount(),
                            comment.getLikedUsers()
                    );
                })
                .collect(Collectors.toList());

        // Task를 DTO로 매핑
        TaskResponseDTO taskResponseDTO = mapToTaskResponseDTO(task);

        // 매핑된 댓글 정보를 TaskResponseDTO에 설정
        taskResponseDTO.setComments(comments);

        return taskResponseDTO;
    }

    public List<TaskResponseDTO> getTasksByMonthAndProjectIds(int year, int month, List<String> projectIds) {
        // 데이터베이스 쿼리를 통해 해당 연도, 월, 프로젝트 ID에 맞는 태스크를 필터링하여 반환
        List<Task> tasks = taskRepository.findTasksByMonthAndProjectIds(year, month, projectIds);

        // Task -> TaskResponseDTO로 매핑
        return tasks.stream()
                .map(this::mapToTaskResponseDTO) // 매핑 로직 호출
                .collect(Collectors.toList()); // 리스트로 수집
    }


    private TaskResponseDTO mapToTaskResponseDTO(Task task) {
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
        dto.setCreatedBy(task.getCreatedBy());
        dto.setCommentCount(task.getCommentCount());

//        // Assignee 정보 설정
//        Optional<User> assignee = userRepository.findById(task.getAssigneeId());
//        assignee.ifPresent(user -> {
//            TaskResponseDTO.Assignee assigneeDTO = new TaskResponseDTO.Assignee();
//            assigneeDTO.setAssigneeId(user.getUserId());
//            assigneeDTO.setAssigneeName(user.getName());
//            assigneeDTO.setAssigneeProfile(user.getPhoto());
//            dto.setAssignee(assigneeDTO);
//        });

        // 다중 Assignee 정보 설정
        List<TaskResponseDTO.Assignee> assigneeDTOs = task.getAssignees().stream()
                .map(assignee -> {
                    TaskResponseDTO.Assignee assigneeDTO = new TaskResponseDTO.Assignee();
                    assigneeDTO.setAssigneeId(assignee.getUserId());
                    assigneeDTO.setAssigneeName(assignee.getName());
                    assigneeDTO.setAssigneeProfile(assignee.getPhoto());
                    return assigneeDTO;
                })
                .collect(Collectors.toList());
// Assignees를 DTO에 설정
        dto.setAssignees(assigneeDTOs);

        return dto;
    }

        public ResponseEntity<Response> updateTask(String taskId, TaskRequest taskRequest) {
        // 필수 필드 유효성 검사
        if (taskRequest.getProjectId() == null ||
                taskRequest.getTaskName() == null || taskRequest.getAssigneeIds() == null ||
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
            //updatedTask.setAssigneeId(taskRequest.getAssigneeId());
            updatedTask.setCreatedDate(taskRequest.getCreatedDate());
            updatedTask.setStartDate(taskRequest.getStartDate());
            updatedTask.setDueDate(taskRequest.getDueDate());
            //updatedTask.setFrequencyId(taskRequest.getFrequencyId());
            updatedTask.setStatus(taskRequest.getStatus());
            updatedTask.setItoProcessId(taskRequest.getItoProcessId());
            updatedTask.setAssigneeConfirmation(taskRequest.getAssigneeConfirmation());

            Task savedTask = taskRepository.save(updatedTask);
            return new ResponseEntity<>(new Response(200, "OK", "업무가 성공적으로 수정되었습니다.", savedTask), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response(404, "Not Found", "해당 업무를 찾을 수 없습니다.", null), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Response> deleteTask(String taskId) {
        if (taskId == null) {
            return new ResponseEntity<>(new Response(400, "Bad Request", "유효하지 않은 요청입니다. ID를 확인하세요.", null), HttpStatus.BAD_REQUEST);
        }

        Optional<Task> task = taskRepository.findByTaskId(taskId);
        if (task.isPresent()) {
            taskRepository.delete(task.get());
            return new ResponseEntity<>(new Response(200, "OK", "업무가 성공적으로 삭제되었습니다.", null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Response(404, "Not Found", "해당 업무를 찾을 수 없습니다.", null), HttpStatus.NOT_FOUND);
    }


        // 매일 자정에 실행 (cron 표현식 사용)
    @Scheduled(cron = "0 0 0 * * *")
    public void updateTaskStatus() {
        LocalDate today = LocalDate.now();
        taskRepository.updateStatusForPastTasks(today);
    }

    // Task 완료처리
    public boolean completeTask(String taskId) {
        int rowsUpdated = taskRepository.updateStatusToCompleted(taskId);
        return rowsUpdated > 0; // 업데이트 성공 여부 반환
    }

    public boolean confirmTask(String taskId, String email) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null && task.getAssigneeConfirmation().equals("N")) { // 초기값이 "N"
            task.setAssigneeConfirmation("Y");  // "Y"로 변경
            taskRepository.save(task);
            return true;
        }
        return false;
    }

        /*
20241106 주기적 업무를 가능하게...
 */
    // 주기적 업무 생성 로직
    public List<Task> createRecurringTasks(TaskRequest taskRequest) {
        List<Task> recurringTasks = new ArrayList<>();

        LocalDate startDate = taskRequest.getStartDate();
        LocalDate endDate = taskRequest.getEndDate();
        boolean hasEndDate = taskRequest.isHasEndDate(); // 종료일 여부
        int frequencyInterval = taskRequest.getFrequencyInterval(); // 주기 간격

        System.out.println(taskRequest.getFrequencyInterval());
        // 반복을 종료할 기준 설정
        LocalDate limitDate = hasEndDate ? endDate : LocalDate.of(startDate.getYear() + 1, 12, 31);

        switch (taskRequest.getFrequencyType()) {
            case "daily":
                recurringTasks.addAll(createDailyTasks(taskRequest, startDate, limitDate, frequencyInterval));
                break;

            case "weekly":
                recurringTasks.addAll(createWeeklyTasks(taskRequest, startDate, limitDate, frequencyInterval));
                break;

            case "monthly":
                recurringTasks.addAll(createMonthlyTasks(taskRequest, startDate, limitDate, frequencyInterval));
                break;

            case "yearly":
                recurringTasks.addAll(createYearlyTasks(taskRequest, startDate, limitDate));
                break;

            default:
                throw new IllegalArgumentException("Invalid frequency type");
        }

        // DB에 저장
        return taskRepository.saveAll(recurringTasks);
    }

    private List<Task> createDailyTasks(TaskRequest taskRequest, LocalDate startDate, LocalDate limitDate, int interval) {
        List<Task> tasks = new ArrayList<>();
        String baseTaskId = "TASK_" + UUID.randomUUID().toString().substring(0, 8); // 공통 접두사 생성

        int instanceNumber = 1;
        while (startDate.isBefore(limitDate) || startDate.equals(limitDate)) {

            Task task = new Task(taskRequest);

            // 고유한 taskId 생성
            String uniqueTaskId = baseTaskId + "_" + instanceNumber;
            task.setTaskId(uniqueTaskId);

            task.setStartDate(startDate);
            task.setDueDate(startDate); // 종료일을 시작일과 동일하게 설정
            task.setCreatedBy(taskRequest.getCreatedBy());
            task.setRecurring(taskRequest.isRecurring());
            // Assignees 처리 로직 추가
            List<User> users = userRepository.findAllByUserIdIn(taskRequest.getAssigneeIds());
            if (users.isEmpty()) {
                throw new RuntimeException("사용자를 찾을 수 없습니다.");
            }
            task.setAssignees(new HashSet<>(users)); // User 엔티티를 Set으로 변환하여 설정

            tasks.add(task);

            // 다음 반복 주기 시작일을 설정
            startDate = startDate.plusDays(interval);
            instanceNumber++; // 인스턴스 번호 증가
        }

        return tasks;
    }

    private List<Task> createWeeklyTasks(TaskRequest taskRequest, LocalDate startDate, LocalDate limitDate, int interval) {
        List<Task> tasks = new ArrayList<>();
        //DayOfWeek dayOfWeek = taskRequest.getWeeklyDay(); // 사용자가 선택한 요일 (월, 화 등)

        String baseTaskId = "TASK_" + UUID.randomUUID().toString().substring(0, 8); // 공통 접두사 생성

        int instanceNumber = 1;
        // 문자열 요일을 DayOfWeek로 변환 (람다식 사용)
        List<DayOfWeek> weeklyDays = taskRequest.getWeeklyDay(); // 이미 DayOfWeek 타입


        while (startDate.isBefore(limitDate) || startDate.equals(limitDate)) {
            for (DayOfWeek dayOfWeek : weeklyDays) { // 선택된 요일 각각에 대해 반복
                LocalDate nextDate = getNextWeekday(startDate, dayOfWeek);
                if (nextDate.isAfter(limitDate)) continue;

                Task task = new Task(taskRequest);

                // 고유한 taskId 생성
                String uniqueTaskId = baseTaskId + "_" + instanceNumber;
                task.setTaskId(uniqueTaskId);
                task.setStartDate(nextDate);
                task.setDueDate(nextDate);

                task.setCreatedBy(taskRequest.getCreatedBy());
                task.setRecurring(taskRequest.isRecurring());
                // Assignees 처리 로직 추가
                List<User> users = userRepository.findAllByUserIdIn(taskRequest.getAssigneeIds());
                if (users.isEmpty()) {
                    throw new RuntimeException("사용자를 찾을 수 없습니다.");
                }
                task.setAssignees(new HashSet<>(users)); // User 엔티티를 Set으로 변환하여 설정

                tasks.add(task);

                instanceNumber++; // 인스턴스 번호 증가
            }
            startDate = startDate.plusWeeks(interval);
        }
        return tasks;
    }

    private LocalDate getNextWeekday(LocalDate date, DayOfWeek dayOfWeek) {
        int daysToAdd = (dayOfWeek.getValue() - date.getDayOfWeek().getValue() + 7) % 7;
        return date.plusDays(daysToAdd == 0 ? 0 : daysToAdd); // 현재 날짜가 요일과 같으면 바로 반환
    }


    private List<Task> createMonthlyTasks(TaskRequest taskRequest, LocalDate startDate, LocalDate limitDate, int interval) {
        List<Task> tasks = new ArrayList<>();


        String baseTaskId = "TASK_" + UUID.randomUUID().toString().substring(0, 8); // 공통 접두사 생성

        int instanceNumber = 1;
        while (startDate.isBefore(limitDate) || startDate.equals(limitDate)) {
            LocalDate nextDate;

            if (taskRequest.getMonthlyDayOfMonth() != null) {
                // 1. 매월 특정 날짜 반복 (예: 매월 20일)
                nextDate = startDate.withDayOfMonth(taskRequest.getMonthlyDayOfMonth());
            } else if (taskRequest.getMonthlyWeekOfMonth() != null && taskRequest.getMonthlyDayOfWeek() != null) {
                // 2. 매월 특정 주차의 특정 요일 반복 (예: 매월 2번째 수요일)
                nextDate = getMonthlyWeekday(startDate, taskRequest.getMonthlyWeekOfMonth(), taskRequest.getMonthlyDayOfWeek());
            } else {
                throw new IllegalArgumentException("유효하지 않은 월간 반복 설정입니다.");
            }

            if (nextDate.isAfter(limitDate)) break;

            Task task = new Task(taskRequest);
            // 고유한 taskId 생성
            String uniqueTaskId = baseTaskId + "_" + instanceNumber;
            task.setTaskId(uniqueTaskId);
            task.setStartDate(nextDate);
            task.setDueDate(nextDate);

            task.setCreatedBy(taskRequest.getCreatedBy());
            task.setRecurring(taskRequest.isRecurring());
            // Assignees 처리 로직 추가
            List<User> users = userRepository.findAllByUserIdIn(taskRequest.getAssigneeIds());
            if (users.isEmpty()) {
                throw new RuntimeException("사용자를 찾을 수 없습니다.");
            }
            task.setAssignees(new HashSet<>(users)); // User 엔티티를 Set으로 변환하여 설정


            tasks.add(task);

            startDate = startDate.plusMonths(interval);
            instanceNumber++; // 인스턴스 번호 증가
        }
        return tasks;
    }


    private LocalDate getMonthlyWeekday(LocalDate date, int weekOfMonth, DayOfWeek dayOfWeek) {
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);
        int dayOfWeekValue = dayOfWeek.getValue() - firstDayOfMonth.getDayOfWeek().getValue();
        dayOfWeekValue = (dayOfWeekValue < 0) ? dayOfWeekValue + 7 : dayOfWeekValue;
        return firstDayOfMonth.plusDays(dayOfWeekValue + 7 * (weekOfMonth - 1));
    }

    private List<Task> createYearlyTasks(TaskRequest taskRequest, LocalDate startDate, LocalDate limitDate) {
        List<Task> tasks = new ArrayList<>();
        Integer monthOfYear = taskRequest.getYearlyMonth();      // 매년 월 (예: 11월)
        Integer dayOfMonth = taskRequest.getYearlyDayOfMonth();  // 매년 특정 일자 (예: 6일)
        Integer weekOfMonth = taskRequest.getYearlyWeekOfMonth();// 매년 몇 번째 주
        DayOfWeek dayOfWeek = taskRequest.getYearlyDayOfWeek();  // 매년 요일


        String baseTaskId = "TASK_" + UUID.randomUUID().toString().substring(0, 8); // 공통 접두사 생성

        int instanceNumber = 1;
        // 시작일을 설정된 월로 변경하여 초기화
        LocalDate currentDate = startDate.withMonth(monthOfYear).withDayOfMonth(1);

        while (currentDate.isBefore(limitDate) || currentDate.equals(limitDate)) {
            LocalDate nextDate;

            if (dayOfMonth != null) {
                // 케이스 1: 매년 같은 월의 특정 일자 (예: 11월 6일)
                nextDate = currentDate.withDayOfMonth(dayOfMonth);
            } else if (weekOfMonth != null && dayOfWeek != null) {
                // 케이스 2: 매년 같은 월의 특정 주와 요일 (예: 11월 첫 번째 수요일)
                nextDate = getYearlyWeekday(currentDate, weekOfMonth, dayOfWeek);
            } else {
                throw new IllegalArgumentException("유효하지 않은 연간 반복 설정입니다.");
            }

            if (nextDate.isAfter(limitDate)) break;

            Task task = new Task(taskRequest);
            String uniqueTaskId = baseTaskId + "_" + instanceNumber; // 고유한 taskId 생성
            task.setTaskId(uniqueTaskId);
            task.setStartDate(nextDate);
            task.setDueDate(nextDate);

            task.setCreatedBy(taskRequest.getCreatedBy());
            task.setRecurring(taskRequest.isRecurring());
            // Assignees 처리 로직 추가
            List<User> users = userRepository.findAllByUserIdIn(taskRequest.getAssigneeIds());
            if (users.isEmpty()) {
                throw new RuntimeException("사용자를 찾을 수 없습니다.");
            }
            task.setAssignees(new HashSet<>(users)); // User 엔티티를 Set으로 변환하여 설정

            tasks.add(task);

            // 다음 해의 첫 날로 이동
            currentDate = currentDate.plusYears(1).withMonth(monthOfYear).withDayOfMonth(1);
            System.out.println(currentDate);
            instanceNumber++;
        }

        return tasks;
    }

    private LocalDate getYearlyWeekday(LocalDate date, int weekOfMonth, DayOfWeek dayOfWeek) {
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);
        int dayOfWeekValue = dayOfWeek.getValue() - firstDayOfMonth.getDayOfWeek().getValue();
        dayOfWeekValue = (dayOfWeekValue < 0) ? dayOfWeekValue + 7 : dayOfWeekValue;
        return firstDayOfMonth.plusDays(dayOfWeekValue + 7 * (weekOfMonth - 1));
    }



}
