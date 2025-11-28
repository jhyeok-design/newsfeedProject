package com.example.project.like.repository;

import com.example.project.common.entity.Like;
import com.example.project.common.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> post(Post post);

    Optional<Like> findByUser_IdAndPost_Id(Long currentUserId, Long postId);

    boolean existsByUser_IdAndPost_Id(Long currentUserId, Long postId);

    long countByPost_Id(Long postId);
}
