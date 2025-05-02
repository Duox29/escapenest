package com.duox.escapenest.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageUploadResponse {
    String image_id;
    String imageUrl;
    String homestay_id;
    String caption;
    boolean isPrimaryImage;
    int displayOrder;
    LocalDateTime uploadedAt;
}
