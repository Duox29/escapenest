package com.duox.escapenest.controller;

import com.duox.escapenest.dto.request.HomestayRequest;
import com.duox.escapenest.dto.response.HomestayResponse;
import com.duox.escapenest.dto.response.valueObject.ResultMessage;
import com.duox.escapenest.service.HomestayService;
import com.duox.escapenest.util.ResultUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/homestay")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HomestayController {
    HomestayService homestayService;
    @PostMapping("/create")
    public ResultMessage<HomestayResponse> createHomestay(@RequestBody HomestayRequest request){
        return ResultUtil.data(homestayService.createHomestay(request));
    }
}
