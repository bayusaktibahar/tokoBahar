package com.sakti.toko.service;

import lombok.AllArgsConstructor;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public Session getSessionById(String id) {
        return sessionRepository.findById(id);
    }

    public void deleteSessionById(String id) {
        sessionRepository.deleteById(id);
    }
}
