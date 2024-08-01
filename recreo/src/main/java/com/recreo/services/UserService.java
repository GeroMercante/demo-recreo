package com.recreo.services;

import com.recreo.dto.ProfileDTO;
import com.recreo.dto.UserDTO;
import com.recreo.exceptions.RecreoApiException;
import com.recreo.http.response.PagedResponse;
import jakarta.mail.MessagingException;

public interface UserService {
    PagedResponse<UserDTO> searchUsers(int page, int size, Long profileId, String searchText);
    UserDTO getUserById(Long userId) throws RecreoApiException;
    UserDTO createBackofficeUser(UserDTO userDTO) throws RecreoApiException, MessagingException;
    UserDTO updateUser(Long userId, UserDTO userDTO) throws RecreoApiException;
    ProfileDTO getProfile(Long userId) throws RecreoApiException;
}
