package com.duox.escapenest.repository;

import com.duox.escapenest.constant.Status;
import com.duox.escapenest.entity.HomestayOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HomestayOwnerRepository extends JpaRepository<HomestayOwner, String> {
}