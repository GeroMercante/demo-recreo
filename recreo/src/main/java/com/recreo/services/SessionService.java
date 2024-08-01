package com.recreo.services;

import com.recreo.entities.Credential;
import com.recreo.entities.Profile;
import com.recreo.entities.User;

public interface SessionService {
    void saveSession(Credential credential, User user, Profile profile, String token);
}
