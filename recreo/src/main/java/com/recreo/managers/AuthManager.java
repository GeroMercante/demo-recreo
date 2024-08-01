package com.recreo.managers;

import com.recreo.dto.ChangeTemporaryPasswordDTO;
import com.recreo.dto.LoginRequestDTO;
import com.recreo.entities.Credential;
import com.recreo.exceptions.RecreoApiException;
import com.recreo.repositories.CredentialRepository;
import com.recreo.services.impl.SessionServiceImpl;
import com.recreo.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthManager {

    private final CredentialRepository credentialRepository;
    private final AuthenticationManager authenticationManager;
    private final SessionServiceImpl sessionServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthManager(CredentialRepository credentialRepository, org.springframework.security.authentication.AuthenticationManager authenticationManager, SessionServiceImpl sessionServiceImpl, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.credentialRepository = credentialRepository;
        this.authenticationManager = authenticationManager;
        this.sessionServiceImpl = sessionServiceImpl;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public Credential authenticateWithCredential(LoginRequestDTO loginRequestDTO) throws RecreoApiException {
        Credential credential = credentialRepository.findByUser_Email(loginRequestDTO.getEmail())
                .orElseThrow(() -> new RecreoApiException("Credenciales no encontradas para el usuario"));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

        return credential;
    }

    public Credential changePassword(ChangeTemporaryPasswordDTO changeTemporaryPasswordDTO, String token) throws RecreoApiException {
        Credential credential = credentialRepository.findByUser_Email(changeTemporaryPasswordDTO.getEmail()).orElseThrow(() -> new RecreoApiException("Credenciales no encontradas para el usuario"));

        Long userId = jwtUtils.getUserIdFromToken(token);

        if (!credential.getUser().getId().equals(userId)) { throw new RecreoApiException("Usuario no encontrado"); }

        if (!passwordEncoder.matches(changeTemporaryPasswordDTO.getPassword(), credential.getPassword())) {
            throw new RecreoApiException("Contraseña temporal incorrecta");
        }

        credential.setIsTemporary(false);
        credential.setPassword(passwordEncoder.encode(changeTemporaryPasswordDTO.getNewPassword()));
        return credentialRepository.save(credential);
    }

    public void saveSession(Credential credential, String jwt) {
        sessionServiceImpl.saveSession(credential, credential.getUser(), credential.getUser().getProfile(), jwt);
    }
}
