package com.recreo.controllers.bo.user;

import com.recreo.dto.ChangeTemporaryPasswordDTO;
import com.recreo.dto.LoginRequestDTO;
import com.recreo.dto.LoginResponseDTO;
import com.recreo.exceptions.RecreoApiException;
import com.recreo.services.AuthService;
import com.recreo.services.RevokedTokenService;
import com.recreo.utils.AppConstant;
import com.recreo.utils.JwtUtils;
import com.recreo.utils.Validators;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppConstant.API_ENDPOINT_BO + "/auth")
public class AuthController {

    private final AuthService authService;
    private final RevokedTokenService tokenService;

    @Autowired
    public AuthController(final AuthService authService, RevokedTokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) throws RecreoApiException {
        Validators.validateEmail(loginRequestDTO.getEmail());
        return authService.login(loginRequestDTO);
    }

    @PutMapping("/change/password")
    public LoginResponseDTO changePassword(@RequestBody ChangeTemporaryPasswordDTO changeTemporaryPasswordDTO, HttpServletRequest request) throws RecreoApiException {
        final String jwtToken = JwtUtils.getAndValidateToken(request.getHeader("Authorization"));
        return authService.changePassword(changeTemporaryPasswordDTO, jwtToken);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) throws RecreoApiException {
        final String jwtToken = JwtUtils.getAndValidateToken(request.getHeader("Authorization"));
        tokenService.revokeToken(jwtToken);
    }

}
