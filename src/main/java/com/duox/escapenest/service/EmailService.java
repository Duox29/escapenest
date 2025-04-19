package com.duox.escapenest.service;

import com.duox.escapenest.dto.request.BrevoRequest;
import com.duox.escapenest.dto.request.Recipient;
import com.duox.escapenest.dto.request.Sender;
import com.duox.escapenest.dto.response.BrevoResponse;
import com.duox.escapenest.repository.client.BrevoClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.duox.escapenest.util.OtpEmailTemplate.generateOtpEmail;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class EmailService {
    BrevoClient brevoClient;
    RedisTemplate<String, String> redisTemplate;
    @Value("${notification.email.brevo-api}")
    @NonFinal
    String apiKey;


    final String OTP_PREFIX = "otp:";
    final String LINK_PREFIX = "link:";
    final String NOTIFICATION_PREFIX = "notification:";
    public BrevoResponse sendOtp(String email){
        try{
            Random random = new Random();
            String otp = String.format("%06d", 100000 + random.nextInt(900000));
            String redisKey = OTP_PREFIX + email;
            redisTemplate.opsForValue().set(redisKey, String.valueOf(otp),5, TimeUnit.MINUTES);
            String htmlContent = generateOtpEmail(email, otp);

            BrevoRequest emailRequest = BrevoRequest.builder()
                    .sender(Sender.builder()
                            .name("EscapeNest")
                            .email("duong6a123@gmail.com")
                            .build())
                    .to(List.of(Recipient.builder()
                            .email(email)
                            .name("x")
                            .build()))
                    .subject("Xác nhận email của bạn")
                    .htmlContent(htmlContent)
                    .build();
            return brevoClient.sendEmail(apiKey,emailRequest);
        } catch (Exception e){
            log.error("Failed to send email to: "+email, e);
            throw new RuntimeException("Email sending failed",e);
        }
    }
}
