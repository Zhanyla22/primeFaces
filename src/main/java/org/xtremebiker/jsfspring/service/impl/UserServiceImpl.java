package org.xtremebiker.jsfspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.xtremebiker.jsfspring.dto.response.UserDto;
import org.xtremebiker.jsfspring.entity.UserEntity;
import org.xtremebiker.jsfspring.exceptions.BaseException;
import org.xtremebiker.jsfspring.mapper.UserMapper;
import org.xtremebiker.jsfspring.repo.UserRepo;
import org.xtremebiker.jsfspring.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;


    @Override
    public UserEntity findByUserName(String username) {
        return userRepo.findByUserName(username).orElseThrow(
                ()-> new BaseException("Не найден", HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos = UserMapper.entityListToUserDtoList(userRepo.findAll());
        return userDtos;
    }
}
