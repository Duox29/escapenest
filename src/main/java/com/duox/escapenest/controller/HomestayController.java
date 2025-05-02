package com.duox.escapenest.controller;

import com.duox.escapenest.dto.request.FindHomestayRequest;
import com.duox.escapenest.dto.request.HomestayRequest;
import com.duox.escapenest.dto.response.AmenityResponse;
import com.duox.escapenest.dto.response.HomestayResponse;
import com.duox.escapenest.dto.response.valueObject.ResultMessage;
import com.duox.escapenest.service.HomestayService;
import com.duox.escapenest.util.ResultUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/get-hs-list")
    public ResultMessage<List<HomestayResponse>> getHomestayList()
    {
        return ResultUtil.data(homestayService.getAllHomestay().stream().toList());
    }
    @GetMapping("/get-amenity-list")
    public ResultMessage<List<AmenityResponse>> getAmenityList(){
        return ResultUtil.data(homestayService.getAllAmenity().stream().toList());
    }
    @GetMapping("/findavailablehomestays")
    public ResultMessage<List<HomestayResponse>> findAvailableHomestays(@RequestBody FindHomestayRequest request) {
            return ResultUtil.data(homestayService.searchHomestay(request).stream().toList());
    }
}
