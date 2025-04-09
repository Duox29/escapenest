package com.duox.escapenest.service;

import com.duox.escapenest.constant.ResultCode;
import com.duox.escapenest.dto.request.AuthenticationRequest;
import com.duox.escapenest.dto.request.IntrospectRequest;
import com.duox.escapenest.dto.request.LoginRequest;
import com.duox.escapenest.dto.response.AuthencationResponse;
import com.duox.escapenest.dto.response.IntrospectResponse;
import com.duox.escapenest.dto.response.LoginResponse;
import com.duox.escapenest.entity.Account;
import com.duox.escapenest.exception.AppException;
import com.duox.escapenest.repository.AccountRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    AccountRepository accountRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    public AuthencationResponse authenticate(AuthenticationRequest request){
        var account = accountRepository.findAccountsByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ResultCode.ACCOUNT_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), account.getPasswordHash());
        if(!authenticated)
            throw new AppException(ResultCode.UNAUTHENTICATED);
        var token = generateToken(request.getEmail());
        return  AuthencationResponse.builder()
                .token(token)
                .authenticated(authenticated)
                .build();
    }
    public String generateToken(String email){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(email)
                .issuer("authentication_service")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("customerClaim","Custom").build();
        Payload payload = new Payload((jwtClaimsSet.toJSONObject()));
        JWSObject jwsObject = new JWSObject(header,payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        }
        catch (JOSEException e){
            log.error("Cannot create token",e);
            throw new RuntimeException(e);
        }
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        return IntrospectResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
            .build();
    }

    //Login
    public LoginResponse Login(LoginRequest request){
        log.info("Attempting to login user: {}",request.getEmail());
        Account account = accountRepository.findAccountsByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ResultCode.ACCOUNT_NOT_EXISTED));
        if(!bCryptPasswordEncoder.matches(request.getPassword(),account.getPasswordHash())){
            throw new AppException((ResultCode.ACCOUNT_PASSWORD_ERROR));
        }
        if(!account.isActive()){
            throw new AppException(ResultCode.ACCOUNT_NOT_ACTIVATED);
        }
        String token = generateToken(account.getEmail());
        return new LoginResponse(token);
    }
}
