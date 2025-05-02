package com.duox.escapenest.repository;

import com.duox.escapenest.entity.Homestay;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HomestayRepository extends JpaRepository<Homestay, String> {
    @Query("SELECT hs from Homestay hs where hs.homestay_id =:id")
   Optional<Homestay> findByHomestay_id(@Param("id") String id);

    @Query("SELECT DISTINCT h FROM Homestay h " +
            "LEFT JOIN FETCH h.amenities " +
            "LEFT JOIN FETCH h.rules " +
            "WHERE h.isActive = true " +
            "AND h.homestay_id NOT IN (" +
            "    SELECT b.homestay.homestay_id FROM Booking b " +
            "    WHERE b.isActive = true " +
            "    AND b.status IN (com.duox.escapenest.constant.BookingStatus.CONFIRMED, " +
            "                    com.duox.escapenest.constant.BookingStatus.PENDING) " +
            "    AND (b.checkInDate < :checkOutDate AND b.checkOutDate > :checkInDate)" +
            ")")
    List<Homestay> findAvailableHomestays(
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate);
}