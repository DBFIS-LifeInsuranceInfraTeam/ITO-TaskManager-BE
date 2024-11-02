package com.ITOPW.itopw.controller;

import com.ITOPW.itopw.dto.Response;
import com.ITOPW.itopw.dto.TaskRequest;
import com.ITOPW.itopw.dto.request.CommentRequestDTO;
import com.ITOPW.itopw.entity.Comment;
import com.ITOPW.itopw.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

//    @GetMapping("/{taskId}")
//    public ResponseEntity<List<Comment>> getComments(@PathVariable String taskId) {
//        List<Comment> comments = commentService.getCommentsByTaskId(taskId);
//        return ResponseEntity.ok(comments);
//    }

    @PostMapping("")
    public ResponseEntity<Comment> addComment(@RequestBody CommentRequestDTO commentRequest) {
        Comment newComment = commentService.addComment(
                commentRequest.getTaskId(),
                commentRequest.getCommenterId(),
                commentRequest.getCommentContent()
        );
        return ResponseEntity.ok(newComment);
    }

    @PostMapping("/{commentId}/like")
    public ResponseEntity<?> likeComment(@PathVariable Long commentId, @RequestBody Map<String, String> request) {
        try {
            String userId = request.get("userId");
            Comment updatedComment = commentService.likeComment(commentId, userId);
            return ResponseEntity.ok(updatedComment);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // CommentController.java
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, @RequestBody Map<String, String> request) {
        try {
            String userId = request.get("userId");
            commentService.deleteComment(commentId, userId);
            return ResponseEntity.ok("Comment deleted successfully.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

}

