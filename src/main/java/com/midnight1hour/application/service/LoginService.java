package com.midnight1hour.application.service;

import com.midnight1hour.adapter.in.dto.LoginDto;
import com.midnight1hour.application.port.in.LoginUseCase;
import com.midnight1hour.application.port.out.LoginUserPort;
import com.midnight1hour.domain.model.Login;
import com.midnight1hour.domain.model.User;
import com.midnight1hour.response.Response;
import com.midnight1hour.util.MessageUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginService implements LoginUseCase {
    private final LoginUserPort loginUserPort;

    @Override
    public Response login(LoginDto loginDto) throws Exception {
        String userId = loginDto.getUserId();
        User user = loginUserPort.findByUserId(userId);
        if(user == null) {
            return new Response(200, MessageUtil.USER_NOT_EXIST);
        }

        // 비밀번호 검증에 실패하면
        if(InvalidatePassword(user, loginDto.getUserPw())) {
            return new Response(200, MessageUtil.DIFF_PASSWORD);
        }

        return new Response(100, MessageUtil.LOGIN_SUCCESS, user);
    }

    public boolean InvalidatePassword(User user, String userPw) throws Exception {
        String userInfoPw = user.getUserPw();

        return !userInfoPw.equals(userPw);
    }
}
