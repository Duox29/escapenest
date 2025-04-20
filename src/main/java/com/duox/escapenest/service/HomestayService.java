package com.duox.escapenest.service;

import com.duox.escapenest.constant.ResultCode;
import com.duox.escapenest.dto.request.HomestayRequest;
import com.duox.escapenest.dto.response.AmenityResponse;
import com.duox.escapenest.dto.response.HomestayResponse;
import com.duox.escapenest.dto.response.RuleResponse;
import com.duox.escapenest.entity.Amenity;
import com.duox.escapenest.entity.Homestay;
import com.duox.escapenest.entity.Rule;
import com.duox.escapenest.exception.AppException;
import com.duox.escapenest.repository.AmenityRepository;
import com.duox.escapenest.repository.HomestayRepository;
import com.duox.escapenest.repository.RuleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class HomestayService {
    HomestayRepository homestayRepository;
    AmenityRepository amenityRepository;
    RuleRepository ruleRepository;
    public List<HomestayResponse> getAllHomestay(){
        List<Homestay> homestays = homestayRepository.findAll();
        return toResponseList(homestays);
    }
    public HomestayResponse getHomestayById(String id){
        try {
            var homestay = homestayRepository.findByHomestay_id(id).orElseThrow(() -> new AppException(ResultCode.HOMESTAY_NOT_EXISTED));
            return toResponse(homestay);
        } catch (Exception e) {
            log.error("Error: "+ e.getMessage());
            throw new AppException(ResultCode.UNCATEGORIZED_EXCEPTION);
        }
    }
    public HomestayResponse createHomestay(HomestayRequest request){
        Homestay homestay = toEntity(request);
        homestayRepository.save(homestay);
        return toResponse(homestay);
    }

    public HomestayResponse updateHomestay(HomestayRequest request)
    {
        try{
            var homestay = homestayRepository.findByHomestay_id(request.getHomestay_id()).orElseThrow(() -> new AppException(ResultCode.HOMESTAY_NOT_EXISTED));
            homestay = toEntity(request);
            homestayRepository.save(homestay);
            return toResponse(homestay);
        } catch (Exception e){
            log.error("Error: "+ e.getMessage());
            throw new AppException(ResultCode.UNCATEGORIZED_EXCEPTION);
        }
    }
    public void deleteHomestay(String id){
        try {
            homestayRepository.deleteById(id);
        } catch (Exception e){
            log.error("Error: "+ e.getMessage());
            throw new AppException(ResultCode.UNCATEGORIZED_EXCEPTION);
        }
    }
    public boolean deactivateHomestay(String id){
        try {
            var homestay = homestayRepository.findByHomestay_id(id).orElseThrow(() -> new AppException(ResultCode.HOMESTAY_NOT_EXISTED));
            homestay.setActive(false);
            return true;
        } catch (Exception e){
            log.error("Error: "+ e.getMessage());
            throw new AppException(ResultCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    private HomestayResponse toResponse(Homestay homestay){
        return HomestayResponse.builder()
                .homestay_id(homestay.getHomestay_id())
                .description(homestay.getDescription())
                .type(homestay.getType())
                .address(homestay.getAddress())
                .city(homestay.getCity())
                .latitude(homestay.getLatitude())
                .longitude(homestay.getLongitude())
                .roomCount(homestay.getRoomCount())
                .bedCount(homestay.getBedCount())
                .maxGuests(homestay.getMaxGuests())
                .sizeSqm(homestay.getSizeSqm())
                .basePrice(homestay.getBasePrice())
                .isActive(homestay.isActive())
                .amenities(homestay.getAmenities().stream()
                        .map(amenity -> AmenityResponse.builder()
                                .amenity_id(amenity.getAmenity_id())
                                .name(amenity.getName())
                                .description(amenity.getDescription())
                                .icon(amenity.getIcon())
                                .category(amenity.getCategory())
                                .active(amenity.isActive())
                                .build()
                        ).collect(Collectors.toList()))
                .rules(homestay.getRules().stream()
                        .map(rule -> RuleResponse.builder()
                                .rule_id(rule.getRule_id())
                                .ruleDescription(rule.getRuleDescription())
                                .major(rule.isMajor())
                                .active(rule.isActive())
                                .build()
                        ).collect(Collectors.toList()))
                .build();
    }

    private Homestay toEntity(HomestayRequest request){
        List<Amenity> amenities = amenityRepository.findAllById(request.getAmenities());
        List<Rule> rules = ruleRepository.findAllById(request.getRules());
        return Homestay.builder()
                .description(request.getDescription())
                .type(request.getType())
                .address(request.getAddress())
                .city(request.getCity())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .roomCount(request.getRoomCount())
                .bedCount(request.getBedCount())
                .maxGuests(request.getMaxGuests())
                .sizeSqm(request.getSizeSqm())
                .basePrice(request.getBasePrice())
                .isActive(request.isActive())
                .amenities(amenities)
                .rules(rules)
                .build();
    }
    private  List<HomestayResponse> toResponseList(List<Homestay> homestays) {
        return homestays.stream().map(this::toResponse).collect(Collectors.toList());
    }
}