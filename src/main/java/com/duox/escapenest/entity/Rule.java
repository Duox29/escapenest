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
@Table(name = "rule")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String rule_id;
    @Column(nullable = false, columnDefinition = "TEXT")
    String ruleDescription;
    boolean major;
    boolean active;
    @ManyToMany(mappedBy = "rules")
    List<Homestay> homestays;
}
