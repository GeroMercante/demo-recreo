package com.recreo.managers;

import com.recreo.dto.CredentialDTO;
import com.recreo.exceptions.RecreoApiException;
import com.recreo.entities.Credential;
import com.recreo.entities.User;
import com.recreo.repositories.CredentialRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CredentialManager {

    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;

    public CredentialManager(CredentialRepository credentialRepository, PasswordEncoder passwordEncoder) {
        this.credentialRepository = credentialRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createCredential(User user, CredentialDTO credentialDTO) throws RecreoApiException {
        Credential credential = credentialRepository.findByUserIdAndTypeCredential(user.getId(), credentialDTO.getTypeCredential()).orElse(null);

        if (credential != null) {
            throw new RecreoApiException("Credential already exists");
        }

        credential = new Credential();
        credential.setTypeCredential(credentialDTO.getTypeCredential());
        credential.setUser(user);
        credential.setIsTemporary(credentialDTO.getIsTemporary());
        credential.setPassword(passwordEncoder.encode(credentialDTO.getPassword()));

        credentialRepository.save(credential);
    }

}
