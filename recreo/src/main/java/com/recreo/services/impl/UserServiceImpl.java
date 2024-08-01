package com.recreo.services.impl;

import com.recreo.dto.CredentialDTO;
import com.recreo.dto.ProfileDTO;
import com.recreo.dto.UserDTO;
import com.recreo.entities.Profile;
import com.recreo.enums.CredentialTypeEnum;
import com.recreo.exceptions.RecreoApiException;
import com.recreo.entities.User;
import com.recreo.http.response.PagedResponse;
import com.recreo.managers.UserManager;
import com.recreo.services.UserService;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.SecureRandom;

@Service
public class UserServiceImpl extends BaseServices implements UserService {

    private final UserManager userManager;
    private final EmailServiceImpl emailServiceImpl;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserManager userManager, EmailServiceImpl emailServiceImpl) {
        this.modelMapper = modelMapper;
        this.userManager = userManager;
        this.emailServiceImpl = emailServiceImpl;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<UserDTO> searchUsers(int page, int size, Long profileId, String searchText) {
        Page<User> userPage = userManager.searchUsers(page, size, profileId, searchText);
        return PagedResponse.from(this.convertToDto(userPage.getContent(), UserDTO.class), userPage);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long userId) throws RecreoApiException {
        User user = userManager.getUserById(userId);
        return this.convertToDto(user, UserDTO.class);
    }

    @Override
    @Transactional()
    public UserDTO createBackofficeUser(UserDTO userDTO) throws RecreoApiException, MessagingException {
        CredentialDTO credentialDTO = new CredentialDTO();
        credentialDTO.setTypeCredential(CredentialTypeEnum.BACKOFFICE);
        credentialDTO.setIsTemporary(true);
        credentialDTO.setIsVerified(false);
        credentialDTO.setPassword(generateTemporaryPassword());

        User user = userManager.createUser(userDTO, credentialDTO);

        emailServiceImpl.sendTemporaryPassword(user.getEmail(), credentialDTO.getPassword());

        return this.convertToDto(user, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) throws RecreoApiException {
        User user = userManager.updateUser(userId, userDTO);

        return this.convertToDto(user, UserDTO.class);
    }

    @Override
    public ProfileDTO getProfile(Long userId) throws RecreoApiException {
        Profile profile = userManager.getProfile(userId);

        return this.convertToDto(profile, ProfileDTO.class);
    }

    private String generateTemporaryPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }
}
