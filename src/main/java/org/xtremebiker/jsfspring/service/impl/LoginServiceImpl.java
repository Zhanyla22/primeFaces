package org.xtremebiker.jsfspring.service.impl;//package data.service.impl;
//
//import data.dto.request.LoginRequest;
//import data.entity.UserEntity;
//import data.repo.UserRepo;
//import data.service.LoginService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.faces.application.FacesMessage;
//import javax.faces.context.FacesContext;
//
//@RequiredArgsConstructor
//@Service
//public class LoginServiceImpl implements LoginService {
//
//    private final UserRepo userRepo;
//
//    @Override
//    public String login(LoginRequest loginRequest) throws Exception {
//        UserEntity userEntity = userRepo.findByUserName(loginRequest.getUserName())
//                .orElseThrow(() -> new Exception("user not founs"));
//
//        if (userEntity != null && passwordMatches(userEntity.getPassword(), loginRequest.getPassword())) {
//            return "main.xhtml";
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("логин или пароль направильно, попытайтесь еще раз!"));
//            return null;
//        }
//    }
//
//    private boolean passwordMatches(String encodedPassword, String rawPassword) {
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        return encoder.matches(rawPassword, encodedPassword);
//    }
//}
