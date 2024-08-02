package com.recreo.controllers.bo.user;

import com.recreo.dto.ProfileDTO;
import com.recreo.exceptions.RecreoApiException;
import com.recreo.services.impl.ProfileServiceImpl;
import com.recreo.utils.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstant.API_ENDPOINT_BO + "/profiles")
public class ProfileController {

    private final ProfileServiceImpl profileServiceImpl;

    @Autowired
    public ProfileController(ProfileServiceImpl profileServiceImpl) {
        this.profileServiceImpl = profileServiceImpl;
    }

    @GetMapping
    public List<ProfileDTO> getProfiles() {
        return profileServiceImpl.getProfiles();
    }

    @PostMapping
    public ProfileDTO createProfile(@RequestBody ProfileDTO profileDTO) throws RecreoApiException {
        return profileServiceImpl.createProfile(profileDTO);
    }

    @PutMapping
    public ProfileDTO updateProfile(@RequestBody ProfileDTO profileDTO) throws RecreoApiException {
        return profileServiceImpl.updateProfile(profileDTO);
    }
}
