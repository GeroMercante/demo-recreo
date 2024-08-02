package com.recreo.managers;

import com.recreo.dto.ProfileDTO;
import com.recreo.entities.ProfilePermission;
import com.recreo.enums.PermissionsEnum;
import com.recreo.exceptions.RecreoApiException;
import com.recreo.entities.Profile;
import com.recreo.repositories.ProfilePermissionRepository;
import com.recreo.repositories.ProfileRepository;
import com.recreo.utils.MessageUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileManager {

    private final ProfileRepository profileRepository;
    private final ProfilePermissionRepository profilePermissionRepository;
    private final MessageUtil messageUtil;

    public ProfileManager(ProfileRepository profileRepository, ProfilePermissionRepository profilePermissionRepository, MessageUtil messageUtil) {
        this.profileRepository = profileRepository;
        this.profilePermissionRepository = profilePermissionRepository;
        this.messageUtil = messageUtil;
    }

    public Profile getProfile(String profileName) throws RecreoApiException {
        return profileRepository.findByName(profileName).orElseThrow(() -> new RecreoApiException(messageUtil.getMessage("rol.not.found")));
    }

    public List<Profile> getProfiles() {
        return profileRepository.findAll();
    }

    public Profile createProfile(ProfileDTO profileDTO) throws RecreoApiException {
        Profile profile = new Profile();
        profile.setName(profileDTO.getName());

        List<ProfilePermission> profilePermissions = createProfilePermissions(profileDTO.getPermissions(), profile);
        profile.setProfilePermissions(profilePermissions);

        Profile savedProfile = profileRepository.save(profile);
        profilePermissionRepository.saveAll(profilePermissions);

        return savedProfile;
    }

    public Profile updateProfile(ProfileDTO profileDTO) throws RecreoApiException {
        Profile profile = getProfile(profileDTO.getName());

        profilePermissionRepository.deleteAll(profile.getProfilePermissions());
        profile.getProfilePermissions().clear();

        Profile savedProfile = profileRepository.save(profile);

        List<ProfilePermission> profilePermissions = createProfilePermissions(profileDTO.getPermissions(), savedProfile);

        profilePermissionRepository.saveAll(profilePermissions);

        savedProfile.setProfilePermissions(profilePermissions);

        return savedProfile;
    }


    private List<ProfilePermission> createProfilePermissions(List<String> permissionCodes, Profile profile) throws RecreoApiException {
        List<ProfilePermission> profilePermissions = new ArrayList<>();
        for (String permissionCode : permissionCodes) {
            if (isValidPermission(permissionCode)) {
                ProfilePermission profilePermission = new ProfilePermission();
                profilePermission.setPermissionCode(PermissionsEnum.valueOf(permissionCode));
                profilePermission.setProfile(profile);
                profilePermissions.add(profilePermission);
            } else {
                throw new RecreoApiException(messageUtil.getMessage("permission.not.found", permissionCode));
            }
        }
        return profilePermissions;
    }

    private boolean isValidPermission(String permissionCode) {
        for (PermissionsEnum permission : PermissionsEnum.values()) {
            if (permission.name().equals(permissionCode)) {
                return true;
            }
        }
        return false;
    }

}
