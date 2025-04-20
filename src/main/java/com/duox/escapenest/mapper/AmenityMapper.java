package com.duox.escapenest.mapper;

import com.duox.escapenest.dto.response.AmenityResponse;
import com.duox.escapenest.entity.Amenity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AmenityMapper {
    AmenityResponse toResponse(Amenity amenity);
}
