    package com.duox.escapenest.entity;
    import jakarta.persistence.*;
    import lombok.*;
    import lombok.experimental.FieldDefaults;

    import java.math.BigDecimal;
    import java.util.List;
    import java.util.Set;

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
        HomestayOwner homestay_owner;
        @Column(nullable = false)
        String name;
        @Column(nullable = false)
        String description;
        @Column(nullable = false)
        String type;
        @Column(nullable = false)
        String address;
        @Column(nullable = false)
        String city;
        @Column(precision = 12, scale = 8)
        BigDecimal latitude;
        @Column(precision = 12, scale = 8)
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
        @OneToMany(mappedBy = "homestay", cascade = CascadeType.ALL)
        List<Availability> availabilityCalendar;
        @org.hibernate.annotations.BatchSize(size = 30)
        @ManyToMany
        @JoinTable(
                name = "homestay_amenities",
                joinColumns = @JoinColumn(name = "homestay_id"),
                inverseJoinColumns = @JoinColumn(name = "amenity_id")
        )
        Set<Amenity> amenities;
        @org.hibernate.annotations.BatchSize(size = 30)
        @ManyToMany
        @JoinTable(
                name = "homestay_rules",
                joinColumns = @JoinColumn(name = "homestay_id"),
                inverseJoinColumns = @JoinColumn(name = "rule_id")
        )
        Set<Rule> rules;
    }
