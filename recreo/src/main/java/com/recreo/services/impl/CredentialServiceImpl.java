package com.recreo.services.impl;

import com.recreo.dto.CredentialDTO;
import com.recreo.entities.Credential;
import com.recreo.entities.User;
import com.recreo.repositories.CredentialRepository;
import com.recreo.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CredentialServiceImpl implements UserDetailsService {

    private final CredentialRepository credentialRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public CredentialServiceImpl(CredentialRepository credentialRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.credentialRepository = credentialRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + email));

        return credentialRepository.findByUser_Email(user.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Credenciales no encontradas para el usuario con el email: " + email));
    }

    public Credential createCredential(CredentialDTO credentialDTO) {
        Credential credential = new Credential();
        modelMapper.map(credentialDTO, credential);

        return credentialRepository.save(credential);
    }

}
