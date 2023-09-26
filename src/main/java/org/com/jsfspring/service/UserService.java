package org.com.jsfspring.service;

import org.com.jsfspring.dto.request.AddUserRequest;
import org.com.jsfspring.dto.request.AuthRequest;
import org.com.jsfspring.dto.request.UpdatePassRequest;
import org.com.jsfspring.dto.response.AddUserResponse;
import org.com.jsfspring.dto.response.AuthenticationResponse;
import org.com.jsfspring.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();

    AddUserResponse addNewUser(AddUserRequest addUserRequest);

    AuthenticationResponse auth(AuthRequest authRequest);

    AuthenticationResponse refreshToken(String token);

    String updatePass(UpdatePassRequest updatePassRequest, String token); //TODO: спросить
}
