package com.pre_capstone_design_24.server.responseDto;

import com.pre_capstone_design_24.server.domain.Owner;
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

    public static OwnerResponseDto of(Owner owner) {
        return OwnerResponseDto.builder()
                .id(owner.getId())
                .name(owner.getName())
                .build();
    }

}
