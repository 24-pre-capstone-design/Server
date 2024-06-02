package com.pre_capstone_design_24.server.requestDto;

import com.pre_capstone_design_24.server.domain.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateDto {

    @Password
    @NotNull
    @Schema(description = "새로운 비밀번호", example = "asdf1234!")
    private String newPassword;

}
