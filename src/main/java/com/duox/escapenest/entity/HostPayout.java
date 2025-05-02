package com.duox.escapenest.entity;
import com.duox.escapenest.constant.PaymentStatus;
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
@Table(name = "host_payout")
public class HostPayout {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String hostpayout_id;
    @ManyToOne
    @JoinColumn(name = "host_id",nullable = false)
    HomestayOwner host;
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    Booking booking;
    @Column(nullable = false)
    BigDecimal amount;
    @Column(nullable = false)
    String currency;
    @Column(nullable = false)
    PaymentStatus paymentStatus;
    String payoutMethod;
    @Column(nullable = false)
    LocalDateTime createdAt;
    LocalDateTime processedAt;
    String transactionId;
}
