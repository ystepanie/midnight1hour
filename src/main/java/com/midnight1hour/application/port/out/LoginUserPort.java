package com.midnight1hour.application.port.out;

import com.midnight1hour.domain.model.User;

public interface LoginUserPort {
    public User findByUserId(String userId);
}
