package com.pre_capstone_design_24.server.requestDto;

import com.pre_capstone_design_24.server.domain.EmployeeStatus;
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
public class EmployeeRequestDto {

    @NotNull
    @Schema(description = "이름", example = "석해현")
    private String name;

    @NotNull
    @Schema(description = "전화번호", example = "010-1111-1111")
    private String phoneNumber;

    @Schema(description = "근무 상태", example = "WORKING")
    private EmployeeStatus status; // 근무 상태 필드 추가

    @NotNull
    @Schema(description = "근무 날짜", example = "월화수")
    private String workDate;    //근무 날짜 얘기가 나왔던거 같아서 추가했어요 필요없으면 빼도 됩니다.
}
