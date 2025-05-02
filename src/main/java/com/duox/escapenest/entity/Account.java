package com.duox.escapenest.entity;

import com.duox.escapenest.constant.Role;
import com.duox.escapenest.constant.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String account_id;
    @Column(unique = true, nullable = false)
    String email;
    @Column(nullable = false)
    String passwordHash;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Role role;
    LocalDateTime dateJoined;
    LocalDateTime lastLogin;
    boolean verified;
    boolean active;
    @Enumerated(EnumType.STRING)
    Status accountStatus;
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    UserProfile userProfile;
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    Customer customer;
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    HomestayOwner homestayOwner;
}
