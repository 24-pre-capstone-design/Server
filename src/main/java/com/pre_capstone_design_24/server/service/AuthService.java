package com.pre_capstone_design_24.server.service;

import com.pre_capstone_design_24.server.domain.Owner;
import com.pre_capstone_design_24.server.domain.Role;
import com.pre_capstone_design_24.server.domain.Token;
import com.pre_capstone_design_24.server.global.auth.JwtProvider;
import com.pre_capstone_design_24.server.global.response.GeneralException;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.global.util.RandomPasswordGenerator;
import com.pre_capstone_design_24.server.repository.OwnerRepository;
import com.pre_capstone_design_24.server.requestDto.LoginRequestDto;
import com.pre_capstone_design_24.server.requestDto.PasswordCheckRequestDto;
import com.pre_capstone_design_24.server.responseDto.IdDuplicateCheckResponseDto;
import com.pre_capstone_design_24.server.responseDto.JwtResponseDto;
import com.pre_capstone_design_24.server.responseDto.PasswordCheckResponseDto;
import com.pre_capstone_design_24.server.responseDto.PasswordRandomSetResponseDto;
import java.security.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final OwnerService ownerService;

    private final PasswordEncoder passwordEncoder;

   private final JwtProvider jwtProvider;

    public JwtResponseDto login(LoginRequestDto loginRequestDto) {
        String id = loginRequestDto.getId();
        String password = loginRequestDto.getPassword();

        Owner owner = ownerService.getOwnerById(id);

        if (!passwordEncoder.matches(password, owner.getPassword())) {
            throw new GeneralException(Status.OWNER_PASSWORD_INCORRECT);
        }

        Token token = jwtProvider.generateToken(id, Role.USER);

        return JwtResponseDto.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }

    public IdDuplicateCheckResponseDto idDuplicateCheck(String id) {
        boolean isIdExist = ownerService.isOwnerExist(id);
        return IdDuplicateCheckResponseDto.builder()
                .isDuplicated(isIdExist)
                .build();
    }

    public PasswordCheckResponseDto passwordCheck(PasswordCheckRequestDto passwordCheckRequestDto) {
        Owner owner = ownerService.getCurrentOwner();
        String currentPassword = passwordCheckRequestDto.getCurrentPassword();
        boolean isPasswordCorrect = passwordEncoder.matches(currentPassword, owner.getPassword());
        return PasswordCheckResponseDto.builder()
                .isPasswordCorrect(isPasswordCorrect)
                .build();
    }

    public PasswordRandomSetResponseDto setRandomPassword(String id, String birthDate) {
        Owner owner = ownerService.getOwnerByIdAndBirthDate(id, birthDate);
        String randomPassword = RandomPasswordGenerator.generatePassword(9);
        owner.updatePassword(passwordEncoder.encode(randomPassword));
        ownerService.save(owner);

        return PasswordRandomSetResponseDto.builder()
                .newPassword(randomPassword)
                .build();
    }

}
