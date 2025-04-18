package com.duox.escapenest.repository;

import com.duox.escapenest.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
}