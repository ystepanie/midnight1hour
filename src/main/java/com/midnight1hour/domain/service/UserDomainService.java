package com.midnight1hour.domain.service;

import com.midnight1hour.adapter.in.dto.LoginDto;
import com.midnight1hour.domain.model.entity.User;
import com.midnight1hour.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDomainService {
    private final UserRepository userRepository;

    public User login(LoginDto loginDto) {
        String userId = loginDto.userId();
        String userPw = loginDto.userPw();

        User userInfo = userRepository.findByUserIdAndPw(userId, userPw);
        // TODO: 유효성 검증 추가해야함
        return userInfo;
    }

    public User signup(UserDto userDto) {

    }
}
