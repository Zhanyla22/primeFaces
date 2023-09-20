package org.xtremebiker.jsfspring.mapper;

import org.xtremebiker.jsfspring.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.xtremebiker.jsfspring.dto.response.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public static UserDto entityToUserDto(UserEntity userEntity) {
        return UserDto.builder()
                .userId(userEntity.getId())
                .name(userEntity.getFirstName())
                .build();
    }

    public static List<UserDto> entityListToUserDtoList(List<UserEntity> userEntities) {
        return userEntities.stream().map(UserMapper::entityToUserDto).collect(Collectors.toList());
    }
}
