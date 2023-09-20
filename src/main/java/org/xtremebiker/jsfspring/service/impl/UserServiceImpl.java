package org.xtremebiker.jsfspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.xtremebiker.jsfspring.dto.request.AddUserDto;
import org.xtremebiker.jsfspring.dto.request.UpdatePassRequest;
import org.xtremebiker.jsfspring.dto.response.AuthenticationResponse;
import org.xtremebiker.jsfspring.dto.response.UserDto;
import org.xtremebiker.jsfspring.entity.AttendRecord;
import org.xtremebiker.jsfspring.entity.Payment;
import org.xtremebiker.jsfspring.entity.UserEntity;
import org.xtremebiker.jsfspring.enums.Role;
import org.xtremebiker.jsfspring.enums.Status;
import org.xtremebiker.jsfspring.exceptions.BaseException;
import org.xtremebiker.jsfspring.mapper.UserMapper;
import org.xtremebiker.jsfspring.repo.AttendanceRepo;
import org.xtremebiker.jsfspring.repo.PaymentRepo;
import org.xtremebiker.jsfspring.repo.UserRepo;
import org.xtremebiker.jsfspring.security.jwt.JWTService;
import org.xtremebiker.jsfspring.service.UserService;
import org.xtremebiker.jsfspring.util.AESUtil;
import org.xtremebiker.jsfspring.util.EmailUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final AttendanceRepo attendanceRepo;
    private final PaymentRepo paymentRepo;
    private final EmailUtil emailUtil;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserDetailServiceImpl userDetailService;
    private final AESUtil aesUtil;

    @Override
    public UserEntity findByUserName(String username) {
        return userRepo.findByUserName(username).orElseThrow(
                () -> new BaseException("Не найден", HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos = UserMapper.entityListToUserDtoList(userRepo.findAll());
        return userDtos;
    }

    public String codeGenerate() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString().replaceAll("-", "");

        return randomUUIDString.substring(0, 6);
    }

    @Override
    public String addNewUser(AddUserDto addUserDto) {
        if (!userRepo.existsByUserName(addUserDto.getUserName()) && !addUserDto.getUserName().isEmpty()) { //TODO: fix
            String pass = codeGenerate();
            emailUtil.send(addUserDto.getUserName(), "ваш пароль", pass);
            UserEntity userEntity = new UserEntity();
            userEntity.setUserName(addUserDto.getUserName());
            userEntity.setFirstName(addUserDto.getFirstName());
            userEntity.setLastName(addUserDto.getLastName());
            userEntity.setRole(Role.ROLE_USER);
            userEntity.setPassword(passwordEncoder.encode(pass));
            userEntity.setUuid(UUID.randomUUID());
            userRepo.saveAndFlush(userEntity);

            attendanceRepo.save(AttendRecord.builder()
                    .delayInMin(0)
                    .status(Status.ACTIVE)
                    .money(0L)
                    .streak(0)
                    .userEntity(userRepo.findByUserName(addUserDto.getUserName()).orElseThrow(
                            () -> new BaseException("не найден", HttpStatus.NOT_FOUND)
                    ))
                    .attendDate(LocalDate.now())
                    .build()
            );

            paymentRepo.save(Payment.builder()
                    .sum(0L)
                    .userEntity(userRepo.findByUserName(addUserDto.getUserName()).orElseThrow(
                            () -> new BaseException("не найден", HttpStatus.NOT_FOUND)
                    ))
                    .build());
        }
        return null;
    }

    @Override
    public AuthenticationResponse auth(String userName, String pass) {
        try {
            String decryptedPass = aesUtil.decrypt(pass);
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userName,
                            decryptedPass
                    )
            );
            return jwtService.generateToken((UserEntity) authenticate.getPrincipal());
        } catch (Exception e) {
            throw new BaseException("логин или пароль неправильно", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public AuthenticationResponse refreshToken() {
        UserEntity userEntity1 = userDetailService.getCurrentUser();
        try {
            return jwtService.generateToken(userEntity1);
        } catch (Exception e) {
            throw new BaseException("время истекло, авторизируйтесь обратно", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public String updatePass(UpdatePassRequest updatePassRequest) {
        UserEntity userEntity1 = userDetailService.getCurrentUser();
        if (passwordEncoder.matches(updatePassRequest.getOldPassword(), userEntity1.getPassword())) {
            userEntity1.setPassword(passwordEncoder.encode(updatePassRequest.getNewPassword()));
            userRepo.save(userEntity1);
        }
        return null;
    }
}
