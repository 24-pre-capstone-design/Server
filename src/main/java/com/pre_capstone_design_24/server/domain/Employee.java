package com.pre_capstone_design_24.server.domain;

import com.pre_capstone_design_24.server.requestDto.EmployeeRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String name;

    private String phoneNumber;

    @NotNull
    private String workDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public static Employee of(EmployeeRequestDto requestDto, Owner owner) {
        return Employee.builder()
            .name(requestDto.getName())
            .phoneNumber(requestDto.getPhoneNumber())
            .workDate(requestDto.getWorkDate())
            .status(requestDto.getStatus())
            .owner(owner)
            .build();
    }

    public void update(EmployeeRequestDto requestDto) {
        this.name = requestDto.getName();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.workDate = requestDto.getWorkDate();
        this.status = requestDto.getStatus();
    }
}