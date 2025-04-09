package com.duox.escapenest.configuration;

import com.duox.escapenest.dto.request.ValidateTokenRequest;
import com.duox.escapenest.dto.response.ValidateTokenResponse;
import com.duox.escapenest.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

@Component
public class CustomJwtDecoder  implements JwtDecoder {
    @Autowired
    private AuthenticationService authenticationService;
    private NimbusJwtDecoder nimbusJwtDecoder = null;
    @Value("${jwt.signerKey}")
    private String signerKey;
    @Override
    public Jwt decode(String token) throws JwtException {
        //temporary
    return null;
    }
}
