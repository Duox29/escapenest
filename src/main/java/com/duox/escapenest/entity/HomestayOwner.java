package com.duox.escapenest.entity;
import com.duox.escapenest.constant.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "homestay_owner")
public class HomestayOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String owner_id;
    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    Account account;
    @Enumerated(EnumType.STRING)
    Status status;
    @Column(nullable = false)
    String businessName;
    boolean identityVerified;
    boolean businessVerified;
    @Column(nullable = false)
    LocalDateTime createdAt;

    @Column(nullable = false)
    LocalDateTime updatedAt;

    boolean isActive;
    @OneToMany(mappedBy = "homestay_owner")
    List<Homestay> homestays;
}
