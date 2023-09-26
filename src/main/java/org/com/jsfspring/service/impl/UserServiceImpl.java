package org.com.jsfspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.com.jsfspring.dto.request.AddUserRequest;
import org.com.jsfspring.dto.request.AuthRequest;
import org.com.jsfspring.dto.request.UpdatePassRequest;
import org.com.jsfspring.dto.response.AddUserResponse;
import org.com.jsfspring.dto.response.AuthenticationResponse;
import org.com.jsfspring.dto.response.TokenValidResponse;
import org.com.jsfspring.dto.response.UserResponse;
import org.com.jsfspring.entity.UserEntity;
import org.com.jsfspring.enums.Role;
import org.com.jsfspring.exceptions.BaseException;
import org.com.jsfspring.mapper.UserMapper;
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

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final EmailUtil emailUtil;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final AESUtil aesUtil;

    /**
     * получение всех сотрудников(кроме админа)
     * @return лист UserResponse
     */
    @Override
    public List<UserResponse> getAllUsers() {
        List<UserResponse> userResponses = UserMapper.entityListToUserDtoList(userRepo.findAll());
        return userResponses;
    }

    /**
     * генерация 6 значного когда для пароли пользователя
     * @return
     */
    public String codeGenerate() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString().replaceAll("-", "");
        return randomUUIDString.substring(0, 6);
    }

    /**
     * добавление нового пользователя, при добавлении почта проходит валидации,
     * сгенерированный пароль отправляется на почту пользователя
     * @param addUserRequest
     * @return AddUserResponse
     */
    @Override
    public AddUserResponse addNewUser(AddUserRequest addUserRequest) {
        if (!userRepo.existsByUserName(addUserRequest.getUserName()) && !addUserRequest.getUserName().isEmpty()) {
            String pass = codeGenerate();
            if (emailUtil.isValidEmailAddress(addUserRequest.getUserName())) {
                emailUtil.send(addUserRequest.getUserName(), "ваш пароль", pass);
            } else throw new BaseException("введите электронную почту!!!", HttpStatus.NOT_ACCEPTABLE);

            UserEntity userEntity = new UserEntity();
            userEntity.setUserName(addUserRequest.getUserName());
            userEntity.setFirstName(addUserRequest.getFirstName());
            userEntity.setLastName(addUserRequest.getLastName());
            userEntity.setRole(Role.ROLE_USER);
            userEntity.setPassword(passwordEncoder.encode(pass));
            userEntity.setUuid(UUID.randomUUID());
            userRepo.saveAndFlush(userEntity);

            return UserMapper.entityToUserAddDto(userEntity);
        } else
            throw new BaseException("проверьте поле userName, он не должен быть пустым, если он не пустой, то такой пользователь уже существует", HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * логин
     * @param authRequest
     * принимает зашифрованный пароль от фронта, дешифрует пароль с Username передаются на метод authenticate
     * где происходит авторизация, при успешном кейсе генерируется аксесс токен и рефреш токен
     * @return AuthenticationResponse
     */
    @Override
    public AuthenticationResponse auth(AuthRequest authRequest) {
        String decryptedPass = aesUtil.decrypt(authRequest.getPassword());
        if (decryptedPass == null) {
            throw new BaseException("пароль дешифрован неправильно", HttpStatus.NOT_ACCEPTABLE);
        } else {
            try {
                Authentication authenticate = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authRequest.getUserName(),
                                decryptedPass
                        )
                );
                return jwtService.generateToken((UserEntity) authenticate.getPrincipal());
            } catch (Exception e) {
                throw new BaseException("пароль или логин неправильный", HttpStatus.NOT_ACCEPTABLE);
            }
        }
    }

    /**
     * перегенерация токенов
     * @param jwt
     * @return AuthenticationResponse
     */
    @Override
    public AuthenticationResponse refreshToken(String jwt) {
        TokenValidResponse token = jwtService.validToken(jwt);
        UserEntity userEntity = userRepo.findByUserName(token.getUserName()).orElseThrow(
                () -> new BaseException("пользователь с UserName " + token.getUserName() + " не найден", HttpStatus.NOT_FOUND)
        );
        try {
            return jwtService.generateToken(userEntity);
        } catch (Exception e) {
            throw new BaseException("Ошибка при генерации токена", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * обновление пароли(пользователь сам обновляет пароль)
     * @param updatePassRequest
     * @param jwt
     * @return
     */
    @Override
    public String updatePass(UpdatePassRequest updatePassRequest, String jwt) {
        TokenValidResponse token = jwtService.validToken(jwt);
        UserEntity userEntity = userRepo.findByUserName(token.getUserName()).orElseThrow(
                () -> new BaseException("пользователь с UserName " + token.getUserName() + " не найден", HttpStatus.NOT_FOUND)
        );
        if (passwordEncoder.matches(updatePassRequest.getOldPassword(), userEntity.getPassword())) {
            userEntity.setPassword(passwordEncoder.encode(updatePassRequest.getNewPassword()));
            userRepo.save(userEntity);
            return "Пароль обновлен успешно";
        } else
            throw new BaseException("Пароли не совпадают, вы не можете изменить нынешний пароль", HttpStatus.CONFLICT);
    }
}
