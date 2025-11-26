package com.example.project.user.service;

import com.example.project.common.entity.User;
import com.example.project.common.exception.CustomException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.common.utils.PasswordEncoder;
import com.example.project.follow.repository.FollowRepository;
import com.example.project.security.jwt.JwtUtil;
import com.example.project.user.model.request.CreateUserRequest;
import com.example.project.user.model.request.DeleteUserRequest;
import com.example.project.user.model.request.LoginRequest;
import com.example.project.user.model.request.UpdateUserRequest;
import com.example.project.user.model.response.*;
import com.example.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final FollowRepository followRepository;

    public CreateUserResponse createUser(CreateUserRequest request) {

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getUserName(),
                request.getEmail(),
                request.getNickname(),
                encodedPassword
        );

        User savedUser = userRepository.save(user);

        return CreateUserResponse.from(savedUser);

    }

    // 로그인
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        // 논리삭제된 유저 제외
        if (user.isDeleted()) {
            throw new CustomException(ErrorCode.USER_DELETED);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        String token = jwtUtil.generateToken(user);
        return new LoginResponse(token);
    }

    // 로그아웃
    @Transactional
    public void logout(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        user.increaseTokenVersion();
    }
    
    @Transactional(readOnly = true)
    public FindUserResponse findUser(Long userId) {
        User user = findUserOrException(userId);

        return FindUserResponse.from(user);
    }
    
    // 마이페이지 조회
    @Transactional(readOnly = true)
    public GetUserResponse getMe() {
        Long userId = getCurrentUserId();

        // 논리삭제된 유저 제외
        User user = findUserOrException(userId);
        if (user.isDeleted()) {
            throw new CustomException(ErrorCode.USER_DELETED);
        }

        // 팔로워/팔로잉 수 조회
        int followerCount = followRepository.countByFollowingsId(userId);
        int followingCount = followRepository.countByFollowersId(userId);

        return GetUserResponse.from(user, followerCount, followingCount);
    }

    // 타 회원 마이페이지 조회
    @Transactional(readOnly = true)
    public GetOtherUserResponse getOtherUser(Long userId) {

        // 논리삭제된 유저 제외
        User user = findUserOrException(userId);
        if (user.isDeleted()) {
            throw new CustomException(ErrorCode.USER_DELETED);
        }

        // 팔로워/팔로잉 수 조회
        int followerCount = followRepository.countByFollowingsId(userId);
        int followingCount = followRepository.countByFollowersId(userId);
        
        User otherUser = findUserOrException(userId);
        return GetOtherUserResponse.from(otherUser, followerCount, followingCount);
    }
    
    // 내 정보 수정
    @Transactional
    public UpdateUserResponse updateMe(UpdateUserRequest request) {
        Long userId = getCurrentUserId();
        User user = findUserOrException(userId);

        // 요청 값 존재 여부 확인
        boolean nicknameExists = request.getNickname() != null && !request.getNickname().isBlank();
        boolean currentPwExists = request.getCurrentPassword() != null && !request.getCurrentPassword().isBlank();
        boolean newPwExists = request.getNewPassword() != null && !request.getNewPassword().isBlank();

        // 아무런 값도 안 보냈을 때
        if (!nicknameExists && !currentPwExists && !newPwExists) {
            throw new CustomException(ErrorCode.NOTHING_TO_UPDATE);
        }

        // 닉네임 변경
        if (nicknameExists) {
            String newNickname = request.getNickname().trim();

            // 기존 닉네임과 같은 닉네임인지 중복 확인
            if (!newNickname.equals(user.getNickname())) {
                if (userRepository.existsByNicknameAndIdNot(newNickname, user.getId())) {
                    throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
                }
                user.modifyNickname(newNickname);
            }
        }

        // 비밀번호 변경
        // 현재 비밀번호와 새로운 비밀번호 둘 중 하나라도 있으면 변경 의사가 존재
        if (currentPwExists || newPwExists) {

            // 두 값이 다 있어야 정상 변경
            if (!currentPwExists || !newPwExists) {
                throw new CustomException(ErrorCode.INVALID_PASSWORD_INPUT); // 두 값 중 하나가 빠졌을 때
            }

            // 현재 비밀번호 검증
            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new CustomException(ErrorCode.INVALID_PASSWORD);
            }

            // 새 비밀번호가 현재 비밀번호와 같은지 확인
            if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
                throw new CustomException(ErrorCode.SAME_PASSWORD);
            }

            // 비밀번호 수정, 인코딩
            String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
            user.modifyPassword(encodedNewPassword);
        }

        return UpdateUserResponse.from(user);
    }

    // 회원 삭제 (로그인 기능 적용 전까지 userId 임시 사용)
    // 기존 : @PathVariable로 userId를 받아서 findById후 encodedPassword로 해당 유저의 인코딩된 비밀번호를 특정해서 대조함
    // 변경 : @PathVariable대신 JwtAuthenticationFilter의 SecurityContext에서 UserId를 가져와서 대조 (서비스 하단 메서드)
    public void deleteUser(DeleteUserRequest request) {

        Long userId = getCurrentUserId();
        User user = findUserOrException(userId);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        
        user.softDelete();
    }

    public User findUserOrException(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
    }

    // SecurityContextHolder - 현재 로그인한 userId 가져오기
    private Long getCurrentUserId() {
        return (Long) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}

