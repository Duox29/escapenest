package com.duox.escapenest.entity;
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
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String notification_id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    Account user;
    @Column(nullable = false)
    String type;

    @Column(nullable = false)
    String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    String message;
    boolean isRead;

    String relatedId;

    String relatedType;
    @Column(nullable = false)
    LocalDateTime createdAt;

    String notificationUrl;
    boolean isActive;
}
