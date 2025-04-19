package com.duox.escapenest.service;

import com.duox.escapenest.constant.ResultCode;
import com.duox.escapenest.constant.Role;
import com.duox.escapenest.constant.Status;
import com.duox.escapenest.dto.request.RegistrationRequest;
import com.duox.escapenest.dto.response.AccountResponse;
import com.duox.escapenest.entity.Account;
import com.duox.escapenest.exception.AppException;
import com.duox.escapenest.mapper.AccountMapper;
import com.duox.escapenest.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
        account.setAccountStatus(Status.UNACTIVATED);
        log.info("Before save - dateJoined: {}, active: {}", account.getDateJoined(), account.isActive());
        accountRepository.save(account);
        return accountMapper.toAccountResponse(account);

    }

    public List<AccountResponse> getAll()
    {
    List<Account> accounts = accountRepository.findAll();
    return accounts.stream().map(account -> AccountResponse.builder()
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
