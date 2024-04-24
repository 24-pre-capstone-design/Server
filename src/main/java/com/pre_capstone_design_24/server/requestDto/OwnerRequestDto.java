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
public class OwnerRequestDto {

    @NotNull
    @Schema(description = "아이디", example = "mingmingmon")
    private String id;

    @Password
    @NotNull
    @Schema(description = "비밀번호", example = "1234asdf!")
    private String password;

    @NotNull
    @Schema(description = "사장님 이름", example = "전민주")
    private String name;

    @NotNull
    @Schema(description = "생년월일", example = "20020722")
    private String birthDate;

}