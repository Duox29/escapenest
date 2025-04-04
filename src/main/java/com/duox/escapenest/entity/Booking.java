package com.duox.escapenest.entity;
import com.duox.escapenest.constant.BookingStatus;
import com.duox.escapenest.constant.PaymentStatus;
import com.duox.escapenest.constant.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String booking_id;
    @ManyToOne
    @JoinColumn(name = "homestay_id", nullable = false)
    Homestay homestay;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;
    @Column(nullable = false)
    LocalDate checkInDate;
    @Column(nullable = false)
    LocalDate checkOutDate;
    @Column(nullable = false)
    int guestCount;
    @Column(nullable = false)
    BigDecimal baseTotal;
    @Column(nullable = false)
    BigDecimal cleaningFee;
    @Column(nullable = false)
    BigDecimal serviceFee;
    @Column(nullable = false)
    BookingStatus status;
    @Column(nullable = false)
    PaymentStatus paymentStatus;
    @Column(nullable = false)
    LocalDateTime createdAt;
    @Column(nullable = false)
    LocalDateTime updatedAt;
    @Column(columnDefinition = "TEXT")
    String cancellationReason;
    BigDecimal refundAmount;
    boolean isActive;
    @OneToMany(mappedBy = "booking")
    List<Payment> payments;

    @OneToOne(mappedBy = "booking")
    Review review;
}
