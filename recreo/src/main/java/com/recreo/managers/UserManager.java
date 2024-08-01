package com.recreo.managers;

import com.recreo.dto.CredentialDTO;
import com.recreo.dto.UserDTO;
import com.recreo.exceptions.RecreoApiException;
import com.recreo.entities.Profile;
import com.recreo.entities.User;
import com.recreo.repositories.UserRepository;
import com.recreo.utils.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserManager {

    private final UserRepository userRepository;
    private final CredentialManager credentialManager;
    private final ProfileManager profileManager;

    @Autowired
    public UserManager(UserRepository userRepository, CredentialManager credentialManager, ProfileManager profileManager) {
        this.userRepository = userRepository;
        this.credentialManager = credentialManager;
        this.profileManager = profileManager;
    }

    public User createUser(UserDTO userDTO, CredentialDTO credentialDTO) throws RecreoApiException {
        Validators.validateEmail(userDTO.getEmail());

        User user = userRepository.findByEmail(userDTO.getEmail()).orElse(null);

        if (user != null) {
            throw new RecreoApiException("El usuario ya existe");
        }

        Profile profile = profileManager.getProfile(userDTO.getProfile());
        user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setProfile(profile);
        user.setEmail(userDTO.getEmail());
        user.setActive(userDTO.getActive());
        user.setDocumentValue(user.getDocumentValue());
        userRepository.save(user);

        credentialManager.createCredential(user, credentialDTO);

        return user;
    }

    public Page<User> searchUsers(int page, int size, Long profileId, String searchText) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.search(searchText, profileId, pageable);
    }

    public User getUserById(Long userId) throws RecreoApiException {
        return userRepository.findById(userId).orElseThrow(() -> new RecreoApiException("No se encontró el usuario especificado"));
    }

    public User updateUser(Long userId, UserDTO userDTO) throws RecreoApiException {
        User currentUser = this.getUserById(userId);
        currentUser.setFirstName(userDTO.getFirstName());
        currentUser.setLastName(userDTO.getLastName());
        // currentUser.setActive(userDTO.getActive());
        currentUser.setDocumentType(userDTO.getDocumentType());
        currentUser.setDocumentValue(userDTO.getDocumentValue());
        currentUser.setUserArea(userDTO.getUserArea());
        currentUser.setPosition(userDTO.getPosition());
        currentUser.setProfile(profileManager.getProfile(userDTO.getProfile()));
        return userRepository.save(currentUser);
    }

    public Profile getProfile(Long userId) throws RecreoApiException {
        User currentUser = this.getUserById(userId);

        return currentUser.getProfile();
    }
}
