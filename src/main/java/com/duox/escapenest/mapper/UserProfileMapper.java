package com.duox.escapenest.mapper;

import com.duox.escapenest.dto.request.ProfileUpdateRequest;
import com.duox.escapenest.dto.response.valueObject.UserProfileResponse;
import com.duox.escapenest.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfileResponse toUserProfileResponse(UserProfile userProfile);
    void updateProfile(@MappingTarget UserProfile userProfile, ProfileUpdateRequest request);
}
