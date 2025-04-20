package com.duox.escapenest.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AmenityResponse {
    String amenity_id;
    String name;
    String description;
    String icon;
    String category;
    boolean active;
}
