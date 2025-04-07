package com.duox.escapenest.controller;

import com.duox.escapenest.dto.request.RegistrationRequest;
import com.duox.escapenest.dto.response.AccountResponse;
import com.duox.escapenest.dto.response.valueObject.ResultMessage;
import com.duox.escapenest.entity.Account;
import com.duox.escapenest.service.AccountService;
import com.duox.escapenest.service.AuthenticationService;
import com.duox.escapenest.util.ResultUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    AccountService accountService;
    @GetMapping("/testapi")
    public ResultMessage<String> testAPI(){
        return ResultUtil.data("test api");
    }
    @PostMapping("/register")
    public ResultMessage<AccountResponse> registerAccount(@RequestBody RegistrationRequest request){
        return ResultUtil.data(accountService.registerAccount(request));
    }
}
