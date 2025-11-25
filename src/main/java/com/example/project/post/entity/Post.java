package com.example.project.post.entity;

import com.example.project.common.entity.BaseEntity;
import com.example.project.common.entity.User;
import com.example.project.post.dto.UpdatePostRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    // 속성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시물 고유 ID
    @Column(length = 50, nullable = false)
    private String title; // 제목
    @Column(nullable = false)
    private String content; // 내용
    private Long likeCount; // 좋아요 수
    private Long commentCount; // 댓글 수
    private boolean isDeleted; // 삭제 여부

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 유저

    // 생성자
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
        this.likeCount = 0L;
        this.commentCount = 0L;
        this.isDeleted = false;
    }

    public Post updatePost(UpdatePostRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();

        return this;
    }

    // 기능
    public void delete() {
        this.isDeleted = true;
    }
}
