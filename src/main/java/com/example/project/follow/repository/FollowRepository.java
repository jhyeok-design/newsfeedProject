package com.example.project.follow.repository;

import com.example.project.common.entity.Follow;
import com.example.project.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    // 팔로우 돼있는지 확인 (팔로우)
    Boolean existsByFollowersAndFollowings(User followers, User followings);

    // 팔로우 돼있는지 확인 (팔로우 취소)
    Optional<Follow> findByFollowersIdAndFollowingsId(Long followerId, Long followingId);

    // 팔로잉 목록
    Page<Follow> findByFollowings(User followings, Pageable pageable);

    // 팔로워 목록
    Page<Follow> findByFollowers(User followers,Pageable pageable);

    // 해당 유저를 팔로우한 사람 수 (팔로워 수)
    int countByFollowingsId(Long userId);
    // 해당 유저가 팔로우한 사람 수 (팔로잉 수)
    int countByFollowersId(Long userId);

}
