package com.example.project.user.repository;

import com.example.project.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    // 해당 닉네임을 가진 다른 유저가 존재하는지 확인
    boolean existsByNicknameAndIdNot(String nickname, Long id);

}
