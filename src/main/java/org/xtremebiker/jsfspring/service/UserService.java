package org.xtremebiker.jsfspring.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.xtremebiker.jsfspring.dto.response.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDetails auth(String userName, String password);
}
