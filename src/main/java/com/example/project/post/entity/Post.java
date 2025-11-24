package com.example.project.post.entity;

import com.example.project.common.entity.BaseEntity;
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
    private Long id;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    private Long userId;
    private Long likeCount;
    private Long commentCount;
    private boolean isDeleted;

    // 생성자
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
        this.likeCount = 0L;
        this.commentCount = 0L;
        this.isDeleted = false;
    }

    // 기능
    public void delete() {
        this.isDeleted = true;
    }
}
