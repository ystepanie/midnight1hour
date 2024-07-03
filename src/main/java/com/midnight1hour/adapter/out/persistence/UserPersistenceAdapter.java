package com.midnight1hour.adapter.out.persistence;

import com.midnight1hour.adapter.out.entity.UserEntity;
import com.midnight1hour.application.port.out.LoginUserPort;
import com.midnight1hour.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements LoginUserPort {
    private final JpaUserRepository jpaUserRepository;

    @Override
    public User findByUserId(String userId) {
        UserEntity userEntity = jpaUserRepository.findByUserId(userId);
        User user = User.builder()
                .userSeq(userEntity.getUserSeq())
                .userId(userEntity.getUserId())
                .userPw(userEntity.getUserPw())
                .phoneNumber(userEntity.getPhoneNumber())
                .build();

        return user;
    }
}
