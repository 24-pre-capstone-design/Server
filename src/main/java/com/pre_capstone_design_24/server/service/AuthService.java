package com.pre_capstone_design_24.server.service;

import com.pre_capstone_design_24.server.domain.Owner;
import com.pre_capstone_design_24.server.domain.Role;
import com.pre_capstone_design_24.server.domain.Token;
import com.pre_capstone_design_24.server.global.auth.JwtProvider;
import com.pre_capstone_design_24.server.global.response.GeneralException;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.repository.OwnerRepository;
import com.pre_capstone_design_24.server.requestDto.LoginRequestDto;
import com.pre_capstone_design_24.server.responseDto.JwtResponseDto;
import java.security.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final OwnerRepository ownerRepository;

    private final PasswordEncoder passwordEncoder;

   private final JwtProvider jwtProvider;

    public JwtResponseDto login(LoginRequestDto loginRequestDto) {
        String id = loginRequestDto.getId();
        String password = loginRequestDto.getPassword();

        Owner member = ownerRepository.findById(id)
                .orElseThrow(() -> new GeneralException(Status.OWNER_NOT_FOUND));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new GeneralException(Status.OWNER_PASSWORD_INCORRECT);
        }

        Token token = jwtProvider.generateToken(id, Role.USER);

        return JwtResponseDto.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }

}
