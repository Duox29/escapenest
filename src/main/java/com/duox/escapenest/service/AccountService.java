package com.duox.escapenest.service;

import com.duox.escapenest.constant.ResultCode;
import com.duox.escapenest.constant.Role;
import com.duox.escapenest.constant.Status;
import com.duox.escapenest.dto.request.LoginRequest;
import com.duox.escapenest.dto.request.RegistrationRequest;
import com.duox.escapenest.dto.response.AccountResponse;
import com.duox.escapenest.dto.response.LoginResponse;
import com.duox.escapenest.entity.Account;
import com.duox.escapenest.exception.AppException;
import com.duox.escapenest.mapper.AccountMapper;
import com.duox.escapenest.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    AccountRepository accountRepository;
    AccountMapper accountMapper;
    AuthenticationService authenticationService;
    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;
    @NonFinal
    @Value("${jwt.valid-duration}")
    int VALID_DURATION;
    //Register
    public AccountResponse registerAccount(RegistrationRequest request){
        if(accountRepository.findAccountsByEmail(request.getEmail()).isPresent()){
            throw new AppException((ResultCode.EMAIL_EXISTED));
        }
        Account account = new Account();
        account.setEmail(request.getEmail());
        account.setPasswordHash(bCryptPasswordEncoder.encode(request.getPassword()));
        switch (request.getRole().toUpperCase()){
            case "ADMIN" -> account.setRole(Role.ADMIN);
            case "CUSTOMER" -> account.setRole(Role.CUSTOMER);
            case "OWNER" -> account.setRole(Role.OWNER);
            case "STAFF" -> account.setRole(Role.STAFF);
        }
        account.setActive(true);
        account.setDateJoined(LocalDateTime.now());
        account.setVerified(false);
        account.setAccountStatus(Status.UNFINISHED);
        log.info("Before save - dateJoined: {}, active: {}", account.getDateJoined(), account.isActive());
        accountRepository.save(account);
        return accountMapper.toAccountResponse(account);

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
        String token = authenticationService.generateToken(account.getEmail());
        return new LoginResponse(token);
    }
    public List<AccountResponse> getAll()
    {
    List<Account> accounts = accountRepository.findAll();
    return accounts.stream()
            .map(account -> AccountResponse.builder()
                    .email(account.getEmail())
                    .role(account.getRole())
                    .dateJoined(account.getDateJoined())
                    .lastLogin(account.getLastLogin())
                    .verified(account.isVerified())
                    .accountStatus(account.getAccountStatus())
                    .active(account.isActive())
                    .build())
            .collect(Collectors.toList());
    }
}
