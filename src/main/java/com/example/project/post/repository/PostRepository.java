package com.example.project.post.repository;

import com.example.project.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<User,Long> {
}
