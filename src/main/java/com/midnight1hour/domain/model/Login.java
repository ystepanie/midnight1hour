package com.midnight1hour.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Login {
    private String userId;
    private String userPw;
}
