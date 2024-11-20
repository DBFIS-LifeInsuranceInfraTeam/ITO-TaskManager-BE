package com.ITOPW.itopw.controller;

import com.ITOPW.itopw.dto.Response;
import com.ITOPW.itopw.dto.request.UserUpdateRequest;
import com.ITOPW.itopw.dto.response.BaseResponseDTO;
import com.ITOPW.itopw.entity.User;
import com.ITOPW.itopw.service.UserService;
import com.ITOPW.itopw.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

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

    @PostMapping("/{userId}/profile-image")
    public BaseResponseDTO uploadProfileImage(
            @PathVariable String userId,
            @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("파일이 비어 있습니다.");
            }

            // Persistent Volume에 연결된 경로
            String uploadDir = "/app/uploads/profile-images/";
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + fileName);
            file.transferTo(dest);

            // DB에 파일 경로 저장
            // DB에 파일 경로 저장
            String profileImagePath = "/uploads/profile-images/" + fileName;
            userService.updateProfileImage(userId, profileImagePath);

//            return ResponseEntity.status(HttpStatus.OK)
//                    .body("파일 업로드 성공: " + fileName);
            return new BaseResponseDTO(200, "OK", "파일 업로드 성공: " + fileName);
        } catch (Exception e) {
            return new BaseResponseDTO(500, "INTERNAL_SERVER_ERROR", "파일 업로드 실패: " + e.getMessage());
                    //ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 실패: " + e.getMessage());
        }
    }
}