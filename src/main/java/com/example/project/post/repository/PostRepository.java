package com.example.project.post.repository;

import com.example.project.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUserIdOrderByCreatedAtDesc(Long userID);
}
