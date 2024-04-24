package com.pre_capstone_design_24.server.controller;

import com.pre_capstone_design_24.server.global.response.ApiResponse;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.requestDto.LoginRequestDto;
import com.pre_capstone_design_24.server.requestDto.PasswordCheckRequestDto;
import com.pre_capstone_design_24.server.responseDto.IdDuplicateCheckResponseDto;
import com.pre_capstone_design_24.server.responseDto.JwtResponseDto;
import com.pre_capstone_design_24.server.responseDto.PasswordCheckResponseDto;
import com.pre_capstone_design_24.server.responseDto.PasswordRandomSetResponseDto;
import com.pre_capstone_design_24.server.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "auth", description = "인증 관련 api")
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

    @GetMapping("/id-duplicate-check")
    @Operation(summary = "아이디 중복 체크")
    public ApiResponse<?> idDuplicateCheck (@RequestParam("id") String id) {
        IdDuplicateCheckResponseDto idDuplicateCheckResponseDto = authService.idDuplicateCheck(id);
        return ApiResponse.onSuccess(Status.OK.getCode(),
                Status.OK.getMessage(), idDuplicateCheckResponseDto);
    }

    @PostMapping("/password-check")
    @Operation(summary = "비밀번호 인증")
    public ApiResponse<?> passwordCheck (
            @RequestBody PasswordCheckRequestDto passwordCheckRequestDto
    ) {
        PasswordCheckResponseDto passwordCheckResponseDto = authService.passwordCheck(passwordCheckRequestDto);
        return ApiResponse.onSuccess(Status.OK.getCode(),
                Status.OK.getMessage(), passwordCheckResponseDto);
    }

    @GetMapping("/random-password")
    @Operation(summary = "비밀번호 재설정")
    public ApiResponse<?> setRandomPassword (
            @RequestParam String id,
            @RequestParam String birthDate
    ) {
        PasswordRandomSetResponseDto passwordRandomSetResponseDto = authService.setRandomPassword(id, birthDate);
        return ApiResponse.onSuccess(Status.OK.getCode(),
                Status.OK.getMessage(), passwordRandomSetResponseDto);
    }

}
