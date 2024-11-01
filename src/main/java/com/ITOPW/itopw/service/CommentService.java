package com.ITOPW.itopw.service;

import com.ITOPW.itopw.entity.Comment;
import com.ITOPW.itopw.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public Comment likeComment(Long commentId, String userId) {
        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        // 사용자 ID가 likedUsers에 포함되지 않은 경우만 추가
        if (!comment.getLikedUsers().contains(userId)) {
            comment.setLikeCount(comment.getLikeCount() + 1); // 좋아요 개수 증가
            comment.getLikedUsers().add(userId); // 사용자 ID 추가
        } else {
            throw new IllegalStateException("이미 이 댓글에 좋아요를 눌렀습니다.");
        }

        return commentRepository.save(comment);
    }

    public Comment addComment(String taskId, String commenterId, String commentContent) {
        Comment comment = new Comment();
        comment.setTaskId(taskId);
        comment.setCommenterId(commenterId);
        comment.setCommentContent(commentContent);
        comment.setCreateDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }
}
