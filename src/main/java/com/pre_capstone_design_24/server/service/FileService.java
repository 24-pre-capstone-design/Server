package com.pre_capstone_design_24.server.service;

import com.pre_capstone_design_24.server.domain.UploadedFile;
import com.pre_capstone_design_24.server.global.handler.FileHandler;
import com.pre_capstone_design_24.server.repository.UploadedFileRepository;
import com.pre_capstone_design_24.server.responseDto.UploadedFileResponseDto;
import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class FileService {

    @Value("${resource.file.url}")
    private String fileURL;

    @Value("${resource.file.path}")
    private String filePath;

    private final UploadedFileRepository uploadedFileRepository;

    private final FileHandler fileHandler;

    public UploadedFileResponseDto saveFile(MultipartFile multipartFile) throws IOException {

        String savedFilePath = fileHandler.saveFile(multipartFile);
        String savedFileName = new File(savedFilePath).getName();
        String url = fileURL + "/" + savedFileName;

        UploadedFile uploadedFile = UploadedFile.create(multipartFile.getOriginalFilename(), savedFileName, savedFilePath, url, multipartFile.getSize(), multipartFile.getContentType());
        uploadedFileRepository.save(uploadedFile);
        return UploadedFileResponseDto.toDto(uploadedFile);
    }


}
