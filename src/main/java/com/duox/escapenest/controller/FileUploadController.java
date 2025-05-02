package com.duox.escapenest.controller;

import com.duox.escapenest.constant.ResultCode;
import com.duox.escapenest.dto.request.ImageUploadRequest;
import com.duox.escapenest.dto.response.FileUploadResponse;
import com.duox.escapenest.dto.response.ImageUploadResponse;
import com.duox.escapenest.dto.response.valueObject.ResultMessage;
import com.duox.escapenest.service.FileUploadService;
import com.duox.escapenest.util.ResultUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileUploadController {
    FileUploadService fileUploadService;
    @PostMapping("/upload")
    public ResultMessage<FileUploadResponse> uploadImage(@RequestParam("image")MultipartFile file) {
        return ResultUtil.data(fileUploadService.uploadImage(file));
    }
    @PostMapping("/upload-multiple")
    public ResponseEntity<?> uploadImages(@RequestParam("images") MultipartFile[] files){
        fileUploadService.uploadImages(files);
        return ResponseEntity.ok(ResultUtil.success(ResultCode.SUCCESS));
    }
    @PostMapping("hs-img-upload")
    public ResultMessage<ImageUploadResponse> hsImgUpload(@RequestParam("image") MultipartFile file, @RequestPart("request") ImageUploadRequest request) {
        return ResultUtil.data(fileUploadService.uploadHomestayImage(file, request));
    }
}
