package com.midnight1hour.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class User {
    private Long userSeq;
    private String userId;
    private String userPw;
    private String phoneNumber;

    @Builder
    public User(Long userSeq, String userId, String userPw, String phoneNumber) {
        this.userSeq = userSeq;
        this.userId = userId;
        this.userPw = userPw;
        this.phoneNumber = phoneNumber;
    }
}
