package com.duox.escapenest.dto.request;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileUpdateRequest {
    String firstName;
    String lastName;
    String phoneNumber;
    String imageUrl;
    String bio;
    String address;
    String city;
    LocalDate dateOfBirth;
}
