package com.duox.escapenest.entity;

import com.duox.escapenest.constant.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String profile_id;
    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    Account account;
    String firstName;
    String lastName;
    String phoneNumber;
    String imageUrl;
    String bio;
    String address;
    String city;
    LocalDate dateOfBirth;
    boolean isActive;
    @Enumerated(EnumType.STRING)
    Status status;
    @Column(nullable = false)
    LocalDateTime createdAt;
    @Column(nullable = false)
    LocalDateTime updatedAt;

}
