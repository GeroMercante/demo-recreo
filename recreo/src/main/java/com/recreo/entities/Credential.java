package com.recreo.entities;

import com.recreo.enums.CredentialTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "credentials")
public class Credential extends BaseEntity implements UserDetails {

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @Column(nullable = true)
    private String password;

    @Enumerated(EnumType.STRING)
    private CredentialTypeEnum typeCredential;

    private String externalReference;

    @Column(name = "is_temporary")
    private Boolean isTemporary = Boolean.TRUE;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @PostLoad
    private void loadAuthorities() {
        if (Boolean.TRUE.equals(isTemporary)) {
            this.authorities = new ArrayList<>();
        } else if (user != null && user.getProfile() != null) {
            List<SimpleGrantedAuthority> list = new ArrayList<>();
            for (String s : user.getProfile().getPermissionCodes()) {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(s);
                list.add(simpleGrantedAuthority);
            }
            this.authorities = list;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}