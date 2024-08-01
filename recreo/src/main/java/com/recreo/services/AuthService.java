package com.recreo.services;

import com.recreo.dto.ChangeTemporaryPasswordDTO;
import com.recreo.dto.LoginRequestDTO;
import com.recreo.dto.LoginResponseDTO;
import com.recreo.exceptions.RecreoApiException;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO credentialDTO) throws RecreoApiException;
    LoginResponseDTO changePassword(@RequestBody ChangeTemporaryPasswordDTO changeTemporaryPasswordDTO, String token) throws RecreoApiException;
}
