package com.midnight1hour.application.port.in;

import com.midnight1hour.adapter.in.dto.LoginDto;
import com.midnight1hour.adapter.in.response.Response;

public interface LoginUseCase {
    public Response login(LoginDto loginDto) throws Exception;
}
