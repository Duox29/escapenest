package com.duox.escapenest.configuration;

import com.duox.escapenest.constant.ResultCode;
import com.duox.escapenest.dto.request.ValidateTokenRequest;
import com.duox.escapenest.dto.response.ValidateTokenResponse;
import com.duox.escapenest.exception.AppException;
import com.duox.escapenest.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Component
public class CustomJwtDecoder  implements JwtDecoder {
    @Autowired
    private AuthenticationService authenticationService;
    private NimbusJwtDecoder nimbusJwtDecoder = null;
    @Value("${jwt.signerKey}")
    private String signerKey;
    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            ValidateTokenResponse response = authenticationService.validateToken(ValidateTokenRequest.builder()
                    .token(token)
                    .build());
            if(!response.isValid()){
                throw new AppException(ResultCode.UNAUTHENTICATED);
            }
        } catch (AppException e) {
            throw e;
        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ResultCode.UNAUTHENTICATED,"Token valid error: " + e.getMessage(), e);
        }
        if (Objects.isNull(nimbusJwtDecoder)) {
            try {
                // Sử dụng UTF-8 encoding
                SecretKeySpec spec = new SecretKeySpec(
                        signerKey.getBytes(StandardCharsets.UTF_8),
                        "HS512"
                );
                nimbusJwtDecoder = NimbusJwtDecoder
                        .withSecretKey(spec)
                        .macAlgorithm(MacAlgorithm.HS512)
                        .build();
            } catch (Exception e) {
                throw new AppException(
                        ResultCode.UNAUTHENTICATED,
                        "Decoder initialization failed: " + e.getMessage(),
                        e
                );
            }
        }

        try {
            return nimbusJwtDecoder.decode(token);
        } catch (JwtException e) {
            // Bọc lỗi decode vào AppException
            throw new AppException(
                    ResultCode.UNAUTHENTICATED,
                    "JWT decoding failed: " + e.getMessage(),
                    e
            );
    }
}
}
