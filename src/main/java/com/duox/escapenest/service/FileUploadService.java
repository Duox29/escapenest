package com.duox.escapenest.service;

import com.duox.escapenest.constant.ResultCode;
import com.duox.escapenest.dto.response.FileUploadResponse;
import com.duox.escapenest.exception.AppException;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

    @Service
    @RequiredArgsConstructor
    @Slf4j
    @FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
    public class FileUploadService {
        String UPLOAD_DIR = "uploads/";
        public FileUploadResponse uploadImage(MultipartFile file){
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if(!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                String fileName = UUID.randomUUID()+"_"+file.getOriginalFilename();

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(),filePath);
                return FileUploadResponse.builder()
                        .path(filePath.toString()).build();
            } catch (IOException e) {
                throw new AppException(ResultCode.UPLOAD_FAILED);
            }
        }

        public void uploadImages(MultipartFile[] files) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                for (MultipartFile file : files) {
                    if (file.isEmpty()) continue;
                    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(file.getInputStream(), filePath);
                }

            } catch (IOException e) {
                log.error("Upload failed: {}", e.getMessage());
                throw new AppException(ResultCode.UPLOAD_FAILED);
            }
        }
    }
