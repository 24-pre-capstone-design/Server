package com.pre_capstone_design_24.server.global.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

public class ImageConverter {
    public static MultipartFile convertToMultipartFile(File file) throws IOException {
        byte[] fileContent = FileUtils.readFileToByteArray(file);
        MultipartFile multipartFile = new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return file.getName();
            }

            @Override
            public String getContentType() {
                try {
                    return Files.probeContentType(file.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public boolean isEmpty() {
                return fileContent.length == 0;
            }

            @Override
            public long getSize() {
                return fileContent.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return fileContent;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return FileUtils.openInputStream(file);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                FileUtils.copyFile(file, dest);
            }
        };
        return multipartFile;
    }
}
