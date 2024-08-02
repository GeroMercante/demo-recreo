package com.recreo.services;

import com.recreo.dto.ProfileDTO;
import com.recreo.exceptions.RecreoApiException;
import java.util.List;

public interface ProfileService {
    List<ProfileDTO> getProfiles();
    ProfileDTO createProfile(ProfileDTO profileCreateDTO) throws RecreoApiException;
    ProfileDTO updateProfile( ProfileDTO profileDTO) throws RecreoApiException;
}
