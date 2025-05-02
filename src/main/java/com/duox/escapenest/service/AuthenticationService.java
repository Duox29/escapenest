package com.duox.escapenest.service;

import com.duox.escapenest.constant.ResultCode;
import com.duox.escapenest.constant.Status;
import com.duox.escapenest.dto.request.*;
import com.duox.escapenest.dto.response.*;
import com.duox.escapenest.entity.Account;
import com.duox.escapenest.entity.UserProfile;
import com.duox.escapenest.exception.AppException;
import com.duox.escapenest.repository.AccountRepository;
import com.duox.escapenest.repository.UserProfileRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    AccountRepository accountRepository;
    UserProfileRepository userProfileRepository;
    RedisTemplate<String,String> redisTemplate;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    @NonFinal
    @Value("${jwt.valid-duration}")
    protected int VALID_DURATION;
    final String OTP_PREFIX = "otp:";

    public AuthencationResponse authenticate(AuthenticationRequest request){
        var account = accountRepository.findAccountsByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ResultCode.ACCOUNT_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (!passwordEncoder.matches(request.getPassword(), account.getPasswordHash())) {
            throw new AppException(ResultCode.UNAUTHENTICATED);
        }
        var token = generateToken(request.getEmail());
        return AuthencationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public String generateToken(String email){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString())
                .subject(email)
                .issuer("authentication_service")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(VALID_DURATION,ChronoUnit.SECONDS)
                ))
                .claim("customerClaim","Custom").build();
        Payload payload = new Payload((jwtClaimsSet.toJSONObject()));
        JWSObject jwsObject = new JWSObject(header,payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes(StandardCharsets.UTF_8)));
            return jwsObject.serialize();
        }
        catch (JOSEException e){
            log.error("Cannot create token",e);
            throw new RuntimeException(e);
        }
    }
    public ValidateTokenResponse validateToken(ValidateTokenRequest request){
        String token = request.getToken();
        boolean isValid = true;
        try{
            verifyToken(token,true);
        } catch (Exception e) {
            log.info("Lỗi là: "+ e.getMessage());
            isValid = false;
        }
        log.info(String.valueOf(isValid));
        return ValidateTokenResponse.builder()
                .valid(isValid).build();
    }
    public SignedJWT verifyToken(String token, boolean flag){
        try{
            JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes(StandardCharsets.UTF_8));
            SignedJWT signedJWT = SignedJWT.parse(token);
            boolean verified = signedJWT.verify(jwsVerifier);
            if(!verified || redisTemplate.hasKey("invalidToken:"+ signedJWT.getJWTClaimsSet().getJWTID()))
                throw new AppException(ResultCode.UNAUTHENTICATED);
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            if(expiryTime.before(new Date()) && flag){
                String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
                redisTemplate.opsForValue().set("invalidToken:"+jwtId,token,VALID_DURATION + 10, TimeUnit.SECONDS);
                throw new AppException(ResultCode.UNAUTHENTICATED);
            }
            return signedJWT;
        } catch (ParseException | JOSEException e) {
            throw new AppException(ResultCode.UNAUTHENTICATED);
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
        account.setLastLogin(LocalDateTime.now());
        accountRepository.save(account);
        UserProfile userProfile = userProfileRepository.findByAccount_id(account.getAccount_id()).orElseGet(() -> {
            UserProfile profile = UserProfile.builder()
                    .account(account)
                    .firstName("")
                    .lastName("")
                    .phoneNumber("")
                    .imageUrl("")
                    .bio("")
                    .address("")
                    .city("")
                    .dateOfBirth(null)
                    .isActive(true)
                    .status(Status.UNFINISHED)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            return userProfileRepository.save(profile);
        });

        log.info("User {} has logged",account.getAccount_id());
        return LoginResponse.builder()
                .token(token)
                .email(account.getEmail())
                .name(userProfile.getLastName())
                .role(account.getRole())
                .build();
    }
    public void logout(String token){
        log.info("Starting logout process for token");
        int ttlInSeconds = VALID_DURATION + 10;
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
            log.info("Found JWT ID: {}", jwtId);
            redisTemplate.opsForValue().set("invalidToken:"+jwtId,token,ttlInSeconds,TimeUnit.SECONDS);
            log.info("JWT {} has been pushed into cache and will be expired in {}", jwtId,ttlInSeconds);
        } catch (ParseException e){
            log.error("Error in analysing JWT: ",e);
            throw new AppException(ResultCode.UNAUTHENTICATED);
        }
    }

    public String refreshToken(String oldToken) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(oldToken);

            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes(StandardCharsets.UTF_8));
            if (!signedJWT.verify(verifier)) {
                throw new AppException(ResultCode.INVALID_TOKEN);
            }
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            Date expirationTime = claims.getExpirationTime();
            if (new Date().after(expirationTime)) {
                throw new AppException(ResultCode.TOKEN_EXPIRED);
            }
            JWTClaimsSet newClaimsSet = new JWTClaimsSet.Builder()
                    .subject(claims.getSubject())
                    .issuer(claims.getIssuer())
                    .issueTime(new Date())
                    .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                    .jwtID(UUID.randomUUID().toString())  // Tạo ID mới cho token
                    .claim("scope", claims.getClaim("scope"))  // Sử dụng lại scope cũ
                    .build();
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
            Payload payload = new Payload(newClaimsSet.toJSONObject());
            JWSObject jwsObject = new JWSObject(header, payload);
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes(StandardCharsets.UTF_8)));
            return jwsObject.serialize();
        } catch (ParseException | JOSEException e) {
            throw new AppException(ResultCode.CANNOT_REFRESH_TOKEN);
        }
    }

    public ActivateAccountResponse activateAccount(ActiveAccountRequest request)
    {
        String redisKey = OTP_PREFIX + request.getEmail();
        log.info("1- REDIS KEY:",redisKey);
        String storedOtp = redisTemplate.opsForValue().get(redisKey);
        if(storedOtp != null && storedOtp.equals(request.getOtp())){
            redisTemplate.delete(redisKey);
            var account = accountRepository.findAccountsByEmail(request.getEmail()).orElseThrow(() -> new AppException(ResultCode.ACCOUNT_NOT_EXISTED));
            account.setAccountStatus(Status.UNFINISHED);
            accountRepository.save(account);
            return ActivateAccountResponse.builder()
                    .email(request.getEmail())
                    .message("Account activated").build();
        } else {
            throw new AppException(ResultCode.INVALID_OTP);
        }
    }

}
