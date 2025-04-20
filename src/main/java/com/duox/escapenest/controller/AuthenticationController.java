package com.duox.escapenest.controller;

import com.duox.escapenest.dto.request.*;
import com.duox.escapenest.dto.response.*;
import com.duox.escapenest.dto.response.valueObject.ResultMessage;
import com.duox.escapenest.exception.AppException;
import com.duox.escapenest.service.AccountService;
import com.duox.escapenest.service.AuthenticationService;
import com.duox.escapenest.service.producer.EmailProducer;
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
    EmailProducer emailProducer;

    @PostMapping("/login")
    public ResultMessage<LoginResponse> login(@RequestBody LoginRequest request){
        return ResultUtil.data(authenticationService.Login(request));
    }
    @GetMapping("/sendotp")
    public ResultMessage<String> sendOtp(@RequestParam("email") String email){
        return ResultUtil.data(emailProducer.sendOpt(email));
    }
    @PostMapping("/register")
    public ResultMessage<AccountResponse> registerAccount(@RequestBody RegistrationRequest request){
        return ResultUtil.data(accountService.registerAccount(request));
    }
    @PostMapping("/logout")
    public ResultMessage<String> logout(@RequestParam("jwt") String jwt){
        authenticationService.logout(jwt);
        return ResultUtil.data("Logout successful");
    }
    @PostMapping("/refreshToken")
    public ResultMessage<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            String newToken = authenticationService.refreshToken(request.getToken());
            return ResultUtil.data(new TokenResponse(newToken));
        } catch (AppException e) {
            return ResultUtil.error(e.getResultCode());
        }
    }
    @PostMapping("/validateToken")
    public ResultMessage<ValidateTokenResponse> validateToken(@RequestBody ValidateTokenRequest request) {
        return ResultUtil.data(authenticationService.validateToken(request));
    }
    @PostMapping("/activateAccount")
    public ResultMessage<ActivateAccountResponse> activeAccount(@RequestBody ActiveAccountRequest request){
        return  ResultUtil.data(authenticationService.activateAccount(request));
    }
}
