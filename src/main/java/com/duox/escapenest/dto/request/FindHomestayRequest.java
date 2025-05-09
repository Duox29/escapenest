package com.duox.escapenest.dto.request;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class FindHomestayRequest {
    String city;
    LocalDate checkInDate;
    LocalDate checkOutDate;
    int numberOfGuest;
}
