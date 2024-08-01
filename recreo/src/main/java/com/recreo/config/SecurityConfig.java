package com.recreo.config;

import com.recreo.services.impl.CredentialServiceImpl;
import com.recreo.enums.PermissionsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CredentialServiceImpl credentialService;

    private final JWTAuthFilter jwtAuthFilter;

    @Autowired
    public SecurityConfig(final CredentialServiceImpl credentialService, final JWTAuthFilter jwtAuthFilter) {
        this.credentialService = credentialService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean(name = "securityFilterChain")
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/api/v1/bo/auth/**").permitAll()
                        .requestMatchers("/api/v1/app/auth/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/bo/auth/change/password").authenticated()
                        .requestMatchers("/api/v1/bo/users/search").hasAnyAuthority(PermissionsEnum.USERS_READ.name(), PermissionsEnum.USERS_READ_WRITE.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/bo/users/**").hasAnyAuthority(PermissionsEnum.USERS_READ_WRITE.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/bo/users/**").hasAnyAuthority(PermissionsEnum.USERS_READ_WRITE.name())
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean(name = "authenticationProvider")
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(credentialService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "authenticationManager")
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
