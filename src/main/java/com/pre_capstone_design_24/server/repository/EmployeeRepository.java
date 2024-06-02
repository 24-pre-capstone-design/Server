package com.pre_capstone_design_24.server.repository;

import com.pre_capstone_design_24.server.domain.Employee;
import com.pre_capstone_design_24.server.domain.EmployeeStatus;
import com.pre_capstone_design_24.server.domain.Owner;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByOwner(Owner owner);

    Page<Employee> findAllByOwner(Owner owner, Pageable pageable);

    Optional<Employee> findByNameAndOwner(String name, Owner owner);

    @Query("SELECT e FROM Employee e WHERE e.workDate LIKE %:workDate% AND e.owner = :owner")
    List<Employee> findAllByWorkDateAndOwner(@Param("workDate") String workDate, @Param("owner") Owner owner);

    List<Employee> findAllByStatusAndOwner(EmployeeStatus status, Owner owner);
}
