package com.recreo.services.impl;

import com.recreo.entities.Credential;
import com.recreo.entities.Profile;
import com.recreo.entities.Session;
import com.recreo.entities.User;
import com.recreo.repositories.SessionRepository;
import com.recreo.services.SessionService;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void saveSession(Credential credential, User user, Profile profile, String token) {
        Session newSession = new Session();
        newSession.setCredential(credential);
        newSession.setUser(user);
        newSession.setProfile(profile);
        newSession.setToken(token);
        sessionRepository.save(newSession);
    }
}
