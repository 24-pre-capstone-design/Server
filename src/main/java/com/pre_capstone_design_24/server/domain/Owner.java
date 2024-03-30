package com.pre_capstone_design_24.server.domain;

import com.pre_capstone_design_24.server.requestDto.OwnerRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Owner {

    @Id
    private String id;

    private String password;

    private String name;

    public static Owner of(OwnerRequestDto ownerRequestDto) {
        return Owner.builder()
                .id(ownerRequestDto.getId())
                .password(ownerRequestDto.getPassword())
                .name(ownerRequestDto.getName())
                .build();
    }

}
