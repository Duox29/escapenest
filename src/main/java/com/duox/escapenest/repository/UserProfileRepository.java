package com.duox.escapenest.repository;

import com.duox.escapenest.entity.UserProfile;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
    @Query("SELECT up FROM UserProfile up WHERE up.profile_id = :id")
    Optional<UserProfile> findByProfile_id(@Param("id") String id);

    @Query("SELECT up FROM UserProfile up WHERE up.account.account_id = :id")
    Optional<UserProfile> findByAccount_id(@Param("id") String id);
    @Query("SELECT up FROM UserProfile up WHERE up.account.email = :email")
    Optional<UserProfile> findByEmail(@Param("email") String email);
}