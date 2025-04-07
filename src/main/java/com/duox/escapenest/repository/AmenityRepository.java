package com.duox.escapenest.repository;

import com.duox.escapenest.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, String> {
    boolean existsByName(String name);
    Optional<Amenity> findByName(String name);
}
