package com.duox.escapenest.dto.response;

import com.duox.escapenest.constant.Role;
import com.duox.escapenest.constant.Status;

import java.time.LocalDateTime;
import java.util.Stack;

public class GetAllResponse {
    String email;
    Role role;
    LocalDateTime joinedAt;
    boolean verified;
    boolean isActive;
    Status accountStatus;
}
