package com.pre_capstone_design_24.server.requestDto;

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
public class OwnerUpdateRequestDto {

    @NotNull
    @Schema(description = "아이디", example = "mingmingmon")
    private String id;

    @NotNull
    @Schema(description = "비밀번호", example = "1234")
    private String password;

    @NotNull
    @Schema(description = "사장님 이름", example = "전민주")
    private String name;

    @Schema(description = "새 비밀번호", example = "0000")
    private String newPassword;

}
//DTO따로 안 나누고 할 수 있는 방법이 있을까요 이렇게 하면 좀 연동하는데 힘들지 않을까해서요