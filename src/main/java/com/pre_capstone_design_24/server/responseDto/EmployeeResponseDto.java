package com.pre_capstone_design_24.server.responseDto;

import com.pre_capstone_design_24.server.domain.Employee;
import com.pre_capstone_design_24.server.domain.EmployeeStatus;
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
public class EmployeeResponseDto {

    Long id;

    String name;

    String phoneNumber;

    String workDate;

    EmployeeStatus status;

    public static EmployeeResponseDto of(Employee employee) {
        return EmployeeResponseDto.builder()
            .id(employee.getId())
            .name(employee.getName())
            .phoneNumber(employee.getPhoneNumber())
            .workDate(employee.getWorkDate())
            .status(employee.getStatus())
            .build();
    }
}
