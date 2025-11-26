package com.example.project.post.repository;

import com.example.project.common.entity.Comment;
import com.example.project.common.entity.User;
import com.example.project.common.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // user가 null이면 전체 조회, user가 있으면 user 필터 적용
    // 삭제 처리가 안된 게시물을 생성일자 기준으로 내림차순
    // 시작일과 종료일이 있으면 필터 적용
    @Query("""
        SELECT p FROM Post p
        WHERE p.isDeleted = false
            AND (:user IS NULL OR p.user = :user)
            AND (:startDate IS NULL OR p.createdAt >= :startDate)
            AND (:endDate IS NULL OR p.createdAt <= :endDate)
        ORDER BY p.createdAt DESC
    """)
    Page<Post> findPosts(@Param("user") User user, Pageable pageable,
                         @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // 게시물 조회, 삭제 처리된 게시물은 조회 안됨
    Optional<Post> findByIdAndIsDeletedFalse(Long postID);

}
