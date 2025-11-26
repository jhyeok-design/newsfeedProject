package com.example.project.post.service;

import com.example.project.common.entity.User;
import com.example.project.common.exception.*;
import com.example.project.post.dto.*;
import com.example.project.post.dto.ReadPostResponse;
import com.example.project.common.entity.Post;
import com.example.project.post.repository.PostRepository;
import com.example.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * 게시물 생성
     * @param request CreatePostRequest DTO(생성할 게시물의 제목과 내용)
     * @return CreatePostResponse DTO
     */
    public CreatePostResponse createPost(Long userId, CreatePostRequest request) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Post post = new Post(
                request.getTitle(),
                request.getContent(),
                user
        );

        Post savedPost = postRepository.save(post);

        return CreatePostResponse.from(savedPost);
    }

    /**
     * 게시물 전체 조회 (페이징)
     * - 유저 ID가 없으면 모든 게시물 조회
     * - 유저 ID가 있으면 유저의 게시물 전체 조회
     * - 페이징 없이도 조회 가능
     * @param userId 조회할 유저 ID (선택)
     * @param pageable 페이징 정보를 담고 있는 객체
     * @return 조회된 게시물 (페이징)
     */
    @Transactional(readOnly = true)
    public Page<ReadPostResponse> getAllPost(Long userId, Pageable pageable) {
        // 유저 조회 (userId가 null이면, null)
        User user = userId == null ? null
                : userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        // 게시물 조회 (pageable이 Pageable.unpaged()이면 페이징 없이 조회)
        Page<Post> posts = postRepository.findPosts(user, pageable);

        return posts.map(ReadPostResponse::from);
    }

    /**
     * 게시물 단건 조회
     * @param postID 조회할 게시물 ID
     * @return ReadPostResponse DTO
     */
    @Transactional(readOnly = true)
    public ReadPostResponse getOnePost(Long postID) {
        // 게시물 조회, 삭제 처리된 게시물은 조회 안됨
        Post post = postRepository.findByIdAndIsDeletedFalse(postID).orElseThrow(PostNotFoundException::new);

        return ReadPostResponse.from(post);
    }

    /**
     * 게시물 수정
     * @param postId 수정할 게시물 ID
     * @param request 게시물
     * @return 게시물 수정 응답 DTO
     */
    public UpdatePostResponse updatePost(Long userId, Long postId, UpdatePostRequest request) {
        // 유저의 게시물 조회, 삭제 처리된 게시물은 조회 안됨
        Post post = postRepository.findByIdAndIsDeletedFalse(postId).orElseThrow(PostNotFoundException::new);
        isOwner(userId, post);

        // 아무 정보도 안 줬을 경우
        if ((request.getTitle() == null || request.getTitle().isEmpty())
                && (request.getContent() == null || request.getContent().isEmpty()))
            throw new EmptyPostUpdateException();

        post.update(request);

        return UpdatePostResponse.from(post);
    }

    /**
     * 게시물 삭제 (소프트 삭제)
     * @param postID 삭제할 게시물 ID
     */
    public void deletePost(Long userID, Long postID) {
        // 유저의 게시물 조회, 삭제 처리된 게시물은 조회 안됨
        Post post = postRepository.findByIdAndIsDeletedFalse(postID).orElseThrow(PostNotFoundException::new);
        isOwner(userID, post);

        post.delete(); // 조회한 게시물 삭제 처리
    }

    /**
     * 유저가 게시물의 작성자인지 확인
     * @param userID 로그인한 유저 ID
     * @param post 게시물
     */
    private void isOwner(Long userID, Post post) {
        // 유저 조회
        User user = userRepository.findById(userID).orElseThrow(UserNotFoundException::new);
        // 유저가 게시물의 작성자가 아니면 예외처리
        if (!post.getUser().equals(user)) throw new NotResourceOwnerException();
    }

}
