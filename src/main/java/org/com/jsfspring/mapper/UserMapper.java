package org.com.jsfspring.mapper;

import org.com.jsfspring.dto.response.AddUserResponse;
import org.com.jsfspring.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.com.jsfspring.dto.response.UserDto;

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

    public static AddUserResponse entityToUserAddDto(UserEntity userEntity){
        return AddUserResponse.builder()
                .id(userEntity.getId())
                .uuid(userEntity.getUuid().toString())
                .userName(userEntity.getUsername())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .date(userEntity.getCreatedDate().toLocalDate())
                .build();
    }
}
