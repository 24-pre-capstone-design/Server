package com.pre_capstone_design_24.server.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class JwtResponseDto {

    private String accessToken;

    private String refreshToken;

}
