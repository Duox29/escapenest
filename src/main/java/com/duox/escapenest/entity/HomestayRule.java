package com.duox.escapenest.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "homestay_rules")
public class HomestayRule {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String homestayRule_id;

    @ManyToOne
    @JoinColumn(name = "homestay_id", nullable = false)
    Homestay homestay;
    @Column(nullable = false, columnDefinition = "TEXT")
    String ruleDescription;
    boolean major;
}
