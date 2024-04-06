package com.pre_capstone_design_24.server.service;

import com.pre_capstone_design_24.server.domain.Owner;
import com.pre_capstone_design_24.server.global.auth.JwtProvider;
import com.pre_capstone_design_24.server.global.response.GeneralException;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.repository.OwnerRepository;
import com.pre_capstone_design_24.server.requestDto.OwnerCreateRequestDto;
import com.pre_capstone_design_24.server.requestDto.OwnerUpdateRequestDto;
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

    public void createOwner(OwnerCreateRequestDto ownerCreateRequestDto) {
        Owner newOwner = Owner.of(ownerCreateRequestDto, passwordEncoder);
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

    public boolean isUpdateOwner(String ownerId, OwnerUpdateRequestDto ownerUpdateRequestDto) {
        Owner existingOwner = ownerRepository.findById(ownerId)
            .orElseThrow(() -> new GeneralException(Status.OWNER_NOT_FOUND));

        // 현재 비밀번호 유지
        if (!passwordEncoder.matches(ownerUpdateRequestDto.getPassword(), existingOwner.getPassword())) {
            throw new GeneralException(Status.OWNER_PASSWORD_INCORRECT);
        }

        // 새 비밀번호가 제공되었는지 확인하고, 제공된 경우 비밀번호 업데이트
        if (ownerUpdateRequestDto.getNewPassword() != null && !ownerUpdateRequestDto.getNewPassword().isEmpty()) {
            if (passwordEncoder.matches(ownerUpdateRequestDto.getNewPassword(), existingOwner.getPassword())) {
                throw new GeneralException(Status.OWNER_PASSWORD_IS_THE_SAME);
            }
            String encodedNewPassword = passwordEncoder.encode(ownerUpdateRequestDto.getNewPassword());
            existingOwner.setPassword(encodedNewPassword); // 새 비밀번호로 업데이트
        }

        // ID 변경 로직은 유지
        if (!ownerId.equals(ownerUpdateRequestDto.getId())) {
            // 새 ID가 이미 존재하는지 확인
            boolean idExists = ownerRepository.existsById(ownerUpdateRequestDto.getId());
            if (idExists) {
                throw new GeneralException(Status.OWNER_ID_ALREADY_EXISTS);
            }

            // 기존 Owner 삭제
            delete(existingOwner);
            // 새 ID로 Owner 생성
            Owner updatedOwner = Owner.updateOf(ownerUpdateRequestDto, passwordEncoder);
            updatedOwner.setPassword(existingOwner.getPassword()); // 업데이트된 비밀번호 설정
            save(updatedOwner);
        } else {
            // ID가 변경되지 않은 경우 기존 Owner 정보 업데이트
            existingOwner.update(ownerUpdateRequestDto);
            ownerRepository.save(existingOwner);
        }

        return true;
    }

    public void save(Owner owner) {
        ownerRepository.save(owner);
    }

    public OwnerResponseDto deleteOwner(String ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
            .orElseThrow(() -> new GeneralException(Status.OWNER_NOT_FOUND));
        ownerRepository.delete(owner);
        return null; // 필요에 따라 적절한 응답으로 변경
    }

    private void delete(Owner owner) {
        ownerRepository.delete(owner);
    }
}
