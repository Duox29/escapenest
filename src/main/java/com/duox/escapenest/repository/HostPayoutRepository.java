package com.duox.escapenest.repository;

import com.duox.escapenest.entity.HomestayOwner;
import com.duox.escapenest.entity.HostPayout;
import org.apache.catalina.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HostPayoutRepository extends JpaRepository<HostPayout, String> {
}