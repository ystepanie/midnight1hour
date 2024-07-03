package com.midnight1hour.adapter.out.persistence;

import com.midnight1hour.adapter.out.entity.UserEntity;
import com.midnight1hour.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long> {
    UserEntity findByUserId(String userId);
}
