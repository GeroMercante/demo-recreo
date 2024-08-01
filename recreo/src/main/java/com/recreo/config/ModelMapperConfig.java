package com.recreo.config;

import com.recreo.dto.LoginResponseDTO;
import com.recreo.dto.ProfileDTO;
import com.recreo.dto.SessionDTO;
import com.recreo.dto.UserDTO;
import com.recreo.entities.Credential;
import com.recreo.entities.Profile;
import com.recreo.entities.Session;
import com.recreo.entities.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<User, UserDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setEmail(source.getEmail());
                map().setFirstName(source.getFirstName());
                map().setLastName(source.getLastName());
                map().setDocumentValue(source.getDocumentValue());
                map().setActive(source.getActive());
                map().setProfile(source.getProfile().getName());
                map().setPosition(source.getPosition());
                map().setUserArea(source.getUserArea());
            }
        });

        modelMapper.addMappings(new PropertyMap<Profile, ProfileDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setName(source.getName());
                map().setPermissions(source.getPermissionCodes());
            }
        });

        modelMapper.addMappings(new PropertyMap<Session, SessionDTO>() {
            @Override
            protected void configure() {
                map().setToken(source.getToken());
            }
        });

        modelMapper.addMappings(new PropertyMap<Credential, LoginResponseDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getUser().getId());
                map().setFirstName(source.getUser().getFirstName());
                map().setLastName(source.getUser().getLastName());
                map().setProfile(source.getUser().getProfile().getName());
                map().setEmail(source.getUser().getEmail());
                map().setBirthDate(source.getUser().getBirthDate());
                map().setDocumentValue(source.getUser().getDocumentValue());
                map().setDocumentType(source.getUser().getDocumentType());
                map().setUserArea(source.getUser().getUserArea());
                map().setJobPosition(source.getUser().getPosition());
                map().setIsCredentialTemporary(source.getIsTemporary());
            }
        });

        return modelMapper;
    }

}
