package com.duox.escapenest.entity;
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
@Table(name = "wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String wishlist_id;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    Account user;
    @Column(nullable = false)
    LocalDateTime createdAt;
    boolean isActive;
    @ManyToMany
    @JoinTable(
            name = "wishlist_properties",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "homestay_id")
    )
    List<Homestay> homestays;
}
