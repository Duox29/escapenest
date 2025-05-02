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
@Table(name = "homestay_image")
public class HomestayImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String image_id;
    @ManyToOne
    @JoinColumn(name = "homestay_id", nullable = false)
    Homestay homestay;
    @Column(nullable = false)
    String imageUrl;
    @Column(columnDefinition = "TEXT")
    String caption;
    boolean primaryImage;
    Integer displayOrder;
    @Column(nullable = false)
    LocalDateTime uploadedAt;
}
