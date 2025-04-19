package com.duox.escapenest.controller;

import com.duox.escapenest.dto.request.ProfileUpdateRequest;
import com.duox.escapenest.dto.request.RegistrationRequest;
import com.duox.escapenest.dto.response.AccountResponse;
import com.duox.escapenest.dto.response.valueObject.ResultMessage;
import com.duox.escapenest.dto.response.valueObject.UserProfileResponse;
import com.duox.escapenest.service.AccountService;
import com.duox.escapenest.service.UserProfileService;
import com.duox.escapenest.util.ResultUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    private final AccountService accountService;
    UserProfileService userProfileService;
    @PostMapping("/register")
    public ResultMessage<AccountResponse> registerAccount(@RequestBody RegistrationRequest request){
        return ResultUtil.data(accountService.registerAccount(request));
    }
    @GetMapping("/getall")
    List<AccountResponse> getAll(){
        return accountService.getAll();
    }
    @PostMapping("/updateprofile")
    public ResultMessage<UserProfileResponse> updateProfile(ProfileUpdateRequest request){
        return ResultUtil.data(userProfileService.updateProfile(request));
    }
}
