package com.pre_capstone_design_24.server.repository;

import com.pre_capstone_design_24.server.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, String> {

}
