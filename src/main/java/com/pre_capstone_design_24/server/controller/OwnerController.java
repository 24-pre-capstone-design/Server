package com.pre_capstone_design_24.server.controller;

import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.requestDto.OwnerRequestDto;
import com.pre_capstone_design_24.server.responseDto.OwnerResponseDto;
import com.pre_capstone_design_24.server.service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.pre_capstone_design_24.server.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor
@Tag(name = "owner", description = "사장님 api")
public class OwnerController {

    private final OwnerService ownerService;

    @Operation(summary = "사장님 생성")
    @PostMapping("")
    public ApiResponse<?> createOwner(
      @RequestBody @Valid OwnerRequestDto ownerRequestDto

    ) {
        ownerService.createOwner(ownerRequestDto);
        return ApiResponse.onSuccess(Status.CREATED.getCode(), Status.CREATED.getMessage(), null);
    }

    @Operation(summary = "사장님 조회")
    @GetMapping("/{ownerId}")
    public ApiResponse<?> getOwner(
        @PathVariable("ownerId") String ownerId
    ) {
        OwnerResponseDto ownerResponseDto = ownerService.getOwner(ownerId);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), ownerResponseDto);
    }

    @Operation(summary = "로그인한 사용자 확인용")
    @GetMapping("")
    public ApiResponse<?> getLoginOwner() {
        OwnerResponseDto ownerResponseDto = ownerService.getLoginOwner();
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), ownerResponseDto);
    }

    @Operation(summary = "사장님 정보 수정")
    @PatchMapping("/{ownerId}")
    public ApiResponse<?> updateOwner(
        @RequestBody OwnerRequestDto ownerRequestDto,
        @PathVariable("ownerId") String ownerId
    ) {
        ownerService.UpdateOwner(ownerId, ownerRequestDto);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), null);
    }

    @Operation(summary = "사장님 탈퇴")
    @DeleteMapping("/{ownerId}")
    public ApiResponse<?> deleteOwner (
        @PathVariable("ownerId") String ownerId
        ) {
        ownerService.deleteOwner(ownerId);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), null);
    }
}