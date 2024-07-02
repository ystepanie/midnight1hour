package com.midnight1hour.domain.repository;

import com.midnight1hour.adapter.in.dto.LoginDto;
import com.midnight1hour.domain.model.entity.User;

public interface UserRepository {
    User findByUserIdAndPw(String userId, String userPw);
}
