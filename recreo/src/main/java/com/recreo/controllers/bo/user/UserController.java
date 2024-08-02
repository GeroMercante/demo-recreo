package com.recreo.controllers.bo.user;

import com.recreo.dto.ProfileDTO;
import com.recreo.dto.UserDTO;
import com.recreo.exceptions.RecreoApiException;
import com.recreo.http.response.PagedResponse;
import com.recreo.services.UserService;
import com.recreo.utils.AppConstant;
import com.recreo.utils.AppUtils;
import com.recreo.utils.JwtUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstant.API_ENDPOINT_BO + "/users")
public class UserController {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AppUtils appUtils;

    @Autowired
    public UserController(UserService userService, JwtUtils jwtUtils, AppUtils appUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.appUtils = appUtils;
    }

    @GetMapping("/search")
    public PagedResponse<UserDTO> searchUsers(
            @RequestParam(name = "page", required = false, defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size,
            @RequestParam(name = "searchText", required = false) String searchText,
            @RequestParam(name = "profileId", required = false) Long profileId
    ) throws RecreoApiException
    {
        appUtils.validatePageNumberAndSize(page, size);
        return this.userService.searchUsers(page, size, profileId, searchText);
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable Long userId) throws RecreoApiException {
        return this.userService.getUserById(userId);
    }

    @PostMapping("/create")
    public UserDTO createBackofficeUser(@RequestBody UserDTO userDTO) throws RecreoApiException, MessagingException {
        return this.userService.createBackofficeUser(userDTO);
    }

    @PutMapping("/update/{userId}")
    public UserDTO updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) throws RecreoApiException {
        return this.userService.updateUser(userId, userDTO);
    }

    @GetMapping("/permissions")
    public ProfileDTO getProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws RecreoApiException {
        String token = authHeader.substring(7);
        Long userId = jwtUtils.getUserIdFromToken(token);

        return this.userService.getProfile(userId);
    }
}
