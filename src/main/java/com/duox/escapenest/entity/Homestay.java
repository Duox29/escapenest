package com.duox.escapenest.entity;
import com.duox.escapenest.constant.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "homestay")
public class Homestay {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String homestay_id;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    HomestayOwner owner;
    @Column(nullable = false)
    String description;
    @Column(nullable = false)
    String type;
    @Column(nullable = false)
    String address;
    @Column(nullable = false)
    String city;
    BigDecimal latitude;
    BigDecimal longitude;
    @Column(nullable = false)
    int roomCount;
    @Column(nullable = false)
    int bedCount;
    @Column(nullable = false)
    int maxGuests;
    float sizeSqm;
    @Column(nullable = false)
    BigDecimal basePrice;
    boolean isActive;
    @OneToMany(mappedBy = "homestay", cascade = CascadeType.ALL)
    List<HomestayImage> images;
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    List<HomestayRule> rules;
    @OneToMany(mappedBy = "homestay", cascade = CascadeType.ALL)
    List<Availability> availabilityCalendar;
    @ManyToMany
    @JoinTable(
            name = "amenity",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    List<Amenity> amenities;
}
