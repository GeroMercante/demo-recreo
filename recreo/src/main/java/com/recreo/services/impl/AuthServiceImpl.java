package com.recreo.services.impl;

import com.recreo.dto.ChangeTemporaryPasswordDTO;
import com.recreo.dto.LoginRequestDTO;
import com.recreo.dto.LoginResponseDTO;
import com.recreo.entities.Credential;
import com.recreo.exceptions.RecreoApiException;
import com.recreo.managers.AuthManager;
import com.recreo.services.AuthService;
import com.recreo.utils.AppConstant;
import com.recreo.utils.JwtUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@Service
public class AuthServiceImpl extends BaseServices implements AuthService {

    private final JwtUtils jwtUtils;
    private final AuthManager authManager;

    @Autowired
    public AuthServiceImpl(
            JwtUtils jwtUtils,
            AuthManager authManager,
            ModelMapper modelMapper
    )
    {
        this.jwtUtils = jwtUtils;
        this.authManager = authManager;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws RecreoApiException {

        Credential credential = authManager.authenticateWithCredential(loginRequestDTO);

        String jwt = jwtUtils.generateToken(credential);
        String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), credential);

        LoginResponseDTO loginResponseDTO  = this.convertToDto(credential, LoginResponseDTO.class);
        loginResponseDTO.setToken(jwt);
        loginResponseDTO.setRefreshToken(refreshToken);
        loginResponseDTO.setExpirationTime(AppConstant.TOKEN_EXPIRATION_TIME);

        authManager.saveSession(credential, jwt);

        return loginResponseDTO;
    }

    @Override
    @Transactional
    public LoginResponseDTO changePassword(@RequestBody ChangeTemporaryPasswordDTO changeTemporaryPasswordDTO, String token) throws RecreoApiException {
        Credential credential = authManager.changePassword(changeTemporaryPasswordDTO, token);

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail(credential.getUser().getEmail());
        loginRequestDTO.setPassword(changeTemporaryPasswordDTO.getNewPassword());
        return login(loginRequestDTO);
    }
}
