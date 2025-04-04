package com.duox.escapenest.entity;
import com.duox.escapenest.constant.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String customer_id;
    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    Account account;
    int totalBookings;
    int completedBookings;
    int cancelledBookings;
    @Column(nullable = false)
    LocalDateTime createdAt;

    @Column(nullable = false)
    LocalDateTime updatedAt;
    boolean isActive;
    @OneToMany(mappedBy = "customer")
    List<Booking> bookings;

    @OneToMany(mappedBy = "customer")
    List<Wishlist> wishlists;
}
