package com.pre_capstone_design_24.server.service;

import com.pre_capstone_design_24.server.domain.UploadedFile;
import com.pre_capstone_design_24.server.global.handler.FileHandler;
import com.pre_capstone_design_24.server.global.response.GeneralException;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.repository.UploadedFileRepository;
import com.pre_capstone_design_24.server.responseDto.UploadedFileResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
@Slf4j
public class FileService {

    @Value("${resource.file.url}")
    private String fileURL;

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

    public void deleteFile(String fileUrl) {
        UploadedFile uploadedFile = uploadedFileRepository.findByUrl(fileUrl)
                .orElseThrow(() -> new GeneralException(Status.FILE_NOT_FOUND_IN_DB));
        String filePath = uploadedFile.getSavedPath();
        File savedFile = new File(filePath);
        if (!savedFile.exists()) {
            throw new GeneralException(Status.FILE_NOT_FOUND_IN_STORAGE);
        }
        fileHandler.deleteFile(filePath);
        delete(uploadedFile);
    }

    public void delete(UploadedFile uploadedFile) {
        uploadedFileRepository.delete(uploadedFile);
    }

}
