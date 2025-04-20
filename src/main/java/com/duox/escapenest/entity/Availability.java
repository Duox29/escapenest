package com.duox.escapenest.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "availability")
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String availability_id;
    @ManyToOne
    @JoinColumn(name = "homestay_id",nullable = false)
    Homestay homestay;
    @Column(nullable = false)
    LocalDate date;
    @Column(nullable = false)
    boolean available;

    BigDecimal customPrice;
    Integer minimumStay;
    @Column(nullable = false)
    LocalDateTime createdAt;
    @Column(nullable = false)
    LocalDateTime updatedAt;
    boolean active;
}
