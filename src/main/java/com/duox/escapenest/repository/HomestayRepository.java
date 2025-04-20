package com.duox.escapenest.repository;

import com.duox.escapenest.entity.Homestay;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomestayRepository extends JpaRepository<Homestay, String> {
    @Query("SELECT hs from Homestay hs where hs.homestay_id =:id")
   Optional<Homestay> findByHomestay_id(@Param("id") String id);
}