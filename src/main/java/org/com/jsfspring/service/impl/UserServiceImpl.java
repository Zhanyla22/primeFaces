package org.com.jsfspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.com.jsfspring.dto.request.AddUserDto;
import org.com.jsfspring.dto.request.AuthDto;
import org.com.jsfspring.dto.request.UpdatePassRequest;
import org.com.jsfspring.dto.response.AddUserResponse;
import org.com.jsfspring.dto.response.AuthenticationResponse;
import org.com.jsfspring.dto.response.UserDto;
import org.com.jsfspring.entity.AttendRecord;
import org.com.jsfspring.entity.Payment;
import org.com.jsfspring.entity.UserEntity;
import org.com.jsfspring.enums.Role;
import org.com.jsfspring.enums.Status;
import org.com.jsfspring.exceptions.BaseException;
import org.com.jsfspring.mapper.UserMapper;
import org.com.jsfspring.repo.AttendanceRepo;
import org.com.jsfspring.repo.PaymentRepo;
import org.com.jsfspring.repo.UserRepo;
import org.com.jsfspring.security.jwt.JWTService;
import org.com.jsfspring.service.UserService;
import org.com.jsfspring.util.AESUtil;
import org.com.jsfspring.util.EmailUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public AddUserResponse addNewUser(AddUserDto addUserDto) {
        if (!userRepo.existsByUserName(addUserDto.getUserName()) && !addUserDto.getUserName().isEmpty()) {
            String pass = codeGenerate();
            if (emailUtil.isValidEmailAddress(addUserDto.getUserName())) {
                emailUtil.send(addUserDto.getUserName(), "ваш пароль", pass);
            } else throw new BaseException("введите электронную почту!!!", HttpStatus.NOT_ACCEPTABLE);

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
                            () -> new BaseException("пользователь с username " + addUserDto.getUserName() + " не найден", HttpStatus.NOT_FOUND)
                    ))
                    .attendDate(LocalDate.now())
                    .build()
            );

            paymentRepo.save(Payment.builder()
                    .sum(0L)
                    .userEntity(userRepo.findByUserName(addUserDto.getUserName()).orElseThrow(
                            () -> new BaseException("пользователь с username " + addUserDto.getUserName() + " не найден", HttpStatus.NOT_FOUND)
                    ))
                    .build());

            return UserMapper.entityToUserAddDto(userEntity);
        } else
            throw new BaseException("проверьте поле userName, он не должен быть пустым, если он не пустой, то такой пользователь уже существует", HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public AuthenticationResponse auth(AuthDto authDto) {
        String decryptedPass = aesUtil.decrypt(authDto.getPassword());
        if (decryptedPass == null) {
            throw new BaseException("пароль дешифрован неправильно", HttpStatus.NOT_ACCEPTABLE);
        } else {
            try {
                Authentication authenticate = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authDto.getUserName(),
                                decryptedPass
                        )
                );
                return jwtService.generateToken((UserEntity) authenticate.getPrincipal());
            } catch (Exception e) {
                throw new BaseException("пароль или логин неправильный", HttpStatus.NOT_ACCEPTABLE);
            }
        }
    }

    @Override
    public AuthenticationResponse refreshToken(String jwt) {
        String token = jwt.substring(7);
        String userName = jwtService.extractUserName(token);
        if (!jwtService.isTokenExpired(token)) {
            UserEntity userEntity = userRepo.findByUserName(userName).orElseThrow(
                    () -> new BaseException("пользователь с UserName " + userName + " не найден", HttpStatus.NOT_FOUND)
            );
            try {
                return jwtService.generateToken(userEntity);
            } catch (Exception e) {
                throw new BaseException("Ошибка при генерации токена", HttpStatus.NOT_FOUND);
            }
        } else throw new BaseException("срок токена просрочен, пере аутентифицируйтесь", HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public String updatePass(UpdatePassRequest updatePassRequest, String jwt) {
        String token = jwt.substring(7);
        String userName = jwtService.extractUserName(token);
        if (!jwtService.isTokenExpired(token)) {
            UserEntity userEntity = userRepo.findByUserName(userName).orElseThrow(
                    () -> new BaseException("пользователь с UserName " + userName + " не найден", HttpStatus.NOT_FOUND)
            );
            if (passwordEncoder.matches(updatePassRequest.getOldPassword(), userEntity.getPassword())) {
                userEntity.setPassword(passwordEncoder.encode(updatePassRequest.getNewPassword()));
                userRepo.save(userEntity);
                return "Пароль обновлен успешно";
            } else
                throw new BaseException("Пароли не совпадают, вы не можете изменить нынешний пароль", HttpStatus.CONFLICT);
        }
        else throw new BaseException("срок токена просрочен, пере аутентифицируйтесь", HttpStatus.NOT_ACCEPTABLE);
    }
}
