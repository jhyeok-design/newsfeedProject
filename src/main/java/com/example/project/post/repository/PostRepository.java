package com.example.project.post.repository;

import com.example.project.common.entity.User;
import com.example.project.common.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 전체 게시물 조회, 삭제 처리된 게시물은 조회 안됨, 생성일자 기준으로 내림차순
    List<Post> findAllByIsDeletedFalseOrderByCreatedAtDesc();

    // 유저의 전체 게시물 조회, 삭제 처리된 게시물은 조회 안됨, 생성일자 기준으로 내림차순
    List<Post> findByUserAndIsDeletedFalseOrderByCreatedAtDesc(User user);

    // 게시물 조회, 삭제 처리된 게시물은 조회 안됨
    Optional<Post> findByIdAndIsDeletedFalse(Long postID);

    // 유저의 게시물 조회, 삭제 처리된 게시물은 조회 안됨
    Optional<Post> findByIdAndUserAndIsDeletedFalse(Long postID, User user);
}
