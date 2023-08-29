package org.xtremebiker.jsfspring.service;

import org.xtremebiker.jsfspring.dto.response.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();
}
