package com.duox.escapenest.repository.client;

import com.duox.escapenest.dto.request.BrevoRequest;
import com.duox.escapenest.dto.response.BrevoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "brevo-client", url = "${notification.email.brevo-url}")
    public interface BrevoClient {
    @PostMapping(value = "/v3/smtp/email", produces = MediaType.APPLICATION_JSON_VALUE)
    BrevoResponse sendEmail(@RequestHeader("api-key") String apiKey, @RequestBody BrevoRequest request);
}
