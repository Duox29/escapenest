package com.duox.escapenest.service.consumer;

import com.duox.escapenest.dto.response.BrevoResponse;
import com.duox.escapenest.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class EmailConsumer {
    EmailService emailService;
    @KafkaListener(topics = "otp-topic", groupId = "escapenest")
    public String sendOtp(String email){
        BrevoResponse response=emailService.sendOtp(email);
        return response.getMessageId();
    }
}
