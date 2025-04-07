package com.duox.escapenest.dto.response;

import com.duox.escapenest.constant.Role;
import com.duox.escapenest.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {
    String email;
    Role role;
    LocalDateTime dateJoined;
    LocalDateTime lastLogin;
    boolean verified;
    boolean active;
    Status accountStatus;
}

