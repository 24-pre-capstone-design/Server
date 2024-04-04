package com.pre_capstone_design_24.server.responseDto;

import com.pre_capstone_design_24.server.domain.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UploadedFileResponseDto {

    private Long id;

    private String originalFileName;

    private String savedFileName;

    private String savedPath;

    private String url;

    private Long fileSize;

    private String contentType;

    public static UploadedFileResponseDto toDto(UploadedFile uploadedFile) {
        return UploadedFileResponseDto.builder()
                .id(uploadedFile.getId())
                .originalFileName(uploadedFile.getOriginalFileName())
                .savedFileName(uploadedFile.getSavedFileName())
                .savedPath(uploadedFile.getSavedPath())
                .url(uploadedFile.getUrl())
                .fileSize(uploadedFile.getFileSize())
                .contentType(uploadedFile.getContentType())
                .build();
    }

}
