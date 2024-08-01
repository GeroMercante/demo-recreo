package com.recreo.managers;

import com.recreo.exceptions.RecreoApiException;
import com.recreo.entities.Profile;
import com.recreo.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileManager {

    private final ProfileRepository profileRepository;

    public ProfileManager(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile getProfile(String profileName) throws RecreoApiException {
        return profileRepository.findByName(profileName).orElseThrow(() -> new RecreoApiException("Rol no encontrado"));
    }
}
