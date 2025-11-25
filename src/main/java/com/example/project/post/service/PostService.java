package com.example.project.post.service;

import com.example.project.common.entity.User;
import com.example.project.common.exception.CustomException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.common.exception.PostNotFoundException;
import com.example.project.common.exception.UserNotFoundException;
import com.example.project.post.dto.*;
import com.example.project.post.dto.ReadPostResponse;
import com.example.project.common.entity.Post;
import com.example.project.post.repository.PostRepository;
import com.example.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

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
        List<Post> postList = postRepository.findAllByIsDeletedFalseOrderByCreatedAtDesc();

        return postList.stream().map(ReadPostResponse::from).toList(); // == .map(post -> new ReadPostResponse(post))
    }

    /**
     * 내 게시물 전체 조회
     * - 로그인한 유저를 기준으로 전체 조회
     * @param userID 로그인한 유저 ID
     * @return ReadPostResponse 리스트
     */
    public List<ReadPostResponse> getAllMe(Long userID) {
        // 유저 조회
        User user = userRepository.findById(userID).orElseThrow(UserNotFoundException::new);
        // 유저의 전체 게시물 조회, 삭제 처리된 게시물은 조회 안됨, 생성일자 기준으로 내림차순
        List<Post> posts = postRepository.findByUserAndIsDeletedFalseOrderByCreatedAtDesc(user);

        return posts.stream().map(ReadPostResponse::from).toList();
    }

    /**
     * 게시물 단건 조회
     * @param postID 조회할 게시물 ID
     * @return ReadPostResponse DTO
     */
    public ReadPostResponse getOne(Long postID) {
        // 게시물 조회, 삭제 처리된 게시물은 조회 안됨
        Post post = postRepository.findByIdAndIsDeletedFalse(postID).orElseThrow(PostNotFoundException::new);

        return ReadPostResponse.from(post);
    }

    /**
     * 게시물 삭제 (소프트 삭제)
     * @param userID 로그인한 유저 ID
     * @param postID 삭제할 게시물 ID
     */
    public void delete(Long userID, Long postID) {
        // 유저 조회
        User user = userRepository.findById(userID).orElseThrow(UserNotFoundException::new);
        // 유저의 게시물 조회, 삭제 처리된 게시물은 조회 안됨
        Post post = postRepository.findByIdAndUserAndIsDeletedFalse(postID, user).orElseThrow(PostNotFoundException::new);

        post.delete(); // 조회한 게시물 삭제 처리
    }

    /**
     * 게시물 수정
     * @param postId 수정할 게시물 ID
     * @param request 게시물
     * @return 게시물 수정 응답 DTO
     */
    public UpdatePostResponse updatePost(Long userID, Long postId, UpdatePostRequest request) {
        // 유저 조회
        User user = userRepository.findById(userID).orElseThrow(UserNotFoundException::new);
        // 유저의 게시물 조회, 삭제 처리된 게시물은 조회 안됨
        Post post = postRepository.findByIdAndUserAndIsDeletedFalse(postId, user).orElseThrow(PostNotFoundException::new);

        if (request.getTitle().isEmpty() && request.getContent().isEmpty()) {
            throw new CustomException(ErrorCode.EMPTY_POST_UPDATE);
        } else if (request.getTitle().isEmpty()) {
            request.setTitle(post.getTitle());
        } else if(request.getContent().isEmpty()) {
            request.setContent(post.getContent());
        }

        post.updatePost(request);

        return UpdatePostResponse.from(post);
    }
}
