package com.pre_capstone_design_24.server.responseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pre_capstone_design_24.server.domain.Owner;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OwnerResponseDto {

    String id;

    String name;

    String birthDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime createdAt;

    public static OwnerResponseDto of(Owner owner) {
        return OwnerResponseDto.builder()
                .id(owner.getId())
                .name(owner.getName())
                .birthDate(owner.getBirthDate())
                .createdAt(owner.getCreatedAt())
                .build();
    }

}
