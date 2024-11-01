package com.ITOPW.itopw.entity;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long commentId;

//    @Column(name = "task_id",nullable = false)
//    private String taskId;

    @Column(name = "commenter_id",nullable = false)
    private String commenterId;

    @Column(name = "comment_content",nullable = false)
    private String commentContent;

    @Column(name = "like_count", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer likeCount = 0 ;

    @Column(name = "create_date",nullable = false)
    private LocalDateTime createDate;


    // Task와의 관계 설정
    @ManyToOne
    @JoinColumn(name = "task_id", insertable = false, updatable = false)
    private Task task;

    // task_id를 사용하는 다른 필드가 있으면 insertable = false, updatable = false 설정
    @Column(name = "task_id", insertable = false, updatable = false)
    private String taskId;


    @ElementCollection
    @CollectionTable(name = "comment_liked_users", joinColumns = @JoinColumn(name = "comment_id"))
    @Column(name = "liked_users")
    private List<String> likedUsers = new ArrayList<>(); // 초기값 빈 리스트

}
