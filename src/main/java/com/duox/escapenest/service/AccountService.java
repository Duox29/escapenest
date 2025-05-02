package com.duox.escapenest.service;

import com.duox.escapenest.constant.ResultCode;
import com.duox.escapenest.constant.Role;
import com.duox.escapenest.constant.Status;
import com.duox.escapenest.dto.request.RegistrationRequest;
import com.duox.escapenest.dto.request.UpdatePasswordRequest;
import com.duox.escapenest.dto.response.AccountResponse;
import com.duox.escapenest.dto.response.HomestayOwnerResponse;
import com.duox.escapenest.dto.response.valueObject.UserProfileResponse;
import com.duox.escapenest.entity.Account;
import com.duox.escapenest.entity.HomestayOwner;
import com.duox.escapenest.entity.UserProfile;
import com.duox.escapenest.exception.AppException;
import com.duox.escapenest.mapper.AccountMapper;
import com.duox.escapenest.repository.AccountRepository;
import com.duox.escapenest.repository.HomestayOwnerRepository;
import com.duox.escapenest.repository.UserProfileRepository;
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
    UserProfileRepository userProfileRepository;
    AccountMapper accountMapper;
    private final HomestayOwnerRepository homestayOwnerRepository;

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

        UserProfile userProfile = UserProfile.builder()
                .account(account)
                .isActive(true)
                .status(Status.UNACTIVATED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userProfileRepository.save(userProfile);
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
    public List<HomestayOwnerResponse> getAllOwner() {
        List<HomestayOwner> homestayOwners = homestayOwnerRepository.findAll();
        return homestayOwners.stream().map(owner -> HomestayOwnerResponse.builder()
                .owner_id(owner.getOwner_id())
                .businessName(owner.getBusinessName())
                .build())
                .collect(Collectors.toList());
    }
    public String deleteAccount(String email) {
       try {
           var account = accountRepository.findAccountsByEmail(email).orElseThrow(() -> new AppException(ResultCode.ACCOUNT_NOT_EXISTED));
           accountRepository.delete(account);
           return "Account deleted";
       } catch (Exception e) {
           throw new AppException(ResultCode.UNCATEGORIZED_EXCEPTION);
       }
    }
    public UserProfileResponse getProfile(String email){
        UserProfile userProfile = userProfileRepository.findByEmail(email).orElseThrow(() -> new AppException(ResultCode.PROFILE_NOT_FOUND));
        return UserProfileResponse.builder()
                .profile_id(userProfile.getProfile_id())
                .firstName(userProfile.getFirstName())
                .lastName(userProfile.getLastName())
                .phoneNumber(userProfile.getPhoneNumber())
                .imageUrl(userProfile.getImageUrl())
                .bio(userProfile.getBio())
                .address(userProfile.getAddress())
                .city(userProfile.getCity())
                .dateOfBirth(userProfile.getDateOfBirth())
                .status(userProfile.getStatus())
                .build();
    }

    public boolean updatePassword(UpdatePasswordRequest request) {
        try {
            Account account = accountRepository.findAccountsByEmail(request.getEmail()).orElseThrow(() -> new AppException(ResultCode.ACCOUNT_NOT_EXISTED));
            if(!bCryptPasswordEncoder.matches(request.getOldPassword(),account.getPasswordHash())) {
                throw new AppException(ResultCode.ACCOUNT_PASSWORD_ERROR);
            } else {
                account.setPasswordHash(bCryptPasswordEncoder.encode(request.getNewPassword()));
                accountRepository.save(account);
                return true;
            }
        } catch (Exception e){
            log.info("Error while update password", e.getMessage());
            throw new AppException(ResultCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
