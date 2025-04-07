package com.duox.escapenest.dto.response.valueObject;
import com.duox.escapenest.constant.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileResponse {
    String profile_id;
    String account_id;
    String firstName;
    String lastName;
    String phoneNumber;
    String imageUrl;
    String bio;
    String address;
    String city;
    LocalDate dateOfBirth;
    boolean isActive;
    Status status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
