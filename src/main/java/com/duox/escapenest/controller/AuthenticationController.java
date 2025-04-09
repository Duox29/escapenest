package com.duox.escapenest.controller;

import com.duox.escapenest.dto.request.BrevoRequest;
import com.duox.escapenest.dto.request.LoginRequest;
import com.duox.escapenest.dto.response.BrevoResponse;
import com.duox.escapenest.dto.response.LoginResponse;
import com.duox.escapenest.dto.response.valueObject.ResultMessage;
import com.duox.escapenest.service.AuthenticationService;
import com.duox.escapenest.service.EmailService;
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
    EmailService emailService;
    @GetMapping("/testapi")
    public ResultMessage<String> testAPI(){
        return ResultUtil.data("test api");
    }
    @GetMapping("/login")
    public ResultMessage<LoginResponse> login(@RequestBody LoginRequest request){
        return ResultUtil.data(authenticationService.Login(request));
    }
    @GetMapping("/sendotp")
    public ResultMessage<BrevoResponse> sendOtp(@RequestParam("email") String email){
        return ResultUtil.data(emailService.sendOtp(email));
    }
}
