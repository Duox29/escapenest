package com.duox.escapenest.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String payment_id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    Booking booking;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

    @Column(nullable = false)
    BigDecimal amount;
    @Column(nullable = false)
    String currency;
    @Column(nullable = false)
    String paymentMethod;
    String transactionId;
    @Column(nullable = false)
    String status;
    @Column(nullable = false)
    LocalDateTime createdAt;
    boolean refund;
    @Column(columnDefinition = "JSON")
    String paymentDetails;
}
