package com.example.project.follow.service;

import com.example.project.common.entity.Follow;
import com.example.project.common.entity.User;
import com.example.project.common.exception.CustomException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.follow.model.response.FollowResponse;
import com.example.project.follow.repository.FollowRepository;
import com.example.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    // 팔로우
    public void follow(Long followerId, Long followingId) {
        // 로그인 확인 예외
        User follower = findUserOrException(followerId);

        // 팔로우 할 대상 없을 시 예외
        User following = userRepository.findById(followingId).orElseThrow(
                () -> new CustomException(ErrorCode.FOLLOWING_USER_NOT_FOUND)
        );

        // 본인 팔로우 시 예외
        if (followerId.equals(followingId)) {
            throw new CustomException(ErrorCode.SELF_FOLLOW_NOT_ALLOWED);
        }

        // 이미 팔로우 되있으면 예외
        boolean existence = followRepository.existsByFollowersAndFollowings(follower, following);
        if (existence) {
            throw new CustomException(ErrorCode.FOLLOW_ALREADY_EXISTS);
        }

        Follow follow = new Follow(
                follower,
                following
        );

        followRepository.save(follow);
    }

    // 팔로우 취소
    public void unfollow(Long followerId, Long followingId) {
        // 팔로우 한 대상이 없으면 예외
        Follow follow = followRepository.findByFollowersIdAndFollowingsId(followerId, followingId).orElseThrow(
                () -> new CustomException(ErrorCode.FOLLOW_NOT_FOUND)
        );

        followRepository.delete(follow);

    }

    // 내가 팔로우 한 사람들 조회
    @Transactional(readOnly = true)
    public Page<FollowResponse> findFollowings(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        User user = findUserOrException(userId);

        Page<Follow> followings = followRepository.findByFollowers(user,pageable);

        return followings.map(FollowResponse::fromFollowings);

    }

    // 나를 팔로우 한 팔로워들 조회
    @Transactional(readOnly = true)
    public Page<FollowResponse> findFollowers(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        User user = findUserOrException(userId);

        Page<Follow> followers = followRepository.findByFollowings(user,pageable);

        return followers.map(FollowResponse::fromFollowers);

    }


    public User findUserOrException(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
    }

}
