package com.duox.escapenest.configuration;

import com.duox.escapenest.constant.ResultCode;
import com.duox.escapenest.dto.request.ValidateTokenRequest;
import com.duox.escapenest.dto.response.ValidateTokenResponse;
import com.duox.escapenest.exception.AppException;
import com.duox.escapenest.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;

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
        } catch (Exception e) {
            throw new AppException(ResultCode.UNAUTHENTICATED);
        }
        if (Objects.isNull(nimbusJwtDecoder)){
            SecretKeySpec spec = new SecretKeySpec((signerKey.getBytes()),"HS512");
            nimbusJwtDecoder = nimbusJwtDecoder.withSecretKey(spec).macAlgorithm(MacAlgorithm.HS512).build();
        }
        return nimbusJwtDecoder.decode(token);
    }
}
