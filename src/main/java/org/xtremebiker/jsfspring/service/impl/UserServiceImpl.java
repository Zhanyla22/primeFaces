package org.xtremebiker.jsfspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.xtremebiker.jsfspring.dto.request.LoginRequest;
import org.xtremebiker.jsfspring.dto.response.UserDto;
import org.xtremebiker.jsfspring.entity.UserEntity;
import org.xtremebiker.jsfspring.exceptions.BaseException;
import org.xtremebiker.jsfspring.mapper.UserMapper;
import org.xtremebiker.jsfspring.repo.UserRepo;
import org.xtremebiker.jsfspring.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;


    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos = UserMapper.entityListToUserDtoList(userRepo.findAll());
        return userDtos;
    }

    @Override
    public boolean auth(String userName, String password) {
        UserEntity userEntity = userRepo.findByUserName(userName).orElseThrow(
                () -> new BaseException("Не найден", HttpStatus.NOT_FOUND));
        if (passwordEncoder.matches(userEntity.getPassword(), password)) {
            return true;
        }
        else return false;

    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByUserName(userName).orElseThrow(
                () -> new BaseException("Не найден", HttpStatus.NOT_FOUND));
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getAuthorities()
        );
    }

    public UserEntity getCurrentUserEntity() {
        return userRepo.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new BaseException("User not found", HttpStatus.NOT_FOUND));
    }
}
