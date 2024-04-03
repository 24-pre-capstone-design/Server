package com.pre_capstone_design_24.server.service;

import com.pre_capstone_design_24.server.domain.Owner;
import com.pre_capstone_design_24.server.global.auth.JwtProvider;
import com.pre_capstone_design_24.server.global.response.GeneralException;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.repository.OwnerRepository;
import com.pre_capstone_design_24.server.requestDto.OwnerRequestDto;
import com.pre_capstone_design_24.server.responseDto.OwnerResponseDto;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    public void createOwner(OwnerRequestDto ownerRequestDto) {
        Owner newOwner = Owner.of(ownerRequestDto, passwordEncoder);
        ownerRepository.save(newOwner);
    }

    public OwnerResponseDto getOwner(String id) {
        Owner owner = getOwnerById(id);
        return OwnerResponseDto.of(owner);
    }

    public OwnerResponseDto getLoginOwner() {
        Owner owner = getCurrentOwner();
        return OwnerResponseDto.of(owner);
    }

    public Owner getOwnerById(String id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new GeneralException(Status.OWNER_NOT_FOUND));
    }

    public Owner getCurrentOwner() {
        String ownerId = jwtProvider.getUsernameFromAuthentication();
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new GeneralException(Status.OWNER_NOT_FOUND));
    }

}
