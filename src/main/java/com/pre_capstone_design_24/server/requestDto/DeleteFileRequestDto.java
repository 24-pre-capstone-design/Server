package com.pre_capstone_design_24.server.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteFileRequestDto {

    @NotNull
    @Schema(description = "파일경로", example = "/resources/files/1570824572662600_e7c8aa5d-11e5-4c86-ad0c-5e7ebfd9ee98.png", required = true)
    private String fileUrl;

}
