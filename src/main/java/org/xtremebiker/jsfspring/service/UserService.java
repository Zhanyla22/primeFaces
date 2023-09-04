package org.xtremebiker.jsfspring.service;

import org.xtremebiker.jsfspring.dto.response.UserDto;
import org.xtremebiker.jsfspring.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity findByUserName(String userName);

    List<UserDto> getAllUsers();

}
