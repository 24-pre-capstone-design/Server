package com.pre_capstone_design_24.server.service;

import com.pre_capstone_design_24.server.domain.Owner;
import com.pre_capstone_design_24.server.global.auth.JwtProvider;
import com.pre_capstone_design_24.server.global.response.GeneralException;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.repository.OwnerRepository;
import com.pre_capstone_design_24.server.requestDto.OwnerRequestDto;
import com.pre_capstone_design_24.server.requestDto.OwnerUpdateDto;
import com.pre_capstone_design_24.server.responseDto.OwnerResponseDto;
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
        String id = ownerRequestDto.getId();
        if (isOwnerExist(id))
            throw new GeneralException(Status.OWNER_ID_ALREADY_EXISTS);
        Owner newOwner = Owner.of(ownerRequestDto, passwordEncoder);
        save(newOwner);
    }

    public OwnerResponseDto getOwner(String id) {
        Owner owner = getOwnerById(id);
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

    public boolean isOwnerExist(String id) {
        return ownerRepository.existsById(id);
    }

    public void UpdateOwner(String id, OwnerUpdateDto ownerUpdateDto) {
        Owner currentOwner = getCurrentOwner();
        if (!currentOwner.getId().equals(id)){
            throw new GeneralException(Status.UNAUTHORIZED);
        }
        Owner owner = ownerRepository.findById(id)
            .orElseThrow(() -> new GeneralException(Status.OWNER_NOT_FOUND));
        owner.update(ownerUpdateDto);
        save(owner);
    }

    public void save(Owner owner) {
        ownerRepository.save(owner);
    }

    public void deleteOwner(String ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
            .orElseThrow(() -> new GeneralException(Status.OWNER_NOT_FOUND));
        delete(owner);
    }

    private void delete(Owner owner) {
        ownerRepository.delete(owner);
    }

    public Owner getOwnerByIdAndBirthDate(String id, String birthDate) {
        return ownerRepository.findByIdAndBirthDate(id, birthDate)
                .orElseThrow(() -> new GeneralException(Status.OWNER_NOT_FOUND));
    }

}

