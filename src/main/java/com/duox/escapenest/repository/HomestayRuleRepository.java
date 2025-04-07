package com.duox.escapenest.repository;

import com.duox.escapenest.entity.Homestay;
import com.duox.escapenest.entity.HomestayRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomestayRuleRepository extends JpaRepository<HomestayRule, String> {
}