package com.duox.escapenest.service.producer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class EmailProducer {
    KafkaTemplate<String, String> kafkaTemplate;
    String OTP_TOPIC = "otp-topic";

    public String sendOpt(String email) {
        kafkaTemplate.send(OTP_TOPIC, email);
        return "OTP sent";
    }
}
