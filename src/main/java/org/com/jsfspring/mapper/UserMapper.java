package org.com.jsfspring.mapper;

import org.com.jsfspring.dto.response.AddUserResponse;
import org.com.jsfspring.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.com.jsfspring.dto.response.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * маппер для оброботки записей пользователя
 */
@Service
public class UserMapper {

    /**
     * @param userEntity
     * @return UserResponse
     */
    public static UserResponse entityToUserDto(UserEntity userEntity) {
        return UserResponse.builder()
                .userId(userEntity.getId())
                .name(userEntity.getFirstName())
                .build();
    }

    /**
     * @param userEntities
     * @return LIst из UserResponse
     */
    public static List<UserResponse> entityListToUserDtoList(List<UserEntity> userEntities) {
        return userEntities.stream().map(UserMapper::entityToUserDto).collect(Collectors.toList());
    }

    /**
     * @param userEntity
     * @return AddUserResponse
     */
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
