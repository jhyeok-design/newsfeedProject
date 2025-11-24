package com.example.project.post.service;

import com.example.project.post.dto.*;
import com.example.project.post.entity.Post;
import com.example.project.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.example.project.common.exception.ErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    /**
     * 게시물 생성
     * @param request CreatePostRequest DTO(생성할 게시물의 제목과 내용)
     * @return CreatePostResponse DTO
     */
    public CreatePostResponse createPost(CreatePostRequest request) {

        Post post = new Post(
                request.getTitle(),
                request.getContent()
        );

        Post savedPost = postRepository.save(post);

        return CreatePostResponse.from(savedPost);
    }

    /**
     * 게시물 전체 조회
     * @return ReadPostResponse 리스트
     */
    public List<ReadPostResponse> getAll() {
        List<Post> postList = postRepository.findAll();

        return postList.stream()
                .map(ReadPostResponse::new)     // == .map(post -> new ReadPostResponse(post))
                .toList();
    }

    /**
     * 내 게시물 전체 조회
     * @param userID 유저 ID
     * @return ReadPostResponse 리스트
     */
    public List<ReadPostResponse> getAllMe(Long userID) {
        // 유저 ID를 기준으로 전체 조회, 생성일자 기준으로 내림차순
        List<Post> posts = postRepository.findByUserIdOrderByCreatedAtDesc(userID);

        return posts.stream().map(ReadPostResponse::new).toList();
    }

    /**
     * 게시물 단건 조회
     * @param userID 로그인한 유저 ID
     * @param postID 조회할 게시물 ID
     * @return
     */
    public ReadPostResponse getOne(Long userID, Long postID) {
        // 게시물 조회
        Post post = postRepository.findById(postID)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 게시물입니다."));

        return new ReadPostResponse(post);
    }

    /**
     *
     * @param postId 게시물 ID
     * @param request 게시물
     * @return UpdatePostResponse DTO
     */
    public UpdatePostResponse updatePost(Long postId, UpdatePostRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException(POST_NOT_FOUND.getMessage())
        );

        Post updatedPost = post.updatePost(request);

        return UpdatePostResponse.from(updatedPost);
    }
}
