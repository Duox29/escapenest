package com.duox.escapenest.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomestayResponse {
    String homestay_id;
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
    List<AmenityResponse> amenities;
    List<RuleResponse> rules;
}
