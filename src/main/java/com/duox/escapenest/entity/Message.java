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
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String message_id;

    @ManyToOne
    @JoinColumn(name = "conversation_id",nullable = false)
    Conversation conversation;
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    Account sender;
    @Column(nullable = false, columnDefinition = "TEXT")
    String messageText;
    @Column(nullable = false)
    LocalDateTime sentAt;
    LocalDateTime readAt;
    String attachmentUrl;
}
