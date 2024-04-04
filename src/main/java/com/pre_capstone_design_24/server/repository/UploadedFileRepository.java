package com.pre_capstone_design_24.server.repository;

import com.pre_capstone_design_24.server.domain.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {

}
