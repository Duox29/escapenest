package com.duox.escapenest.repository;

import com.duox.escapenest.entity.HomestayImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomestayImageRepository extends JpaRepository<HomestayImage, String> {
}