package com.pre_capstone_design_24.server.domain;

import com.pre_capstone_design_24.server.requestDto.EmployeeRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phoneNumber;

    private String workDate;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    // Assume EmployeeRequestDto has similar fields to OwnerCreateRequestDto, but fitting for Employee
    public static Employee of(EmployeeRequestDto requestDto) {
        return Employee.builder()
            .name(requestDto.getName())
            .phoneNumber(requestDto.getPhoneNumber())
            .workDate(requestDto.getWorkDate())
            .status(requestDto.getStatus())
            .build();
    }

    public void update(EmployeeRequestDto requestDto) {
        this.name = requestDto.getName();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.workDate = requestDto.getWorkDate();
        this.status = requestDto.getStatus(); // Assuming EmployeeRequestDto includes status
    }
}