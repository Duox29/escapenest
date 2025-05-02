package com.duox.escapenest.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomestayRequest {
    String homestay_id;
    String owner_id;
    String name;
    String description;
    String type;
    String address;
    String city;
    BigDecimal latitude;
    BigDecimal longitude;
    int roomCount;
    int bedCount;
    int maxGuests;
    float sizeSqm;
    BigDecimal basePrice;
    boolean isActive;
    List<String> amenities;
    List<String> rules;
}