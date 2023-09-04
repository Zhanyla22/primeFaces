package org.xtremebiker.jsfspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xtremebiker.jsfspring.dto.response.UserDto;
import org.xtremebiker.jsfspring.service.UserService;

import java.util.List;


@Component
@Scope("view")
public class UserController {

    @Autowired
    private UserService userService;

    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

}
