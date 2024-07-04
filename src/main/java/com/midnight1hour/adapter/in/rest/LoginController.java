package com.midnight1hour.adapter.in.rest;

import com.midnight1hour.adapter.in.dto.LoginDto;
import com.midnight1hour.application.port.in.LoginUseCase;
import com.midnight1hour.adapter.in.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/api")
@RequiredArgsConstructor
public class LoginController {
    private final LoginUseCase loginUserCase;

    final Logger log = LogManager.getLogger(getClass());

    @PostMapping("/login")
    public Response postLogin(@Valid @RequestBody LoginDto loginDto) throws Exception {
        return loginUserCase.login(loginDto);
    }
}