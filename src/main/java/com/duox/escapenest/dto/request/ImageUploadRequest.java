package com.duox.escapenest.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageUploadRequest {
    String homestay_id;
    String caption;
    boolean primaryImage;
    Integer displayOrder;
}
