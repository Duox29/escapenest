package com.duox.escapenest.entity;
import com.duox.escapenest.constant.BookingStatus;
import com.duox.escapenest.constant.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "conversation")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String conversation_id;
    @ManyToOne
    @JoinColumn(name = "homestay_id", nullable = false)
    Homestay homestay;
    @ManyToOne
    @JoinColumn(name = "booking_id")
    Booking booking;
    @Column(nullable = false)
    LocalDateTime createdAt;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    List<Message> messages;
}
