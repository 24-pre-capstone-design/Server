package com.pre_capstone_design_24.server.repository;

import com.pre_capstone_design_24.server.domain.Owner;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, String> {

    Optional<Owner> findByIdAndBirthDate(String id, String birthDate);

}
