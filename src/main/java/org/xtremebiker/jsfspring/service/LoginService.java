package org.xtremebiker.jsfspring.service;

import org.xtremebiker.jsfspring.dto.request.LoginRequest;

public interface LoginService {

    String login(LoginRequest loginRequest) throws Exception;
}
