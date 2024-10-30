package com.ITOPW.itopw.controller;

import com.ITOPW.itopw.entity.User;
import com.ITOPW.itopw.service.UserService;
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
    @GetMapping("/{projectId}")
    public ResponseEntity<List<User>> getUsersByProject(@PathVariable String projectId) {
        List<User> users = userService.getUsersByProjectId(projectId);
        return ResponseEntity.ok(users);
    }
}