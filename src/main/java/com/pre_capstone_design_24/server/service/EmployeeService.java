package com.pre_capstone_design_24.server.service;

import com.pre_capstone_design_24.server.domain.Employee;
import com.pre_capstone_design_24.server.domain.Owner;
import com.pre_capstone_design_24.server.global.auth.JwtProvider;
import com.pre_capstone_design_24.server.global.response.GeneralException;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.repository.EmployeeRepository;
import com.pre_capstone_design_24.server.repository.OwnerRepository;
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

    private final OwnerService ownerService;

    public void createEmployee(EmployeeRequestDto employeeRequestDto) {
        Owner currentOwner = ownerService.getCurrentOwner();
        Employee newEmployee = Employee.of(employeeRequestDto, currentOwner);
        save(newEmployee);
    }

    public List<EmployeeResponseDto> getAllEmployees() {
        Owner currentOwner = ownerService.getCurrentOwner();
        List<Employee> employees = employeeRepository.findAllByOwner(currentOwner);
        return employees.stream()
            .map(EmployeeResponseDto::of)
            .collect(Collectors.toList());
    }

    public void updateEmployee(Long employeeId, EmployeeRequestDto employeeRequestDto) {
        Owner currentOwner = ownerService.getCurrentOwner();
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new GeneralException(Status.EMPLOYEE_NOT_FOUND));

        if (!employee.getOwner().equals(currentOwner)) {
            throw new GeneralException(Status.UNAUTHORIZED);
        }

        employee.update(employeeRequestDto);
        save(employee);
    }

    public void deleteEmployee(Long employeeId) {
        Owner currentOwner = ownerService.getCurrentOwner();
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new GeneralException(Status.EMPLOYEE_NOT_FOUND));

        if (!employee.getOwner().equals(currentOwner)) {
            throw new GeneralException(Status.UNAUTHORIZED);
        }
        delete(employee);
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    private void delete(Employee employee) {
        employeeRepository.delete(employee);
    }

}
