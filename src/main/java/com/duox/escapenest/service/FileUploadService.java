package com.duox.escapenest.service;

import com.duox.escapenest.constant.ResultCode;
import com.duox.escapenest.dto.request.ImageUploadRequest;
import com.duox.escapenest.dto.response.FileUploadResponse;
import com.duox.escapenest.dto.response.ImageUploadResponse;
import com.duox.escapenest.entity.Homestay;
import com.duox.escapenest.entity.HomestayImage;
import com.duox.escapenest.exception.AppException;
import com.duox.escapenest.repository.HomestayImageRepository;
import com.duox.escapenest.repository.HomestayRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

    @Service
    @RequiredArgsConstructor
    @Slf4j
    @FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
    public class FileUploadService {
        HomestayRepository homestayRepository;
        HomestayImageRepository homestayImageRepository;
        String UPLOAD_DIR = "uploads/";
        String ROOT_DIR = "D:/DATN/escapenest/";
        public FileUploadResponse uploadImage(MultipartFile file){
                String filePath = baseUpload(file);
                return FileUploadResponse.builder()
                        .path(filePath.toString()).build();
        }
        private String baseUpload(MultipartFile file){
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if(!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                String fileName = UUID.randomUUID()+"_"+file.getOriginalFilename();

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(),filePath);
                return filePath.toString();
            } catch (IOException e)
            {
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
        public ImageUploadResponse uploadHomestayImage(MultipartFile file, ImageUploadRequest request){
            try {
                String filePath = baseUpload(file);
                HomestayImage homestayImage = toEntity(filePath,request);
                homestayImageRepository.save(homestayImage);
                return toResponse(homestayImage);
            } catch (Exception e){
                log.error("ERROR: "+e.getMessage());
                throw new AppException(ResultCode.UNCATEGORIZED_EXCEPTION);
            }
        }
        private HomestayImage toEntity(String filePath, ImageUploadRequest request){
            Homestay homestay = homestayRepository.findByHomestay_id(request.getHomestay_id()).orElseThrow(() -> new AppException(ResultCode.HOMESTAY_NOT_EXISTED));
            return HomestayImage.builder()
                    .homestay(homestay)
                    .imageUrl(ROOT_DIR + filePath)
                    .caption(request.getCaption())
                    .primaryImage(request.isPrimaryImage())
                    .displayOrder(request.getDisplayOrder())
                    .uploadedAt(LocalDateTime.now())
                    .build();
        }
        private ImageUploadResponse toResponse(HomestayImage homestayImage){
            return ImageUploadResponse.builder()
                    .image_id(homestayImage.getImage_id())
                    .homestay_id(homestayImage.getHomestay().getHomestay_id())
                    .imageUrl(homestayImage.getImageUrl())
                    .caption(homestayImage.getCaption())
                    .isPrimaryImage(homestayImage.isPrimaryImage())
                    .displayOrder(homestayImage.getDisplayOrder())
                    .uploadedAt(homestayImage.getUploadedAt())
                    .build();
        }
    }
