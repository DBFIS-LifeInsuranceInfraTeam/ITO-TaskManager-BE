package com.ITOPW.itopw.repository;

import com.ITOPW.itopw.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findByTaskId(String taskId);

    Optional<Comment> findByCommentId(Long commentId);
}