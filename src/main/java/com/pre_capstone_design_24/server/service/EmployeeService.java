package com.pre_capstone_design_24.server.service;

import com.pre_capstone_design_24.server.domain.Employee;
import com.pre_capstone_design_24.server.global.response.GeneralException;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.repository.EmployeeRepository;
import com.pre_capstone_design_24.server.requestDto.EmployeeRequestDto;
import com.pre_capstone_design_24.server.responseDto.EmployeeResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public void createEmployee(EmployeeRequestDto requestDto) {
        Employee employee = Employee.of(requestDto);
        employeeRepository.save(employee);
    }

    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
            .map(EmployeeResponseDto::of)
            .collect(Collectors.toList());
    }

    public void updateEmployee(Long id, EmployeeRequestDto requestDto) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new GeneralException(Status.EMPLOYEE_NOT_FOUND));
        employee.update(requestDto);
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new GeneralException(Status.EMPLOYEE_NOT_FOUND));
        employeeRepository.delete(employee);
    }
}
