package com.ITOPW.itopw.repository;

import com.ITOPW.itopw.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, String> {

    Optional<Comment> findByCommentId(Long commentId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Comment c WHERE c.commentId = :commentId")
    void deleteByCommentId(Long commentId);
}