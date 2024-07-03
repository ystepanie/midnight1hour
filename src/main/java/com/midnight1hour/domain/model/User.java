package com.midnight1hour.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private Long userSeq;
    private String userId;
    private String userPw;
    private String phoneNumber;

}
