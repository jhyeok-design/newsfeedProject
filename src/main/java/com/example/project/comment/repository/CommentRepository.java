package com.example.project.comment.repository;

import com.example.project.common.entity.Comment;
import com.example.project.common.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"user", "post"})
    Page<Comment> findByPost(Post post, Pageable pageable);
    Long countByPostId(Long postID);
}
