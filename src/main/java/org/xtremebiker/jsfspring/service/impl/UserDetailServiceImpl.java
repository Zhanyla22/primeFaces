package org.xtremebiker.jsfspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.xtremebiker.jsfspring.entity.UserEntity;
import org.xtremebiker.jsfspring.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.xtremebiker.jsfspring.exceptions.BaseException;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUserName(username).orElseThrow(
                () -> new BaseException("не найден", HttpStatus.NOT_FOUND)
        );
    }

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) loadUserByUsername(authentication.getName());
    }
}
