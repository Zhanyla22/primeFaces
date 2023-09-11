package org.xtremebiker.jsfspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.xtremebiker.jsfspring.dto.request.AddUserDto;
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
import org.xtremebiker.jsfspring.service.UserService;
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
    public void addNewUser(AddUserDto addUserDto) {
        if (!userRepo.existsByUserName(addUserDto.getUserName()) && !addUserDto.getUserName().isEmpty()) { //TODO: fix
            String pass = codeGenerate();
            emailUtil.send(addUserDto.getUserName(),"ваш пароль", pass);
            userRepo.saveAndFlush(UserEntity.builder()
                    .userName(addUserDto.getUserName())
                    .firstName(addUserDto.getFirstName())
                    .lastName(addUserDto.getLastName())
                    .role(Role.ROLE_USER)
                    .password(passwordEncoder.encode(pass))
                    .build()
            );

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
    }
}
