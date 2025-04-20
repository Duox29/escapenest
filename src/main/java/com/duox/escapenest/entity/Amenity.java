package com.duox.escapenest.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "amenity")
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String amenity_id;
    @Column(nullable = false)
    String name;
    @Column(columnDefinition = "TEXT")
    String description;
    String icon;
    String category;
    boolean active;
    @ManyToMany(mappedBy = "amenities")
    List<Homestay> homestays;
}
