package org.com.jsfspring.service;

import org.com.jsfspring.dto.request.AddUserDto;
import org.com.jsfspring.dto.request.AuthDto;
import org.com.jsfspring.dto.request.UpdatePassRequest;
import org.com.jsfspring.dto.response.AddUserResponse;
import org.com.jsfspring.dto.response.AuthenticationResponse;
import org.com.jsfspring.dto.response.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    AddUserResponse addNewUser(AddUserDto addUserDto);

    AuthenticationResponse auth(AuthDto authDto);

    AuthenticationResponse refreshToken(String token);

    String updatePass(UpdatePassRequest updatePassRequest, String token); //TODO: спросить
}
