package com.ITOPW.itopw.controller;

import com.ITOPW.itopw.dto.Response;
import com.ITOPW.itopw.dto.request.UserUpdateRequest;
import com.ITOPW.itopw.entity.User;
import com.ITOPW.itopw.service.UserService;
import com.ITOPW.itopw.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //프로젝트별 사용자 조회
//    @GetMapping("/{projectId}")
//    public ResponseEntity<List<User>> getUsersByProject(@PathVariable String projectId) {
//        List<User> users = userService.getUsersByProjectId(projectId);
//        return ResponseEntity.ok(users);
//    }

    // 여러 프로젝트 ID를 받아 사용자 목록 조회
    @GetMapping
    public ResponseEntity<List<User>> getUsersByProjectIds(@RequestParam List<String> projectIds) {

        List<User> users = userService.getUsersByProjectIdList(projectIds);
        System.out.println(users);
        return ResponseEntity.ok(users);
    }


    @PutMapping("/{userId}")
    public ResponseEntity<Response> updateUser(
            @PathVariable String userId,
            @RequestBody UserUpdateRequest request) {
        try {
            User updatedUser = userService.updateUser(userId, request);
            return ResponseEntity.ok(new Response(200, "Success", "사용자 정보가 업데이트되었습니다.", updatedUser));
        } catch (UserServiceImpl.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response(404, "Not Found", "사용자를 찾을 수 없습니다.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(500, "Error", "사용자 정보를 업데이트하는 중 오류가 발생했습니다.", null));
        }
    }
}