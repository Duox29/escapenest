package com.duox.escapenest.service;

import com.duox.escapenest.constant.ResultCode;
import com.duox.escapenest.dto.request.FindHomestayRequest;
import com.duox.escapenest.dto.request.HomestayRequest;
import com.duox.escapenest.dto.response.AmenityResponse;
import com.duox.escapenest.dto.response.HomestayResponse;
import com.duox.escapenest.dto.response.RuleResponse;
import com.duox.escapenest.entity.Amenity;
import com.duox.escapenest.entity.Homestay;
import com.duox.escapenest.entity.HomestayOwner;
import com.duox.escapenest.entity.Rule;
import com.duox.escapenest.exception.AppException;
import com.duox.escapenest.repository.AmenityRepository;
import com.duox.escapenest.repository.HomestayOwnerRepository;
import com.duox.escapenest.repository.HomestayRepository;
import com.duox.escapenest.repository.RuleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class HomestayService {
    HomestayRepository homestayRepository;
    AmenityRepository amenityRepository;
    RuleRepository ruleRepository;
    HomestayOwnerRepository homestayOwnerRepository;

    public List<HomestayResponse> getAllHomestay(){
        List<Homestay> homestays = homestayRepository.findAll();
        return toHomestayResponseList(homestays);
    }
    public HomestayResponse getHomestayById(String id){
        try {
            var homestay = homestayRepository.findByHomestay_id(id).orElseThrow(() -> new AppException(ResultCode.HOMESTAY_NOT_EXISTED));
            return toHomestayResponse(homestay);
        } catch (Exception e) {
            throw new AppException(ResultCode.UNCATEGORIZED_EXCEPTION);
        }
    }
    public HomestayResponse createHomestay(HomestayRequest request){
        Homestay homestay = toHomestayEntity(request);
        homestayRepository.save(homestay);
        return toHomestayResponse(homestay);
    }

    public HomestayResponse updateHomestay(HomestayRequest request)
    {
        try{
            var homestay = homestayRepository.findByHomestay_id(request.getHomestay_id()).orElseThrow(() -> new AppException(ResultCode.HOMESTAY_NOT_EXISTED));
            homestay = toHomestayEntity(request);
            homestayRepository.save(homestay);
            return toHomestayResponse(homestay);
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


    public List<HomestayResponse> searchHomestay(FindHomestayRequest request) {
        try {
            List<Homestay> availableHomestays = homestayRepository.findAvailableHomestays(request.getCheckInDate(), request.getCheckOutDate());
            List<Homestay> filteredHomestays = availableHomestays.stream().filter(h -> request.getCity() == null || request.getCity().isEmpty() || h.getCity().equalsIgnoreCase(request.getCity()))
                    .filter(h -> h.getMaxGuests() >= request.getNumberOfGuest())
                    .collect(Collectors.toList());
            for(Homestay homestay : filteredHomestays) {
                Hibernate.initialize(homestay.getAmenities());
                Hibernate.initialize(homestay.getRules());
            }
            return toHomestayResponseList(filteredHomestays);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ResultCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public List<AmenityResponse> getAllAmenity() {
        List<Amenity> amenities = amenityRepository.findAll();
        return toAmenityResponseList(amenities);
    }
    private AmenityResponse toAmenityResponse (Amenity amenity) {
        return AmenityResponse.builder()
                .amenity_id(amenity.getAmenity_id())
                .name(amenity.getName())
                .description(amenity.getDescription())
                .icon(amenity.getIcon())
                .category(amenity.getCategory())
                .active(amenity.isActive()).build();
    }
    private List<AmenityResponse> toAmenityResponseList(List<Amenity> amenities) {
        return amenities.stream().map(this::toAmenityResponse).collect(Collectors.toList());
    }
    private HomestayResponse toHomestayResponse(Homestay homestay){
        return HomestayResponse.builder()
                .homestay_id(homestay.getHomestay_id())
                .name(homestay.getName())
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

    private Homestay toHomestayEntity(HomestayRequest request){
        List<Amenity> amenitiesList = amenityRepository.findAllById(request.getAmenities());
        List<Rule> rulesList = ruleRepository.findAllById(request.getRules());
        Set<Amenity> amenities = new HashSet<>(amenitiesList);
        Set<Rule> rules = new HashSet<>(rulesList);
        HomestayOwner homestayOwner = homestayOwnerRepository.getById(request.getOwner_id());
        return Homestay.builder()
                .name(request.getName())
                .homestay_owner(homestayOwner)
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
    private  List<HomestayResponse> toHomestayResponseList(List<Homestay> homestays) {
        return homestays.stream().map(this::toHomestayResponse).collect(Collectors.toList());
    }
}