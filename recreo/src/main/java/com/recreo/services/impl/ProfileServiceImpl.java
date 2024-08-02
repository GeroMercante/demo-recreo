package com.recreo.services.impl;

import com.recreo.dto.ProfileDTO;
import com.recreo.entities.Profile;
import com.recreo.exceptions.RecreoApiException;
import com.recreo.managers.ProfileManager;
import com.recreo.repositories.ProfilePermissionRepository;
import com.recreo.services.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfileServiceImpl extends BaseServices implements ProfileService {

    private final ProfileManager profileManager;
    private final ProfilePermissionRepository profilePermissionRepository;

    public ProfileServiceImpl(ModelMapper modelMapper, ProfileManager profileManager, ProfilePermissionRepository profilePermissionRepository) {
        this.profileManager = profileManager;
        this.modelMapper = modelMapper;
        this.profilePermissionRepository = profilePermissionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProfileDTO> getProfiles() {
        List<Profile> profiles = profileManager.getProfiles();

        return this.convertToDto(profiles, ProfileDTO.class);
    }

    @Override
    @Transactional
    public ProfileDTO createProfile(ProfileDTO profileCreateDTO) throws RecreoApiException {
        Profile profile = profileManager.createProfile(profileCreateDTO);
        return this.convertToDto(profile, ProfileDTO.class);
    }

    @Override
    @Transactional
    public ProfileDTO updateProfile(ProfileDTO profileUpdateDTO) throws RecreoApiException {
        Profile profile = profileManager.updateProfile(profileUpdateDTO);
        return this.convertToDto(profile, ProfileDTO.class);
    }
}
