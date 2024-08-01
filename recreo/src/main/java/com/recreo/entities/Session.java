package com.recreo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Session extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "credentials_id", nullable = false)
    private Credential credential;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "profiles_id", nullable = false)
    private Profile profile;

    @Column(nullable = false, unique = true)
    private String token;

    // TODO: fecha de cierre -> logout
}
