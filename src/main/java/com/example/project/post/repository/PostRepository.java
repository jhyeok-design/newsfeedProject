package com.example.project.post.repository;

import com.example.project.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 전체 조회, 삭제 처리된 게시물은 조회 안됨, 생성일자 기준으로 내림차순
    List<Post> findAllByIsDeletedFalseOrderByCreatedAtDesc();

    // 유저 ID를 기준으로 전체 조회, 삭제 처리된 게시물은 조회 안됨, 생성일자 기준으로 내림차순
    List<Post> findByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(Long userID);

    // 게시물 조회, 삭제 처리된 게시물은 조회 안됨
    Optional<Post> findByIdAndIsDeletedFalse(Long postID);

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUserIdOrderByCreatedAtDesc(Long userID);
}
