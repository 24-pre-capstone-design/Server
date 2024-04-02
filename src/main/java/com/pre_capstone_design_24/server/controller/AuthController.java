package com.pre_capstone_design_24.server.controller;

import com.pre_capstone_design_24.server.global.response.ApiResponse;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.requestDto.LoginRequestDto;
import com.pre_capstone_design_24.server.responseDto.JwtResponseDto;
import com.pre_capstone_design_24.server.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ApiResponse<?> login(
            @RequestBody @Valid LoginRequestDto loginRequestDto) {
        JwtResponseDto jwtResponseDTO = authService.login(loginRequestDto);
        return ApiResponse.onSuccess(Status.OK.getCode(),
                Status.CREATED.getMessage(), jwtResponseDTO);
    }

}
