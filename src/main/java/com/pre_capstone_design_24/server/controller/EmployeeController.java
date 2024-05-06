package com.pre_capstone_design_24.server.controller;

import com.pre_capstone_design_24.server.global.response.ApiResponse;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.requestDto.EmployeeRequestDto;
import com.pre_capstone_design_24.server.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@Tag(name = "employee", description = "직원 api")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "직원 생성")
    @Secured({"ROLE_USER"})
    @PostMapping("")
    public ApiResponse<?> createEmployee(@RequestBody EmployeeRequestDto requestDto) {
        employeeService.createEmployee(requestDto);
        return ApiResponse.onSuccess(Status.CREATED.getCode(), Status.CREATED.getMessage(), null);
    }

    @Operation(summary = "모든 직원 조회")
    @Secured({"ROLE_USER"})
    @GetMapping("/all")
    public ApiResponse<?> getAllEmployees() {
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), employeeService.getAllEmployees());
    }


    @Operation(summary = "직원 수정")
    @Secured({"ROLE_USER"})
    @PatchMapping("/{employeeId}")
    public ApiResponse<?> updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeRequestDto requestDto) {
        employeeService.updateEmployee(employeeId, requestDto);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), null);
    }

    @Operation(summary = "직원 삭제")
    @Secured({"ROLE_USER"})
    @DeleteMapping("/{employeeId}")
    public ApiResponse<?> deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), null);
    }
}
