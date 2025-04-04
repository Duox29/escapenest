package com.duox.escapenest.entity;
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
@Table(name = "payment_method")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String paymentMethod_id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    Account user;
    @Column(nullable = false)
    String provider;
    @Column(nullable = false)
    String accountDetails;
    boolean defaultMethod;
    @Column(nullable = false)
    LocalDateTime createdAt;
    String lastFour;
    String expiryDate;
    Status method_status;
    boolean isActive;
}
