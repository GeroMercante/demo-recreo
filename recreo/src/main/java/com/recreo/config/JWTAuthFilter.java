package com.recreo.config;

import com.recreo.services.RevokedTokenService;
import com.recreo.services.impl.CredentialServiceImpl;
import com.recreo.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    protected static final Logger logger = LoggerFactory.getLogger(JWTAuthFilter.class);

    private final JwtUtils jwtUtils;
    private final CredentialServiceImpl credentialService;
    private final RevokedTokenService tokenService;

    public JWTAuthFilter(JwtUtils jwtUtils, CredentialServiceImpl credentialService, RevokedTokenService tokenService) {
        this.jwtUtils = jwtUtils;
        this.credentialService = credentialService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);

        // TODO: No ir a la base.
        if (tokenService.isTokenRevoked(jwtToken)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

      userEmail = jwtUtils.extractUsername(jwtToken);

       if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           UserDetails userDetails = credentialService.loadUserByUsername(userEmail);

           if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
               UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
               authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               logger.info("{}", userDetails.getAuthorities());
               SecurityContextHolder.getContext().setAuthentication(authToken);
           }
       }

        filterChain.doFilter(request, response);
    }
}
