package com.duox.escapenest.service;

import com.duox.escapenest.constant.ResultCode;
import com.duox.escapenest.dto.request.ProfileUpdateRequest;
import com.duox.escapenest.dto.response.valueObject.UserProfileResponse;
import com.duox.escapenest.entity.UserProfile;
import com.duox.escapenest.exception.AppException;
import com.duox.escapenest.mapper.CustomerMapper;
import com.duox.escapenest.mapper.UserProfileMapper;
import com.duox.escapenest.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;

    public UserProfileResponse updateProfile(ProfileUpdateRequest request){
        UserProfile userProfile = userProfileRepository.findByProfile_id(request.getProfile_id())
                .orElseThrow(() -> new AppException(ResultCode.ACCOUNT_NOT_EXISTED));
        userProfileMapper.updateProfile(userProfile,request);
        userProfileRepository.save(userProfile);
        log.info("Profile {} updated successfully", userProfile.getProfile_id());
        return userProfileMapper.toUserProfileResponse(userProfile);
    }
}
