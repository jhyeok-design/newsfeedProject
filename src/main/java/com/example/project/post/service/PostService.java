package com.example.project.post.service;

import com.example.project.post.dto.ReadPostResponse;
import com.example.project.post.entity.Post;
import com.example.project.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    /**
     * 내 게시물 전체 조회
     * @param userID 유저의 고유 ID
     * @return ReadPostResponse 리스트
     */
    public List<ReadPostResponse> getAllMe(Long userID) {
        // 유저 ID를 기준으로 전체 조회, 생성일자 기준으로 내림차순
        List<Post> posts = postRepository.findByUserIdOrderByCreatedAtDesc(userID);

        return posts.stream().map(ReadPostResponse::new).toList();
    }
}
