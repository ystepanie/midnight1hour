package com.midnight1hour.domain.model.entity;

import lombok.Builder;

@Builder
public class User {
    private Long userSeq;
    private String userId;
    private String userPw;
    private String phoneNumber;

}
